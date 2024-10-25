package com.example.vtpa_b2013518_lvtn.adapter

data class Appointment(
    var id_app: String = "",
    val id_user: String? =null,
    val email: String? = null,
    val username: String ?=null,
    val service : String ?=null,
    val date: String ?=null,
    val hour : String ?=null,
    val phoneNumber: String ?=null,
    val note : String="",
    val status : String = "Chờ xác nhận"
)
