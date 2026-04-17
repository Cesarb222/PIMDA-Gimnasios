package com.example.appgimnasio.repository

import com.example.appgimnasio.Model.GimnasioModel
import com.example.appgimnasio.Model.UserModel
import com.example.appgimnasio.Request.UserRequest
import com.example.appgimnasio.data.Api
import java.util.UUID
import javax.inject.Inject

//Repositorio manejan la respuesta de la api
class UserRepository @Inject constructor(private val api: Api) {

    //Peticion para recopilar el usuario
    suspend fun getUser(email: String,passwd: String): UserModel? {
        val res = api.login(UserRequest(email,passwd))
        if (res.isSuccessful){
            return res.body()
        }
        return null
    }

    //Peticion para recopilar el usuario por uuid
    suspend fun getUserByUuid(uuid: UUID?): UserModel? {
        val res = api.getUserByUUID(uuid)
        if (res.isSuccessful) return res.body()
        return null
    }

    //Peticion para añadir el usuario
    suspend fun addUser(userModel: UserModel): UserModel?{
        val res = api.registro(userModel)
        if (res.isSuccessful) return res.body()
        return null
    }

    //Peticion para recopilar los emails y comprobar si existe o no
    suspend fun getEmails(): List<String>?{
        val res = api.getAllEmails();
        if (res.isSuccessful) return res.body()
        return null
    }

    //recuperar password
    suspend fun getCodigo(email: String): Int?{
        val res = api.getCodigo(email)
        if (res.isSuccessful) return res.body()
        return  null
    }

    suspend fun getUpdatePassword(email: String,passwd: String): Boolean?{
        val res = api.getUpdatePassword(UserRequest(email,passwd))
        if (res.isSuccessful) return res.body()
        return null
    }
    //actualizamos el gimmnasio
    suspend fun updateGimnasio(gimnasioModel: GimnasioModel,uuid: UUID){
        api.updateGimnasio(gimnasioModel,uuid)
    }

    //funcion para obtener los usuarios y calcular la estadistica
    suspend fun getAllUsers(): List<UserModel>?{
        val res = api.getAllUser()
        if (res.isSuccessful) return res.body()
        return null
    }

}