package com.crackearth.bulliontest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.crackearth.bulliontest.data.remote.ApiService
import com.crackearth.bulliontest.model.DetailUserResponse
import com.crackearth.bulliontest.model.LoginRequest
import com.crackearth.bulliontest.model.LoginResponse
import com.crackearth.bulliontest.model.RegisterRequest
import com.crackearth.bulliontest.model.RegisterResponse
import com.crackearth.bulliontest.model.UsersResponse
import com.crackearth.bulliontest.utils.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

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

    fun register(
        file: File,
        data: RegisterRequest
    ): LiveData<Response<RegisterResponse>> = liveData {
        emit(Response.Loading)
        try {
            val requestBody = HashMap<String, RequestBody>()
            requestBody["first_name"] = data.first_name.toRequestBody("text/plain".toMediaType())
            requestBody["last_name"] = data.last_name.toRequestBody("text/plain".toMediaType())
            requestBody["gender"] = data.gender.toRequestBody("text/plain".toMediaType())
            requestBody["date_of_birth"] = data.date_of_birth.toRequestBody("text/plain".toMediaType())
            requestBody["email"] = data.email.toRequestBody("text/plain".toMediaType())
            requestBody["phone"] = data.phone.toRequestBody("text/plain".toMediaType())
            requestBody["address"] = data.address.toRequestBody("text/plain".toMediaType())
            requestBody["password"] = data.password.toRequestBody("text/plain".toMediaType())

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile,
            )

            val response = apiService
                .register(requestBody, imageMultipart)
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
            Log.d(TAG, "resigter: ${e.message.toString()}")
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

    fun getDetailUser(token: String?, id: String): LiveData<Response<DetailUserResponse>> =
        liveData {
            emit(Response.Loading)
            try {
                val response = apiService
                    .getDetailUser(token, id)
                emit(Response.Success(response))
            } catch (e: Exception) {
                emit(Response.Error(e.message.toString()))
            }
        }

    companion object {
        const val TAG = "Repository"
    }
}