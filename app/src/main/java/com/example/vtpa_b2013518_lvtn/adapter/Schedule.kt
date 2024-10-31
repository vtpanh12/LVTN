package com.example.vtpa_b2013518_lvtn.adapter

data class Schedule(
    val timeSlot: String,        // Thời gian khám trong ca, định dạng HH:mm
    val id_user: String? = null, // ID của bệnh nhân nếu có lịch khám, nếu trống thì null
    val id_app: String =""

    //val description: String? = null // Mô tả thêm về lịch khám
)
