package com.example.appgimnasio.repository

import com.example.appgimnasio.Model.EntrenamientosModel
import com.example.appgimnasio.data.Api
import java.util.UUID
import javax.inject.Inject

//Repositorio manejan la respuesta de la api
class EntrenamientoRepository @Inject constructor(private val api: Api) {

    //Peticion para añadir un entrenamiento
    suspend fun addEntrenamiento(enntreno: EntrenamientosModel): Boolean{
        val res = api.addEntrenamiento(enntreno)
        if (res.isSuccessful) return true
        return false
    }
    //Peticion para ver los entrenamientos de un usuario
    suspend fun getEntrenamientos(uuid: UUID?): List<EntrenamientosModel>? {
        val res = api.getEntrenamientos(uuid)
        if (res.isSuccessful) return res.body()
        return null
    }

    //Peticion para eliminar un entrenamiento
    suspend fun deleteEntrenamiento(id: Long?) {
        api.deleteEntrenamiento(id)
    }

    ///Peticion para ver la info de un entrenamiento
    suspend fun getEntreno(id: Long?): EntrenamientosModel?{
        val res = api.getEntreno(id)
        if (res.isSuccessful) return res.body()
        return null
    }
}