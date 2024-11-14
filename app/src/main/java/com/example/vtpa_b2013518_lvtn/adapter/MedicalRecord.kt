package com.example.vtpa_b2013518_lvtn.adapter

data class MedicalRecord(
    val id_mr: String ?= null,
    val id_app: String ?= null,
    val id_user: String? =null,
    var id_dentist: String? = null,
    val diagnosis: String = "",           // Chẩn đoán
    val treatment: String = "",           // Phương pháp điều trị
    val prescription: String = "",        // Đơn thuốc
    val notes: String = "",               // Ghi chú bổ sung từ nha sĩ
    val date: String = "",                // Ngày tạo hồ sơ bệnh án
    val nextAppointment: String = ""   // Ngày hẹn lần sau (nếu có)
)
