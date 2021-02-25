package com.gabrielribeiro.suacorrida.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gabrielribeiro.suacorrida.R
import com.gabrielribeiro.suacorrida.databinding.FragmentSettingsBinding
import com.gabrielribeiro.suacorrida.databinding.FragmentSetupBinding
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_FIRST_TIME_TOGGLE
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_NAME
import com.gabrielribeiro.suacorrida.utils.Constants.KEY_WEIGHT
import com.gabrielribeiro.suacorrida.utils.Constants.TAG
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding  : FragmentSettingsBinding? = null
    private val binding : FragmentSettingsBinding get() = _binding!!

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserInfoFromSharedPreferences()

        binding.buttonApplyChanges.setOnClickListener{
            val success = updateUserInformation()
            if (!success){
                Snackbar.make(view, getString(R.string.tente_novamente), Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(view, getString(R.string.alteracoes_salvas), Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun getUserInfoFromSharedPreferences(){
        val name = sharedPreferences.getString(KEY_NAME, "Not Found")
        val weigh = sharedPreferences.getFloat(KEY_WEIGHT, 70F)
        val firstTimeToggle = sharedPreferences.getBoolean(KEY_FIRST_TIME_TOGGLE, false)
        Log.d(TAG, "getUserInfoFromSharedPreferences: User Info: ${name} - $weigh - $firstTimeToggle")
        binding.editTextNameSettings.setText(name)
        binding.editTextWeightSettings.setText(weigh.toString())

    }
    private fun updateUserInformation() : Boolean{
        val name = binding.editTextNameSettings.text.toString().trim()
        val weigh = binding.editTextWeightSettings.text.toString().trim()

        if (name.isEmpty() || weigh.isEmpty()){
            return false
        }

        return try{
            sharedPreferences.edit().putString(KEY_NAME, name).putFloat(KEY_WEIGHT, weigh.toFloat()).apply()
            true

        }catch (e : Exception){
            Log.d("TAG", "updateUserInformation: Erro ao editar user info: ${e.message} ")
            false
        }



    }



    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}