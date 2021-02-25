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
import androidx.navigation.fragment.findNavController
import com.gabrielribeiro.suacorrida.R
import com.gabrielribeiro.suacorrida.database.RunDAO
import com.gabrielribeiro.suacorrida.databinding.FragmentSettingsBinding
import com.gabrielribeiro.suacorrida.databinding.FragmentSetupBinding
import com.gabrielribeiro.suacorrida.ui.viewmodel.MainViewModel
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_FIRST_TIME_TOGGLE
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_NAME
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {
    private var _binding  : FragmentSetupBinding? = null
    private val binding : FragmentSetupBinding get() = _binding!!

    private val mainViewModel : MainViewModel by viewModels()

    @set:Inject
    var isFirstTimeOpenApp = true

    @set:Inject
    var weight = 70F

    @Inject
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSetupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isFirstTimeOpenApp){
            findNavController().navigate(R.id.action_setupFragment_to_runFragment)
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