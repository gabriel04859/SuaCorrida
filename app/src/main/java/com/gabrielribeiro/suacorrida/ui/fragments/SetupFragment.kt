package com.gabrielribeiro.suacorrida.ui.fragments

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
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {
    private var _binding  : FragmentSetupBinding? = null
    private val binding : FragmentSetupBinding get() = _binding!!

    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSetupBinding.inflate(layoutInflater, container, false)
        return binding.root
        Log.d("TESTE", "onCreateView: $mainViewModel")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewContinue.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_runFragment)
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}