package com.example.appgimnasio.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgimnasio.Model.SubscripcionModel
import com.example.appgimnasio.repository.SubscripcionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscripcionViewModel @Inject constructor(private val repository: SubscripcionRepository) : ViewModel() {

    private val _estadoSub = MutableStateFlow<Boolean>(false)

    val estadoSub = _estadoSub.asStateFlow()

    fun addSubscripcion(s: SubscripcionModel){
        viewModelScope.launch {
            val result = repository.addSubscripcion(s)
            if (result != null) {
                _estadoSub.value = result
            };
        }
    }
}