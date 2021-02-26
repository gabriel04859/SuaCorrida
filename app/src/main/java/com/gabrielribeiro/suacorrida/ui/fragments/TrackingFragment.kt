package com.gabrielribeiro.suacorrida.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gabrielribeiro.suacorrida.R
import com.gabrielribeiro.suacorrida.databinding.FragmentRunBinding
import com.gabrielribeiro.suacorrida.databinding.FragmentTrackingBinding
import com.gabrielribeiro.suacorrida.model.Run
import com.gabrielribeiro.suacorrida.service.Polyline
import com.gabrielribeiro.suacorrida.service.TrackingService
import com.gabrielribeiro.suacorrida.ui.viewmodel.MainViewModel
import com.gabrielribeiro.suacorrida.utils.CancelTrackingDialog
import com.gabrielribeiro.suacorrida.utils.Constants.ACTION_PAUSE_SERVICE
import com.gabrielribeiro.suacorrida.utils.Constants.ACTION_START_OR_RESUME_SERVICE
import com.gabrielribeiro.suacorrida.utils.Constants.ACTION_STOP_SERVICE
import com.gabrielribeiro.suacorrida.utils.Constants.CAMERA_ZOOM
import com.gabrielribeiro.suacorrida.utils.Constants.CANCEL_TRACKING_DIALOG_TAG
import com.gabrielribeiro.suacorrida.utils.Constants.POLYLINE_COLOR
import com.gabrielribeiro.suacorrida.utils.Constants.POLYLINE_WIDTH
import com.gabrielribeiro.suacorrida.utils.TrackingUtility
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.round

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {
    private var _binding  : FragmentTrackingBinding? = null
    private val binding : FragmentTrackingBinding get() = _binding!!

    private val mainViewModel : MainViewModel by viewModels()

    private var map : GoogleMap? = null

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    private var currentTimeMillis = 0L

    private var menu : Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTrackingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState != null){
            val cancelTrackingDialog = parentFragmentManager.findFragmentByTag(
                    CANCEL_TRACKING_DIALOG_TAG) as CancelTrackingDialog?
            cancelTrackingDialog?.setYesListener {
                stopRun()
            }
        }
        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.getMapAsync {
            map = it
            addAllPolyline()
        }

        subscribeToObservers()


        setHasOptionsMenu(true)
        binding.buttonToggleRun.setOnClickListener {
           toggleRun()
        }

        binding.buttonFinishRun.setOnClickListener{
            zoomToSeeWholeTrack()
            endRunAndSaveToDb()
        }


    }

    private fun sendCommandToService(action : String){
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    }

    private fun subscribeToObservers(){
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyline()
            moveCamera()
        })

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, Observer {
            currentTimeMillis = it
            val formattedTime = TrackingUtility.getFormattedStopWatchTime(currentTimeMillis,true)
            binding.textViewTimer.text = formattedTime
        })
    }
    private fun toggleRun(){
        if (isTracking){
            menu?.getItem(0)?.isVisible = true
            sendCommandToService(ACTION_PAUSE_SERVICE)
        }else{
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun updateTracking(isTracking : Boolean){
        this.isTracking = isTracking
        if (!isTracking && currentTimeMillis > 0L){
            binding.buttonToggleRun.text = getString(R.string.comecar)
            binding.buttonFinishRun.visibility = View.VISIBLE
        }else if(isTracking){
            binding.buttonToggleRun.text = getString(R.string.parar)
            binding.buttonFinishRun.visibility = View.GONE
            menu?.getItem(0)?.isVisible = true
        }

    }
    private fun moveCamera(){
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()){
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(pathPoints.last().last(), CAMERA_ZOOM))
        }
    }
    private fun addAllPolyline(){

        for (polyline in pathPoints){
            val polylineOptions = PolylineOptions().color(POLYLINE_COLOR)
                    .width(POLYLINE_WIDTH)
                    .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }
    private fun addLatestPolyline(){
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1){
            Log.d("TAG", "addLatestPolyline: pathPoints: $pathPoints")
            val preLastLatLng = pathPoints.last()[pathPoints.last().size -1]
            Log.d("TAG", "addLatestPolyline: preLastLatLng: $preLastLatLng")
            val lastLatLng = pathPoints.last().last()
            Log.d("TAG", "addLatestPolyline: lastLatLng: $lastLatLng")
            val polylineOptions = PolylineOptions()
                    .color(POLYLINE_COLOR)
                    .width(POLYLINE_WIDTH)
                    .add(preLastLatLng)
                    .add(lastLatLng)
            map?.addPolyline(polylineOptions)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_tracking_menu, menu)
        this.menu = menu
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if(currentTimeMillis > 0L){
            this.menu?.getItem(0)?.isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miCancelTracking -> showCancelTrackingDialog()
        }

        return super.onOptionsItemSelected(item)

    }

    private fun zoomToSeeWholeTrack() {
        val bounds = LatLngBounds.Builder()
        for(polyline in pathPoints) {
            for(pos in polyline) {
                bounds.include(pos)
            }
        }

        map?.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                        bounds.build(),
                        binding.mapView.width,
                        binding.mapView.height,
                        (binding.mapView.height * 0.05f).toInt()
                )
        )
    }

    private fun endRunAndSaveToDb() {
        map?.snapshot { bmp ->
            var distanceInMeters = 0
            for(polyline in pathPoints) {
                distanceInMeters += TrackingUtility.calculatePolylineLength(polyline).toInt()
            }
            val avgSpeed = round((distanceInMeters / 1000f) / (currentTimeMillis / 1000f / 60 / 60) * 10) / 10f
            val dateTimestamp = Calendar.getInstance().timeInMillis
            val caloriesBurned = ((distanceInMeters / 1000f) * 72).toInt()
            val run = Run(bmp, dateTimestamp, avgSpeed, distanceInMeters, currentTimeMillis, caloriesBurned)
            mainViewModel.insertRun(run)
            Snackbar.make(
                    requireActivity().findViewById(R.id.rootView),
                    "Corrida salva",
                    Snackbar.LENGTH_LONG
            ).show()
            stopRun()
        }
    }


    private fun showCancelTrackingDialog() {
        CancelTrackingDialog().apply {
            setYesListener {
                stopRun()
            }
        }.show(parentFragmentManager, CANCEL_TRACKING_DIALOG_TAG)

    }

    private fun stopRun() {
        binding.textViewTimer.text = getString(R.string.initial_time)
        sendCommandToService(ACTION_STOP_SERVICE)
        findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStop()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
      
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}