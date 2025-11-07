package com.example.nutrifit.data

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val height: String? = null,
    val weight: String? = null,
    val age: String? = null,
    val gender: String? = null
)