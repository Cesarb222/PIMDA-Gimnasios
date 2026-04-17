package com.example.appgimnasio.Request

//Esto son las DTO que se hab utilizado para mandar recuperar la informacion
data class UserRequest(
    val email:String,
    val passwd:String
)

data class NombreEjercicio(
    val nombre: String
)