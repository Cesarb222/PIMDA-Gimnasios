package com.example.appgimnasio.repository

import com.example.appgimnasio.Model.GimnasioModel
import com.example.appgimnasio.data.Api
import javax.inject.Inject

class GimnasioRepository @Inject constructor(private val api: Api) {
    suspend fun getGimnasioId(id: Long): GimnasioModel?{
        val res = api.getGimansioId(id)
        if (res.isSuccessful) return res.body()
        return null
    }

    suspend fun update(gimnasioModel: GimnasioModel): GimnasioModel?{
        val res = api.updateGym(gimnasioModel)
        if (res.isSuccessful) return res.body()
        return null
    }
}