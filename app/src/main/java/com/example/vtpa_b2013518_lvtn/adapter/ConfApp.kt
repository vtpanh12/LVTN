package com.example.vtpa_b2013518_lvtn.adapter

data class ConfApp(
    val id_app: String,   // ID của lịch hẹn
    val confirmedBy: String,     // Người xác nhận (admin, dentist)
    val confirmationStatus: Boolean = false, // Trạng thái xác nhận
    val confirmationDate: String? = null     // Ngày xác nhận, nếu có
)
