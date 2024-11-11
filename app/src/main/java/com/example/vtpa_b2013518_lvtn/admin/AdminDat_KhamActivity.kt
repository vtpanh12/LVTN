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
//private fun selectDentistForAppointment(
//    service: String,
//    date: String,
//    shiftId: String,
//    appointmentHour: String,
//    appointmentId: String
//) {
//    //truy cập  vào db: dentists, duyệt qua các bác sĩ có chuyên khoa = service, lấy thông tin
//    db.collection("dentists").whereEqualTo("specialty", service).get()
//        .addOnSuccessListener { dentists ->
//            //list dentist co lich trong
//            val availableDentists = mutableListOf<String>()
//
//            // Duyệt từng bác sĩ và kiểm tra lịch trống, lấy id
//            for (dentist in dentists) {
//                val dentistId = dentist.id
//                //tim shift dua tren date của lich kham
//                val shift = db.collection("dentists").document(dentistId)
//                    .collection("shifts").document("${date}_$shiftId")
//
//                shift.get().addOnSuccessListener { shiftDoc ->
//                    if (shiftDoc.exists()) {
//
//                        val shift = shiftDoc.toObject(Shift::class.java)
//                        val slots = shift?.slots?.toMutableMap() ?: mutableMapOf()
//                        val matchingSlotKey =
//                            findMatchingSlot(appointmentHour, shift?.slots ?: emptyMap())
//                        val selectedSlot = slots[matchingSlotKey]
//                        //shiftSlot == appointment Hour
//                        //matchingSlotKey: match giờ giữa app và dentist slot.key
//                        if (selectedSlot?.isBooked == false){
//                            availableDentists.add(dentistId)
//                        }
////                            if (matchingSlotKey != null) {
////                                bookSlot(dentistId, date, shiftId, matchingSlotKey, appointmentId) { success ->
////                                    if (success) {
////                                        availableDentists.add(dentistId)
////                                    } else {
////                                        // Slot không thể đặt hoặc xảy ra lỗi
////                                        Toast.makeText(this, "Lỗi không thể đặt lịch (bookSlot)", Toast.LENGTH_SHORT)
////                                            .show()
////                                    }
////                                }
////                            }
//                    }
//                    // Kiểm tra tất cả bác sĩ đã được duyệt
//                    if (dentist == dentists.last()) {
//                        if (availableDentists.isNotEmpty()) {
//                            showDentistSelectionDialog(availableDentists)
//                        } else {
//                            Toast.makeText(this, "Không có bác sĩ phù hợp", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }
//                }.addOnFailureListener {
//                    Toast.makeText(this, "Lỗi khi truy vấn ca trực", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }.addOnFailureListener {
//            Toast.makeText(this, "Lỗi khi truy vấn danh sách bác sĩ", Toast.LENGTH_SHORT).show()
//        }
//}