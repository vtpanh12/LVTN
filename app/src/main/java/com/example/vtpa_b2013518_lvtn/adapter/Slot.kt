package com.example.vtpa_b2013518_lvtn.adapter

data class Slot(
    var isBooked: Boolean = false,
    var id_app: String? = null,

)
//    val id_user:String?,
//    val timeSlot: String?
//fun generateEmptySlots(): Map<String, Slot> {
//    val slots = mutableMapOf<String, Slot>()
//    for (hour in 7..11) { // Tạo slot từ 7h đến 11h
//        val timeSlot = "$hour h"
//        slots[timeSlot] = Slot() // Khởi tạo slot với isBooked = false
//    }
//    return slots
//}
