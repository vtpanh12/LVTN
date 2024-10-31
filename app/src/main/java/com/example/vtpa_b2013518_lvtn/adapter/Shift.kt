package com.example.vtpa_b2013518_lvtn.adapter

data class Shift(
    val shiftId: Int,            // ID của ca trực (1 hoặc 2)
    val startTime: String,       // Thời gian bắt đầu ca, định dạng HH:mm
    val endTime: String,         // Thời gian kết thúc ca, định dạng HH:mm
    val schedules: List<Schedule> // Danh sách lịch khám của ca trực
)
