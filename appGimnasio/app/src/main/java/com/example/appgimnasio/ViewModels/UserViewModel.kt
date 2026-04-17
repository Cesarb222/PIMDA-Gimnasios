package com.example.appgimnasio.ViewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appgimnasio.Model.GimnasioModel
import com.example.appgimnasio.Model.UserModel
import com.example.appgimnasio.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository,
    @ApplicationContext private val context: Context): ViewModel() {
    var usuarioLogueado by mutableStateOf<UserModel?>(null)
    var mensajeLogin by mutableStateOf("")

    private val _listaUsuarios = MutableStateFlow<List<UserModel>>(emptyList())
    val listaUsuarios = _listaUsuarios.asStateFlow()

    private val _porcentajeUsuariosapp = Array<Double>(3) { 0.0 }
    val porcentajeUsuariosapp = _porcentajeUsuariosapp

    private val _porcentajeGimnasiosapp = Array<Double>(3) { 0.0 }
    val porcentajeGimnasiosapp = _porcentajeGimnasiosapp
    private val _codigoRecuperacion = MutableStateFlow<Int?>(null)

    val codigoRecuperacion = _codigoRecuperacion.asStateFlow()
    private val _cargaCodigo = MutableStateFlow(false)
    val cargaCodigo = _cargaCodigo.asStateFlow()

    private val _estadonewPassword = MutableStateFlow<Boolean?>(null)
    val estadonewPassword = _estadonewPassword.asStateFlow()

    //Cargamos la sesion con el Init, esto lo primero que va a hacer
    // es cargar la sesion en la app cuando se inicia
    init {
        cargarSesion()
        getAllUsers()
    }
    //Companion object este nos muestra un objeto para toda la aplicacion
    //inicialmente se inicia en null y si el usuario ha sido autenticado se
    //modifica con el usuario actual
    companion object{
        var USUARIO by mutableStateOf<UserModel?>(null)
    }

    //El login se ha realizado en una funcion suspendida para que nos retorne el usuario
    //para manejar los errores directamente desde la UI o vista
    suspend fun login(email: String, password: String): UserModel? {
        val resultado = repository.getUser(email, password)
        //si encuentra resultado lo guardamos en el object companion y guardamo session
        if (resultado != null) {
            usuarioLogueado = resultado
            USUARIO = resultado
            guardarSesion(resultado)
        } else {
            usuarioLogueado = null
        }
        return USUARIO
    }


    //Funcion para hacer el registro del usuario en la que recibimos todos los parametros para el usuario
    fun registro(nombre: String,apellido:String, email: String, passwd: String, f_nacimiento: String, genero:String){
        //Creamos el objeto utilizando un user model
        val usuario = UserModel(
            nombre = nombre, apellido = apellido,
            email = email, passwd = passwd,
            //Remplazamos los caracteres por que en el datePicker nos llega la fecha separado por /
            // y la tenemos que insertar con '-'
            f_nacimiento = f_nacimiento.replace("/","-"),
            f_registro = LocalDate.now().toString(),
            genero = genero, rol = "usuario"
        )
        viewModelScope.launch {
            //Utilizamos otro hilo que no sea el principal con el Dispatchers.IO que en este caso
            //es el ideal para peticiones
            withContext(Dispatchers.IO){
                val user = repository.addUser(usuario)
                usuarioLogueado = user
                //Guardamos el usuario en el object companion
                USUARIO = user
                //Guardo la sesion en las preferencias de la app
                guardarSesion(usuarioLogueado!!)
            }
        }
    }

    //Funcion para guardar los datos de sesión de un usuario
    private fun guardarSesion(user: UserModel) {
        // Se obtiene el objeto en las preferencias de la app en este caso
        // lo hemos llamado "user_prefs" y esta en modo privado
        // para que solo pueda leerlo la app
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        //Gson para convertir objetos de Kotlin a JSON
        val gson = Gson()
        //convertimos el objeto a una cadena json para mandarselo a la preferencias
        val userJson = gson.toJson(user)
        //editamos la preferencia y aplicamos para guardar los cambios
        prefs.edit().putString("usuario_key", userJson).apply()
    }

    private fun cargarSesion() {
        //Obtenemos el objeto desde las preferencias
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        // leemos la cadena guardada en "usuario_key"
        // si esta no exite devuelve null
        val userJson = prefs.getString("usuario_key", null)

        //Si contiene algo lo convertimos a objeto y se lo pasamos al objeto companion
        if (userJson != null) {
            val userGuardado = Gson().fromJson(userJson, UserModel::class.java)
            usuarioLogueado = userGuardado

            //Tambien modificamos el usuario de las preferencias por si ha tenido algun cambio
            viewModelScope.launch {
                val userCompleto = getUserByUUID(userGuardado.id)
                if (userCompleto != null) {
                    USUARIO = userCompleto
                    guardarSesion(userCompleto)
                } else {
                    USUARIO = userGuardado
                }
            }
        }
    }

    //Funxion para cerrar la sesion
    fun logout() {
        //Recuperamos las preferencias y la eliminamos y aplicamos cambios
        // y ponemos las variables como nulas
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        usuarioLogueado = null
        USUARIO = null

    }

    //Funcion para comprobar si el correo existe o no
    suspend fun comprobarCorreo(correo:String): Boolean{
        val emails = repository.getEmails()
        return emails?.contains(correo) ?: false
    }


    suspend fun getUserByUUID(uuid: UUID?): UserModel? {
        return repository.getUserByUuid(uuid)
    }

    fun getCodigo(email: String) {
        viewModelScope.launch {
            try {
                _cargaCodigo.value = true

                val res = repository.getCodigo(email)

                _codigoRecuperacion.value = res
            } catch (e: Exception) {
                // SI HAY TIMEOUT, ESTO SE TIENE QUE VER EN ROJO EN EL LOGCAT
                Log.e("Error", " Error: ${e.localizedMessage}")
            } finally {
                _cargaCodigo.value = false
            }
        }
    }

    //metodos para actualizar la contraseña como el gimnasio cuando se haya completado la subscripcion
    fun updatePassword(email: String,passwd: String){
        viewModelScope.launch {
            _estadonewPassword.value = repository.getUpdatePassword(email,passwd)
        }
    }

    //Metodo  en la cual actualizamos al usuario el gimnasio una vez pasada la subscripcion
    fun updateGimnasio(gimnasioModel: GimnasioModel,uuid: UUID){
        viewModelScope.launch {
            repository.updateGimnasio(gimnasioModel,uuid)
            USUARIO = USUARIO!!.copy(gimnasio = gimnasioModel)
            guardarSesion(USUARIO!!)
        }
    }

    //funcion para recopilar todos los usuarios
    fun getAllUsers() {
        viewModelScope.launch {
            val users = repository.getAllUsers() ?: emptyList()
            _listaUsuarios.value = users

            if (users.isNotEmpty()) {
                calcularPorcentajesUserApp()
                calcularGimnasioPorcentajeApp()
            }
        }
    }

    //En estas 2 funciones calculo los porcentajes de los usuarios, uno de todos
    //los usuarios registrados y otro de los usuarios que tienen una subscripcion
    fun calcularPorcentajesUserApp(){
        var totalMujeres = 0
        var totalHombres = 0
        var totalOtroGenero = 0
        for (i in _listaUsuarios.value){
            when(i.genero){
                "Masculino" -> totalHombres++
                "Femenino" -> totalMujeres++
                "Prefiero no decirlo" -> totalOtroGenero++
            }
        }
        val porcetajeHombre = (totalHombres.toDouble() / _listaUsuarios.value.size) * 100
        val porcetajeMujer = (totalMujeres.toDouble() /_listaUsuarios.value.size) * 100
        val totalGenero = (totalOtroGenero.toDouble() /_listaUsuarios.value.size) * 100

        _porcentajeUsuariosapp.set(0,porcetajeHombre)
        _porcentajeUsuariosapp.set(1,porcetajeMujer)
        _porcentajeUsuariosapp.set(2,totalGenero)
    }

    fun calcularGimnasioPorcentajeApp(){
        var totalMujeres = 0
        var totalHombres = 0
        var totalOtroGenero = 0
        var totalSubscritos = 0
        for (i in _listaUsuarios.value){
            if (i.gimnasio!=null){
                totalSubscritos++
                when(i.genero){
                    "Masculino" -> totalHombres++
                    "Femenino" -> totalMujeres++
                    "Prefiero no decirlo" -> totalOtroGenero++
                }
            }
        }
        val porcetajeHombre = (totalHombres.toDouble() / totalSubscritos) * 100
        val porcetajeMujer = (totalMujeres.toDouble() /totalSubscritos) * 100
        val totalGenero = (totalOtroGenero.toDouble() /totalSubscritos) * 100

        _porcentajeGimnasiosapp.set(0,porcetajeHombre)
        _porcentajeGimnasiosapp.set(1,porcetajeMujer)
        _porcentajeGimnasiosapp.set(2,totalGenero)
    }

}