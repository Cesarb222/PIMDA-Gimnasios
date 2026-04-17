package com.example.appgimnasio.Model

//Modelo para EjerciciosModel
data class EjerciciosModel(
    val id: Long,
    val nombre: String,
    val grupo_muscular:String,
    val descripcion: String,
    val material: String,
    val imagen: String
)
