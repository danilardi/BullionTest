package com.crackearth.bulliontest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crackearth.bulliontest.data.local.AuthPreferences
import com.crackearth.bulliontest.model.DataLoginResponse
import com.crackearth.bulliontest.model.LoginRequest
import com.crackearth.bulliontest.repository.Repository
import kotlinx.coroutines.launch

class AuthViewModel(private val pref: AuthPreferences,  private val repository: Repository): ViewModel() {
    fun login(data: LoginRequest) = repository.login(data)
    fun setAuth(data: DataLoginResponse) {
        viewModelScope.launch {
            pref.setAuth(data)
        }
    }
}