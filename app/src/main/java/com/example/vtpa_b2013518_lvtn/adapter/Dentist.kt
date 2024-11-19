package com.example.vtpa_b2013518_lvtn.adapter

data class Dentist(
    var id_dentist: String? = null,
    val username : String ="",
    val specialty: String ="",
    val date: String ="",
    val phoneNumber : String ?=null,
    val gender : String="",
    val address : String ="",
    val email: String ?= null,
    val role: String ="dentist",
)
