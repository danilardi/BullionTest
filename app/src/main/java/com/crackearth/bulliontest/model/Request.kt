package com.crackearth.bulliontest.model

data class LoginRequest(
    val email: String,
    val password: String,
)

data class RegisterRequest(
    val first_name: String,
    val last_name: String,
    val gender: String,
    val date_of_birth: String,
    val email: String,
    val phone: String,
    val address: String,
    val password: String,
)
