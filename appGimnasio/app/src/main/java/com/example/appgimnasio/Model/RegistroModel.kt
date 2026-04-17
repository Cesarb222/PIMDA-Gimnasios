package com.example.appgimnasio.Model

import java.time.LocalDate
//Modelo para Registro
data class RegistroModel(
    val id: Long? = null,
    val titulo: String,
    val cuerpo: String?,
    val marca: Float?,
    val f_registro: String,
    val publico: Boolean,
    val ejercicio: EjerciciosModel? = null,
    val usuario: UserModel
)