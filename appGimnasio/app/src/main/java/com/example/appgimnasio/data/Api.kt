package com.example.appgimnasio.data

import com.example.appgimnasio.Model.EjerciciosModel
import com.example.appgimnasio.Model.EntrenamientosModel
import com.example.appgimnasio.Model.GimnasioModel
import com.example.appgimnasio.Model.RegistroModel
import com.example.appgimnasio.Model.SubscripcionModel
import com.example.appgimnasio.Model.UserModel
import com.example.appgimnasio.Model.UserRegistro
import com.example.appgimnasio.Request.NombreEjercicio
import com.example.appgimnasio.Request.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

//Peticiones de la api
interface Api {
    @POST("usuarios/login")
    suspend fun login(
        @Body request: UserRequest
    ): Response<UserModel>
    @POST("usuarios/registro")
    suspend fun registro(@Body userModel: UserModel): Response<UserModel>

    @GET("usuarios/emails")
    suspend fun getAllEmails(): Response<List<String>>

    @GET("usuarios/")
    suspend fun getAllUser(): Response<List<UserModel>>

    @POST("registro/add")
    suspend fun addRegistro(): Response<Boolean>

    @GET("ejercicios/")
    suspend fun getAllEjercicios(): Response<List<EjerciciosModel>>

    @POST("ejercicios/ejercicio")
    suspend fun getEjercicioName(@Body nombreEjercicio: NombreEjercicio): Response<EjerciciosModel>

    @POST("registros/add")
    suspend fun addRegistro(@Body registro: RegistroModel): Response<RegistroModel>

    @GET("registros/")
    suspend fun getAllRegistros(): Response<List<RegistroModel>>

    @GET("registros/{uuid}")
    suspend fun getAllRegistrosUser(@Path("uuid") uuid: UUID?): Response<List<RegistroModel>>

    @GET("registros/id/{id}")
    suspend fun getByIdRegistro(@Path("id") id: Long): Response<RegistroModel>

    @GET("registros/comentarios/{id}")
    suspend fun getComentarios(@Path("id") id: Long): Response<List<UserRegistro>>

    @POST("registros/comentarios/add")
    suspend fun addComentario(@Body comentario: UserRegistro): Response<Boolean>

    @POST("entrenamientos/add")
    suspend fun addEntrenamiento(@Body entrenamiento: EntrenamientosModel): Response<Boolean>


    @GET("entrenamientos/{uuid}")
    suspend fun getEntrenamientos(@Path("uuid") uuid: UUID?): Response<List<EntrenamientosModel>>


    @DELETE("entrenamientos/delete/{id}")
    suspend fun deleteEntrenamiento(@Path("id") id: Long?)

    @GET("entrenamientos/entreno/{id}")
    suspend fun getEntreno(@Path("id") id: Long?): Response<EntrenamientosModel>

    @GET("send")
    suspend fun getCodigo(@Query("email") email: String): Response<Int>

    @POST("usuarios/update")
    suspend fun getUpdatePassword(@Body request: UserRequest): Response<Boolean>

    @GET("gimnasio/{id}")
    suspend fun getGimansioId(@Path("id")id: Long?): Response<GimnasioModel>

    @POST("subscripcion/add")
    suspend fun addSubscripcion(@Body subscripcion: SubscripcionModel): Response<Boolean>

    @PATCH("usuarios/update/gym/{uuid}")
    suspend fun updateGimnasio(@Body gimnasio: GimnasioModel,@Path("uuid") uuid: UUID?)

    @GET("usuarios/{id}")
    suspend fun getUserByUUID(@Path("id") uuid: UUID?) : Response<UserModel>

    @PATCH("gimnasio/update")
    suspend fun updateGym(@Body gimnasio: GimnasioModel): Response<GimnasioModel>
}