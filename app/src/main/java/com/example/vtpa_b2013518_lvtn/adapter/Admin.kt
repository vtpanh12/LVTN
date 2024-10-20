package com.example.vtpa_b2013518_lvtn.adapter

data class Admin(
    var id_admin: String? = null,    // ID của admin
    val name: String = "",           // Tên admin
    val email: String? = null,       // Email của admin
    val phoneNumber: String? = null, // Số điện thoại của admin
    val role: String = "admin"       // Vai trò admin (role)
)
