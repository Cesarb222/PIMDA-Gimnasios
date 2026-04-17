package com.example.appgimnasio.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgimnasio.Model.GimnasioModel
import com.example.appgimnasio.repository.GimnasioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GimnasioViewModel @Inject constructor( private val repository: GimnasioRepository) : ViewModel(){

    private val _gimnasio = MutableStateFlow<GimnasioModel?>(null)
    val gimnasio = _gimnasio.asStateFlow()

    init {
        getGimnasio()
    }

    fun getGimnasio(){
        viewModelScope.launch {

            _gimnasio.value = repository.getGimnasioId(1)

            if (_gimnasio.value!=null){
                Log.d("Gimasio ", _gimnasio.value!!.nombre)
            }

        }
    }

    fun updateGimnasio(gym: GimnasioModel){
        viewModelScope.launch {

            _gimnasio.value = repository.update(gym)


        }
    }

}