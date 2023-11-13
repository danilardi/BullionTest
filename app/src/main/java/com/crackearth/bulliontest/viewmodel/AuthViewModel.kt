package com.crackearth.bulliontest.viewmodel

import androidx.lifecycle.ViewModel
import com.crackearth.bulliontest.model.LoginRequest
import com.crackearth.bulliontest.repository.Repository

class AuthViewModel(private val repository: Repository): ViewModel() {
    fun login(data: LoginRequest) = repository.login(data)
}