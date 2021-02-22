package com.gabrielribeiro.suacorrida.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielribeiro.suacorrida.model.Run
import com.gabrielribeiro.suacorrida.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository): ViewModel() {
    fun insertRun(run : Run) = viewModelScope.launch {
        mainRepository.insertRun(run)

    }


}