package com.crackearth.bulliontest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.crackearth.bulliontest.data.local.AuthPreferences
import com.crackearth.bulliontest.model.DataLoginResponse
import com.crackearth.bulliontest.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val pref: AuthPreferences, private val repository: Repository): ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    fun getListUser(token: String?) = repository.getListUser(token)

    fun getDetailUser(token: String?, id: String) = repository.getDetailUser(token, id)
    fun getAuth(): LiveData<DataLoginResponse> {
        return pref.getAuth().asLiveData()
    }

    fun clearSession() {
        viewModelScope.launch {
            pref.clearSession()
        }
    }
}