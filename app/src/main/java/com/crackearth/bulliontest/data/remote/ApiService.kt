package com.crackearth.bulliontest.data.remote

import com.crackearth.bulliontest.model.LoginRequest
import com.crackearth.bulliontest.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body data: LoginRequest
    ): LoginResponse
}