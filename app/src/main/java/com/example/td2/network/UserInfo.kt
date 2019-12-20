package com.example.td2.network

import com.squareup.moshi.Json
import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class UserInfo(
    @field:Json(name = "email")
    val email: String? = null,
    @field:Json(name = "firstname")
    val firstname: String,
    @field:Json(name = "lastname")
    val lastname: String,
    @field:Json(name="avatar")
    val avatar: String? = ""

)

