package com.gabrielribeiro.suacorrida.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gabrielribeiro.suacorrida.R
import com.gabrielribeiro.suacorrida.databinding.FragmentSettingsBinding
import com.gabrielribeiro.suacorrida.databinding.FragmentStaticsBinding
import com.gabrielribeiro.suacorrida.ui.viewmodel.StatisticsViewModel
import com.gabrielribeiro.suacorrida.utils.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StaticsFragment : Fragment(R.layout.fragment_statics) {
    private var _binding  : FragmentStaticsBinding? = null
    private val binding : FragmentStaticsBinding get() = _binding!!

    private val staticsViewModel : StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStaticsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerValuesFromUser()

    }

    private fun observerValuesFromUser() {
        staticsViewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimeRun = TrackingUtility.getFormattedStopWatchTime(it)
                
                binding.textViewTotalTimeStatics.text = totalTimeRun
            }
        })

        staticsViewModel.totalDistance.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it / 1000F
                val totalDistance = round(km * 10F) / 10F
                val totalDistanceString = "${totalDistance}Km"
                binding.textViewTotalDistanceStatics.text = totalDistanceString
            }
        })

        staticsViewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val speed = round(it * 10F) / 10F
                val speedString = "${speed}km/h"
                binding.textViewTotalDistanceStatics.text = speedString

            }
        })

        staticsViewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCalories = "${it}kcal"
                binding.textViewTotalCaloriesBurnedStatics.text = totalCalories
            }
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}