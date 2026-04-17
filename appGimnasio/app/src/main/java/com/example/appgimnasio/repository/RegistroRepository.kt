package com.example.appgimnasio.repository

import com.example.appgimnasio.Model.RegistroModel
import com.example.appgimnasio.Model.UserRegistro
import com.example.appgimnasio.data.Api
import java.util.UUID
import javax.inject.Inject

//Repositorio manejan la respuesta de la api
class RegistroRepository @Inject constructor(private val api: Api) {

    //Peticion para añadir un registro
    suspend fun addRegistro(registroModel: RegistroModel): RegistroModel?{
        val res = api.addRegistro(registroModel)
        if (res.isSuccessful) return res.body()
        return null
    }

    //Peticion para ver los registros de un usuario
    suspend fun getAllRegistrosUser(uuid: UUID?): List<RegistroModel>?{
        val res = api.getAllRegistrosUser(uuid)
        if (res.isSuccessful) return res.body()
        return null
    }

    //Peticion para ver un registro
    suspend fun getByIdRegistros(id: Long): RegistroModel?{
        val res = api.getByIdRegistro(id)
        if (res.isSuccessful) return res.body()
        return null
    }

    //Peticion para visualizar los comentarios de un registro
    suspend fun getComentarios(id: Long): List<UserRegistro>?{
        val res = api.getComentarios(id)
        if (res.isSuccessful) return res.body()
        return null
    }

    //Peticion para añadir comentarios a un registro
    suspend fun addComentario(userRegistro: UserRegistro): Boolean{
        val res = api.addComentario(userRegistro)
        if (res.isSuccessful) return true
        return false
    }

    //Peticion para visualizar todos los registro publicos
    suspend fun getAllRegistros(): List<RegistroModel>? {
        val res = api.getAllRegistros()
        if (res.isSuccessful) return res.body()
        return null

    }
}