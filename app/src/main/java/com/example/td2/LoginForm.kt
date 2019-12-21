package com.example.td2

import com.squareup.moshi.Json
import java.io.Serializable

data class LoginForm(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "password")
    val password: String

)