package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Shift

class AdminDat_KhamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_dat_kham)

    }
}
// Cập nhật slot khi đặt lịch
//private fun bookSlot(
//    dentistId: String,
//    date: String,
//    shiftId: String,
//    slotKey: String,
//    appointmentId: String,
//    callback: (Boolean) -> Unit
//) {
//    val slotRef = db.collection("dentists").document(dentistId)
//        .collection("shifts").document("${date}_$shiftId")
//
//    // Lấy dữ liệu ca trực (shift)
//    slotRef.get().addOnSuccessListener { shiftDoc ->
//        if (shiftDoc.exists()) {
//            val shift = shiftDoc.toObject(Shift::class.java)
//            val slots = shift?.slots?.toMutableMap() ?: mutableMapOf()
//            val selectedSlot = slots[slotKey]
//
//            if (selectedSlot?.isBooked == true) {
//                // Slot đã đầy, báo về false để hàm gọi bỏ qua bác sĩ này
//                callback(false)
//            } else {
//                // Cập nhật slot thành đã được đặt và gán ID lịch khám
//                slots[slotKey]?.isBooked = true
//                slots[slotKey]?.id_app = appointmentId
//
//                // Cập nhật lại ca trực với slot đã được đặt
//                slotRef.update("slots", slots).addOnSuccessListener {
//                    Toast.makeText(this, "Đã cập nhật ca trực!", Toast.LENGTH_SHORT).show()
//                    callback(true) // Đặt thành công
//                }.addOnFailureListener {
//                    Toast.makeText(this, "Lỗi khi cập nhật slot", Toast.LENGTH_SHORT).show()
//                    callback(false)
//                }
//            }
//        } else {
//            Toast.makeText(this, "Ca trực không tồn tại", Toast.LENGTH_SHORT).show()
//            callback(false)
//        }
//    }.addOnFailureListener {
//        Toast.makeText(this, "Lỗi khi truy vấn ca trực", Toast.LENGTH_SHORT).show()
//        callback(false)
//    }
//}
