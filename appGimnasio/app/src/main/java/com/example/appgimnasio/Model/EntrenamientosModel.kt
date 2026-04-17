package com.example.appgimnasio.Model

import java.util.Date
//Modelo para Entrenamientos
data class EntrenamientosModel(
    val id: Long? = null,
    val nombre: String,
    val descripcion: String,
    val f_creacion: String,
    val duracion: Int,
    val usuarios: UserModel,
    val ejercicios: List<EjerciciosModel>
)