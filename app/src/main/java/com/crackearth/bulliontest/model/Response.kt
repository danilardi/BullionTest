package com.crackearth.bulliontest.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("data")
    val dataLoginResponse: DataLoginResponse,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("iserror")
    val iserror: Boolean,

    @field:SerializedName("status")
    val status: Int
)

data class DataLoginResponse(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("token")
    val token: String
)

data class RegisterResponse(
    @field:SerializedName("data")
    val dataRegisterResponse: DataRegisterResponse,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("iserror")
    val iserror: Boolean,

    @field:SerializedName("status")
    val status: Int
)

data class DataRegisterResponse(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,
)

data class UsersResponse(

    @field:SerializedName("data")
    val data: List<DataUserResponse>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("iserror")
    val iserror: Boolean,

    @field:SerializedName("status")
    val status: Int
)

data class DataUserResponse(

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("date_of_birth")
    val dateOfBirth: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("photo")
    val photo: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("email")
    val email: String
)

data class DetailUserResponse(

    @field:SerializedName("data")
    val data: DataDetailUserResponse,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("iserror")
    val iserror: Boolean,

    @field:SerializedName("status")
    val status: Int
)

data class DataDetailUserResponse(

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("date_of_birth")
    val dateOfBirth: String,

    @field:SerializedName("first_name")
    val firstName: String,

    @field:SerializedName("last_name")
    val lastName: String,

    @field:SerializedName("photo")
    val photo: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("email")
    val email: String
)
