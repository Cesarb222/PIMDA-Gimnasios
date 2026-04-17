package com.example.appgimnasio.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgimnasio.Model.RegistroModel
import com.example.appgimnasio.Model.UserRegistro
import com.example.appgimnasio.ViewModels.UserViewModel.Companion.USUARIO
import com.example.appgimnasio.repository.RegistroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegistroViemModel @Inject constructor(private val repository: RegistroRepository) : ViewModel()  {

    //Creamos las variables que usaremos en la UI
    val registroCargado = MutableStateFlow<RegistroModel?>(null)

    val misRegistros = MutableStateFlow<List<RegistroModel?>>(emptyList())
    val registroSeleccionado = MutableStateFlow<RegistroModel?>(null)

    val comentariosRegistro = MutableStateFlow<List<UserRegistro?>>(emptyList())

    val registrosPublicos = MutableStateFlow<List<RegistroModel?>>(emptyList())

    //Si el usuario tiene iniciada la sesion cargaremos sus registros lo primero de todo
    init {
        if (USUARIO!=null){
            getMisRegistros(USUARIO!!.id)
        }

    }

    //Funcion para añadir un registro en nuestra API
    fun addRegistro(registroModel: RegistroModel){
        viewModelScope.launch {
            registroCargado.value = repository.addRegistro(registroModel)
        }
    }

    // Funcion para cargar los registros del usuario mediante su UUID que seria su id
    fun getMisRegistros(uuid: UUID?){
        viewModelScope.launch {
            misRegistros.value = repository.getAllRegistrosUser(uuid)!!
        }
    }

    //Funcion para buscar la info del registro seleccionado
     fun registroById(id: Long){
        viewModelScope.launch {
            registroSeleccionado.value = repository.getByIdRegistros(id)
        }
    }

    //Funcion para cargar los comentarios de una publicacion
    fun comentarios(id: Long){
        viewModelScope.launch {
            comentariosRegistro.value = repository.getComentarios(id)!!
        }
    }

    //Funcion para añadir el comentario
    fun addComentario(userRegistro: UserRegistro): Boolean{
        var estado: Boolean = false
        viewModelScope.launch {
           estado = repository.addComentario(userRegistro)
        }
        return estado
    }


    //Funcion para pillar todos los registros
    fun registros(){
        viewModelScope.launch {
            registrosPublicos.value = repository.getAllRegistros()!!
        }
    }


}