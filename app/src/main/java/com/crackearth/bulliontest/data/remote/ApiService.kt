package com.crackearth.bulliontest.data.remote

import com.crackearth.bulliontest.model.DetailUserResponse
import com.crackearth.bulliontest.model.LoginRequest
import com.crackearth.bulliontest.model.LoginResponse
import com.crackearth.bulliontest.model.RegisterResponse
import com.crackearth.bulliontest.model.UsersResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body data: LoginRequest
    ): LoginResponse

    @Multipart
    @POST("auth/register")
    suspend fun register(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part
    ): RegisterResponse

    @GET("admin")
    suspend fun getListUser(
        @Header("Authorization") Authorization: String? = null,
    ): UsersResponse

    @GET("admin/{id}")
    suspend fun getDetailUser(
        @Header("Authorization") Authorization: String? = null,
        @Path("id") id: String
    ): DetailUserResponse
}