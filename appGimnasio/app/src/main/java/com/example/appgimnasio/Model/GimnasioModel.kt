package com.example.appgimnasio.Model

//Modelo para Gimnasio
data class GimnasioModel(
    val id: Long,
    val nombre: String,
    val direccion: String,
    val ciudad: String,
    val telefono: String,
    val horario: String
)