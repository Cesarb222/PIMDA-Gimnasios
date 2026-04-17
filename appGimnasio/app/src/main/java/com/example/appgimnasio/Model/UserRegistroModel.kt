package com.example.appgimnasio.Model
//Modelo para User registro es decir el comentario
data class UserRegistro(
    val id: Long? = null,
    val f_publicacion: String,
    val mensaje: String,
    val usuario: UserModel?,
    val registro: RegistroModel
)

