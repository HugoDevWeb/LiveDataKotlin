package com.example.td2

import com.squareup.moshi.Json

data class SignupForm(
    @field:Json(name = "firstname")
    val firstname: String,
    @field:Json(name = "lastname")
    val lastname: String,
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "password")
    val password: String,
    @field:Json(name = "confirm password")
    val confirmPassword: String

)