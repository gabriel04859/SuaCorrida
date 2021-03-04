package com.gabrielribeiro.suacorrida.ui.fragments

import android.os.Bundle
import android.util.Log
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
import com.gabrielribeiro.suacorrida.utils.Constants.TAG
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
                if(it == 0.0F){
                    val speed  = "0.0km/h"
                    binding.textViewTotalDistanceStatics.text = speed
                }
                val speed = round(it * 10F) / 10F
                Log.d(TAG, "observerValuesFromUser: Total velocidade: $speed -- $it")
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
    /*
    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/textViewTotalTimeStatics"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="@color/white"
        android:textSize="30sp"
        android:layout_marginTop="8dp"
        app:layout_constraintEnd_toStartOf="@+id/textViewTotalDistanceStatics"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:text="00:00:03" />

    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/textViewTotalDistanceStatics"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="@color/white"
        android:textSize="30sp"
        android:layout_marginTop="8dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toEndOf="@+id/textViewTotalTimeStatics"
        app:layout_constraintTop_toTopOf="parent"
        tools:text="00:00:03" />

    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/textViewTotalTimeText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Tempo Total"
        android:textColor="@color/white"
        android:textSize="18sp"
        android:layout_marginTop="6dp"
        app:layout_constraintEnd_toEndOf="@+id/textViewTotalTimeStatics"
        app:layout_constraintStart_toStartOf="@+id/textViewTotalTimeStatics"
        app:layout_constraintTop_toBottomOf="@+id/textViewTotalTimeStatics" />

    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/textViewTotalDistanceText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Tempo Total"
        android:textColor="@color/white"
        android:layout_marginTop="6dp"
        android:textSize="18sp"
        app:layout_constraintEnd_toEndOf="@+id/textViewTotalDistanceStatics"
        app:layout_constraintStart_toStartOf="@+id/textViewTotalDistanceStatics"
        app:layout_constraintTop_toBottomOf="@+id/textViewTotalDistanceStatics" />

    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/textViewTotalCaloriesBurnedStatics"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="@color/white"
        android:textSize="30dp"
        android:layout_marginTop="16dp"
        app:layout_constraintEnd_toEndOf="@+id/textViewTotalTimeText"
        app:layout_constraintStart_toStartOf="@+id/textViewTotalTimeText"
        app:layout_constraintTop_toBottomOf="@+id/textViewTotalTimeText"
        tools:text="3kcal" />

    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/textViewCaloriesBurnedText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Total calorias"
        android:textColor="@color/white"
        android:textSize="18sp"
        android:layout_marginTop="6dp"
        app:layout_constraintEnd_toEndOf="@+id/textViewTotalCaloriesBurnedStatics"
        app:layout_constraintStart_toStartOf="@+id/textViewTotalCaloriesBurnedStatics"
        app:layout_constraintTop_toBottomOf="@+id/textViewTotalCaloriesBurnedStatics" />

    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/textViewTotalSpeedStatics"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="@color/white"
        android:textSize="22sp"
        android:layout_marginTop="30dp"
        app:layout_constraintEnd_toEndOf="@+id/textViewTotalDistanceText"
        app:layout_constraintStart_toStartOf="@+id/textViewTotalDistanceText"
        app:layout_constraintTop_toBottomOf="@+id/textViewTotalDistanceText"
        tools:text="50.7Km/h" />

    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/textViewSpeedText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Total Velocidade"
        android:textColor="@color/white"
        android:textSize="18sp"
        android:layout_marginTop="6dp"
        app:layout_constraintEnd_toEndOf="@+id/textViewTotalSpeedStatics"
        app:layout_constraintStart_toStartOf="@+id/textViewTotalSpeedStatics"
        app:layout_constraintTop_toBottomOf="@+id/textViewTotalSpeedStatics" />

*/

}