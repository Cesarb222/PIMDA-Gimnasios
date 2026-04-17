package com.example.appgimnasio.Model

data class SubscripcionModel(
    val usuario: UserModel,
    val gimnasio: GimnasioModel?,
    val fechaInicio: String,
    val fechaFin: String,
    val estado: Boolean
)