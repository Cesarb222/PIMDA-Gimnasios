package com.example.appgimnasio.repository

import com.example.appgimnasio.Model.EjerciciosModel
import com.example.appgimnasio.Request.NombreEjercicio
import com.example.appgimnasio.data.Api
import retrofit2.Response
import javax.inject.Inject

//Repositorio manejan la respuesta de la api
class EjerciciosRepository @Inject constructor(private val api: Api) {

    //Peticion para mostrar todos los ejercicios
    suspend fun getAllEjercicios(): List<EjerciciosModel>? {
        val res = api.getAllEjercicios()
        if(res.isSuccessful) return res.body();
        return null

    }

    //Peticion para mostrar el ejercicio por su nombre
    suspend fun getEjercicioName(nombreEjercicio: NombreEjercicio): EjerciciosModel?{
        val res = api.getEjercicioName(nombreEjercicio)
        if (res.isSuccessful) return  res.body()
        return null
    }
}