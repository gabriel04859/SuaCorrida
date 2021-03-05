package com.gabrielribeiro.suacorrida.ui.fragments

import android.content.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.gabrielribeiro.suacorrida.R
import com.gabrielribeiro.suacorrida.database.RunDAO
import com.gabrielribeiro.suacorrida.databinding.FragmentSettingsBinding
import com.gabrielribeiro.suacorrida.databinding.FragmentSetupBinding
import com.gabrielribeiro.suacorrida.ui.viewmodel.MainViewModel
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_FIRST_TIME_TOGGLE
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_NAME
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_WEIGHT
import com.gabrielribeiro.suacorrida.utils.Constants.TAG
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {
    private var _binding  : FragmentSetupBinding? = null
    private val binding : FragmentSetupBinding get() = _binding!!

    @Inject
    lateinit var sharedPreferences : SharedPreferences



    @set:Inject
    var isFirstAppOpen = true

    private var isFirstTime = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSetupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: First Time: $isFirstAppOpen")

        if(!isFirstAppOpen) {
            val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.setupFragment, true)
                    .build()
            findNavController().navigate(
                    R.id.action_setupFragment_to_runFragment,
                    savedInstanceState,
                    navOptions
            )
        }

        binding.textViewContinue.setOnClickListener {
            val success = writePersonalDataToSharedPref()
            if (success){
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            }else{
                Snackbar.make(requireView(), "Preencha todos os campos", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    private fun writePersonalDataToSharedPref() : Boolean{
        val name = binding.editTextName.text.toString().trim()
        val weight = binding.editTextWeight.text.toString().trim()
        if (name.isEmpty() || weight.isEmpty()){
            return false
        }
        sharedPreferences.edit()
                .putString(KEY_NAME, name)
                .putFloat(KEY_WEIGHT, weight.toFloat())
                .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
                .apply()


        return true
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}