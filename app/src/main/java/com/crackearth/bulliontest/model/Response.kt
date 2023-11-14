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

data class UsersResponse(

	@field:SerializedName("data")
	val data: List<DataItemUsersResponse>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("iserror")
	val iserror: Boolean,

	@field:SerializedName("status")
	val status: Int
)

data class DataItemUsersResponse(

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
