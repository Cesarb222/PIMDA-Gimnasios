package com.example.appgimnasio.ViewModels

import androidx.lifecycle.ViewModel
import com.example.appgimnasio.Model.EntrenamientosModel
import com.example.appgimnasio.repository.EntrenamientoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID


@HiltViewModel
class EntrenamientoViewModel @Inject constructor(private val repository: EntrenamientoRepository):
    ViewModel() {

        //Variables que se implementan para modificar la UI
    var estado = MutableStateFlow(false)

    val entrenos = MutableStateFlow<List<EntrenamientosModel>>(emptyList())

    val entrenoInfo = MutableStateFlow<EntrenamientosModel?>(null)

    //Funcion para añadir entreno
    fun addEntrenamiento(entrenamiento: EntrenamientosModel){
        viewModelScope.launch {
            estado.value =repository.addEntrenamiento(entrenamiento)
        }
    }

    //Funcion para ver los entrenos de un usuario
    fun getEntrenamientos(uuid: UUID?){
        viewModelScope.launch {
            entrenos.value = repository.getEntrenamientos(uuid)!!
        }
    }

    //Funcion para ver un entreno individual
    fun getEntreno(id: Long?){
        viewModelScope.launch {
            entrenoInfo.value = repository.getEntreno(id)!!
        }
    }
    //Funcion para eliminar un entreno
    fun deleteEntrenamiento(id: Long?){
        viewModelScope.launch {
            repository.deleteEntrenamiento(id)
            entrenos.value = entrenos.value.filter { it.id != id }
        }
    }

    fun cambiarEstado(valor: Boolean){
        estado.value = valor
    }
}