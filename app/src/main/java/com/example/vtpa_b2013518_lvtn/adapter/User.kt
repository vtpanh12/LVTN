package com.example.vtpa_b2013518_lvtn.adapter

data class User(
    val username : String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    // Giá trị mặc định là "user", có thể là "admin" phân quyền user
    val role: String = "user"
)
