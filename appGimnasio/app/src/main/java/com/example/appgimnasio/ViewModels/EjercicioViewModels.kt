package com.example.appgimnasio.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appgimnasio.Model.EjerciciosModel
import com.example.appgimnasio.repository.EjerciciosRepository
import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.appgimnasio.Request.NombreEjercicio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class EjercicioViewModels @Inject constructor(val repository: EjerciciosRepository): ViewModel() {

    //variables para modificar el estado de la ui

    var list = MutableStateFlow<List<EjerciciosModel>>(emptyList())
    val ejercicioSeleccionado = MutableStateFlow<EjerciciosModel?>(null)

    //Siempre se nos cargara al principio la lista
    init {
        cargarLista()
    }
    //Funcion para cargar la lista de ejercicios
    fun cargarLista(){
        viewModelScope.launch {
            val resultado = repository.getAllEjercicios()
            if (resultado != null) list.value = resultado
        }
    }
    //Funcion para pillar un ejercicio individual, aclarar tambien que en la BD en
    // nombre es Unique es decir no se repite ningun nombr
    fun getEjercicioByName(nombre: String){
        viewModelScope.launch {
            ejercicioSeleccionado.value = repository.getEjercicioName(NombreEjercicio(nombre))
        }
    }
}