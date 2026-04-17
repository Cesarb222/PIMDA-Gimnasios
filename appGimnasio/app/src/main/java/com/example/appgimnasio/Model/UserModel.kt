package com.example.appgimnasio.Model

import java.util.UUID
//Modelo para Usuarios
data class UserModel(
    val id: UUID? = null,
    val nombre: String,
    val apellido: String,
    val email:String,
    val genero: String,
    val passwd:String,
    val f_registro:String,
    val f_nacimiento: String,
    val rol: String,
    var gimnasio: GimnasioModel? = null

)
