package com.example.td2

import java.io.Serializable
import com.squareup.moshi.Json

data class Task(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "description")
    val description: String = "Description content") : Serializable