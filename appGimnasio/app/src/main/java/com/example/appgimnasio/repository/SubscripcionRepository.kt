package com.example.appgimnasio.repository

import com.example.appgimnasio.Model.SubscripcionModel
import com.example.appgimnasio.data.Api
import retrofit2.http.Body
import javax.inject.Inject

class SubscripcionRepository @Inject constructor(private val api: Api) {
    suspend fun addSubscripcion(subscripcionModel: SubscripcionModel): Boolean?{
        val res = api.addSubscripcion(subscripcionModel)
        if (res.isSuccessful) return res.body()
        return false
    }
}