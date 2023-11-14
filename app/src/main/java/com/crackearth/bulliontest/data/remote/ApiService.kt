package com.crackearth.bulliontest.data.remote

import com.crackearth.bulliontest.model.LoginRequest
import com.crackearth.bulliontest.model.LoginResponse
import com.crackearth.bulliontest.model.UsersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body data: LoginRequest
    ): LoginResponse

    @GET("admin")
    suspend fun getListUser(
        @Header("Authorization") Authorization: String? = null,
    ) : UsersResponse
}