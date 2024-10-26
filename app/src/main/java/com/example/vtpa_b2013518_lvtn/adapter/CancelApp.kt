package com.example.vtpa_b2013518_lvtn.adapter

data class CancelApp(
    val id_app: String,   // ID của lịch hẹn bị hủy
    val cancelReason: String,    // Lý do hủy
    val canceledBy: String,      // Người hủy (user, admin, dentist)
    val cancelDate: String       // Ngày hủy
)
