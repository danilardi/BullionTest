package com.crackearth.bulliontest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.crackearth.bulliontest.data.UserPagingSource
import com.crackearth.bulliontest.data.remote.ApiService
import com.crackearth.bulliontest.model.DataItemUsersResponse
import com.crackearth.bulliontest.model.LoginRequest
import com.crackearth.bulliontest.model.LoginResponse
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

    fun getListUser(token: String?): LiveData<PagingData<DataItemUsersResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService, token)
            }
        ).liveData
    }

    companion object {
        const val TAG = "Repository"
    }
}