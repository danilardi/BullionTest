package com.crackearth.bulliontest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.crackearth.bulliontest.data.remote.ApiService
import com.crackearth.bulliontest.model.LoginRequest
import com.crackearth.bulliontest.model.LoginResponse
import com.crackearth.bulliontest.model.UsersResponse
import com.crackearth.bulliontest.utils.Response

class Repository(private val apiService: ApiService) {

    fun login(data: LoginRequest): LiveData<Response<LoginResponse>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiService
                .login(data)
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
            Log.d(TAG, "login: ${e.message.toString()}")
        }
    }

    fun getListUser(token: String?): LiveData<Response<UsersResponse>> =
        liveData {
            emit(Response.Loading)
            try {
                val response = apiService
                    .getListUser(token)
                emit(Response.Success(response))
            } catch (e: Exception) {
                emit(Response.Error(e.message.toString()))
            }
        }

    companion object {
        const val TAG = "Repository"
    }
}