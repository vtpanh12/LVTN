package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.example.vtpa_b2013518_lvtn.adapter.Shift
import com.example.vtpa_b2013518_lvtn.adapter.Slot
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AdminConfAppointmentActivity : AppCompatActivity() {
    private lateinit var btnConfApp: Button
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    val userCurrentId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_conf_appointment)

        val iVBackAdminApp = findViewById<ImageView>(R.id.iVBackAdminApp)
        iVBackAdminApp.setOnClickListener {
            finish()
        }

        btnConfApp = findViewById(R.id.btnConfApp)
        val email = intent.getStringExtra("email")
        val appointmentId = intent.getStringExtra("appointmentId")
        val userId = intent.getStringExtra("userId")
        val username = intent.getStringExtra("username")
        val service = intent.getStringExtra("service")
        val date = intent.getStringExtra("date")
        val hour = intent.getStringExtra("hour")
        val note = intent.getStringExtra("note")
        val status = intent.getStringExtra("status")
        val phoneNumber = intent.getStringExtra("phoneNumber")

        // Hiển thị thông tin cuộc hẹn
        displayAppointmentInfo(email, appointmentId, userId, username, service, date, hour, note, phoneNumber, status)

        btnConfApp.setOnClickListener {
            val shiftId = determineShiftId(hour)
            //Toast.makeText(this, "$service, $date, $shiftId, $hour", Toast.LENGTH_SHORT).show()
            if (appointmentId != null && service != null && date != null && shiftId != null) {
                if (hour != null) {
                    selectDentistForAppointment(service, date, shiftId, hour, appointmentId)
                }
            } else {
                Toast.makeText(this, "Thiếu thông tin để chọn bác sĩ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hiển thị thông tin cuộc hẹn
    private fun displayAppointmentInfo(email: String?, appointmentId: String?, userId: String?,
        username: String?, service: String?, date: String?, hour: String?, note: String?, phoneNumber: String?, status: String?) {
        findViewById<TextView>(R.id.tVAConfAppEmail).text = "Email: $email"
        findViewById<TextView>(R.id.tVAConfAppAppId).text = "Appointment ID: $appointmentId"
        findViewById<TextView>(R.id.tVAConfAppUserId).text = "User ID: $userId"
        findViewById<TextView>(R.id.tVAConfAppUser).text = "Họ và tên: $username"
        findViewById<TextView>(R.id.tVAConfAppService).text = "Dịch vụ: $service"
        findViewById<TextView>(R.id.tVAConfAppDate).text = "Ngày hẹn: $date"
        findViewById<TextView>(R.id.tVAConfAppHour).text = "Giờ hẹn: $hour"
        findViewById<TextView>(R.id.tVAConfAppNote).text = "Ghi chú: $note"
        findViewById<TextView>(R.id.tVAConfAppPhoneNumber).text = "Số điện thoại: $phoneNumber"
        findViewById<TextView>(R.id.tVAConfAppStatus).text = "Trạng thái: $status"
    }

    // Xác định ca trực dựa trên giờ hẹn
    private fun determineShiftId(hour: String?): String? {
        val hourInt = hour?.substringBefore(":")?.toIntOrNull()
        return when (hourInt) {
            in 7..12 -> "1"  // Ca sáng
            in 13..18 -> "2" // Ca chiều
            else -> null
        }
    }

    private fun selectDentistForAppointment(service: String, date: String, shiftId: String, appointmentHour: String, appointmentId: String) {
        db.collection("dentists").whereEqualTo("specialty", service).get()
            .addOnSuccessListener { dentists ->
                //list dentist co lich trong
                val availableDentists = mutableListOf<String>()
                val availableDentistsDetails = mutableListOf<Triple<String, String, String>>()

                // Duyệt từng bác sĩ và kiểm tra lịch trống
                for (dentist in dentists) {
                    val dentistId = dentist.id
                    val dentistName = dentist.getString("username") ?: "khong co nha si phu hop"
                    val dentistEmail = dentist.getString("email")?:"khong co email"
                    val dentistPhoneNumber = dentist.getString("phoneNumber")?:"khong co sdt"
                    if (userCurrentId != null) {
                        confAppointment(appointmentId, userCurrentId, dentistId)
                    }
                    //tim shift dua tren date tren lich kham
                    val shift = db.collection("dentists").document(dentistId)
                        .collection("shifts").document("${date}_$shiftId")

                    shift.get().addOnSuccessListener { shiftDoc ->
                        if (shiftDoc.exists()) {
                            //chuyen doi Shift thanh oop shift
                            val shift = shiftDoc.toObject(Shift::class.java)
                            val matchingSlotKey = findMatchingSlot(appointmentHour, shift?.slots ?: emptyMap())
                            if (matchingSlotKey != null) {
                                val slotBooked = bookSlot(dentistId, date, shiftId, matchingSlotKey, appointmentId)
                                if (slotBooked) {
                                    availableDentists.add(dentistName)
                                    availableDentistsDetails.add(Triple(dentistName, dentistEmail, dentistPhoneNumber))
                                    //break // Dừng lại nếu tìm thấy bác sĩ phù hợp
                                }
                            }
                        }
                        // Kiểm tra tất cả bác sĩ đã được duyệt
                        if (dentist == dentists.last()) {
                            if (availableDentists.isNotEmpty()) {
                                showDentistSelectionDialog(availableDentists, availableDentistsDetails)
                            } else {
                                Toast.makeText(this, "Không có bác sĩ phù hợp", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this, "Lỗi khi truy vấn ca trực", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Lỗi khi truy vấn danh sách bác sĩ", Toast.LENGTH_SHORT).show()
            }
    }

    // Tìm slot phù hợp với giờ hẹn
    private fun findMatchingSlot(appointmentTime: String, shiftSlots: Map<String, Slot>): String? {
        //lay gio
        val appointmentHour = appointmentTime.substringBefore(":").toIntOrNull()

        return appointmentHour?.let {
            // Định dạng giờ thành chuỗi "HH:00"
            val formattedHour = String.format("%02d:00", appointmentHour)

            // Tìm kiếm khóa định dạng trong shiftSlots
            shiftSlots.keys.find { slotHour -> slotHour == formattedHour }
        }
    }

    private fun getInfoAppointment(appointmentId: String, dentistId: String, date: String, shiftId: String){
        val slot = db.collection("dentists").document(dentistId)
            .collection("shifts").document("${date}_$shiftId")
        slot.get().addOnSuccessListener {shiftDoc ->
            //kiem tra xem shift co ton tai khong
            if (shiftDoc.exists()){
                val shift = shiftDoc.toObject(Shift::class.java)
                //fliterValues: loc cac slot da duoc dat
                val bookedAppointments = shift?.slots?.filterValues { it.isBooked == true } // Lọc các slot đã đặt
                // forEach Duyệt qua tung slot đã được đặt và lấy thông tin appointment
                bookedAppointments?.forEach { (slotKey, slot) ->
                    val appointmentId = slot.id_app

                    if (appointmentId != null) {
                        db.collection("appointments").document(appointmentId).get()
                            .addOnSuccessListener { appointmentDoc ->
                                if (appointmentDoc.exists()) {
                                    val appointment = appointmentDoc.toObject(Appointment::class.java)
                                    // Xử lý appointment ở đây, ví dụ: hiển thị hoặc thêm vào danh sách
                                    Log.d("Appointment", "Cuộc hẹn: ${appointment}")
                                }
                            }.addOnFailureListener {
                                Log.e("Error", "Không thể lấy thông tin cuộc hẹn với ID: $appointmentId")
                            }
                    }
                }
            }
        }.addOnFailureListener {
            Log.e("Error", "Lỗi khi truy vấn ca trực")
            }
        }

    // Cập nhật slot khi đặt lịch

    private fun bookSlot(dentistId: String, date: String, shiftId: String, slotKey: String, appointmentId: String): Boolean {
        val slotRef = db.collection("dentists").document(dentistId)
            .collection("shifts").document("${date}_$shiftId")
        var slotBooked = false

        // Lấy dữ liệu ca trực (shift)
        slotRef.get().addOnSuccessListener { shiftDoc ->
            if (shiftDoc.exists()) {
                val shift = shiftDoc.toObject(Shift::class.java)
                val slots = shift?.slots?.toMutableMap() ?: mutableMapOf()
                val selectedSlot = slots[slotKey]

                if (selectedSlot?.isBooked == true) {
                    // Slot đã đầy, báo về false để hàm gọi bỏ qua bác sĩ này
                    slotBooked = false
                } else {
                    slots[slotKey]?.isBooked = true     // Đánh dấu đã đặt lịch khám
                    slots[slotKey]?.id_app = appointmentId // Gán ID lịch khám

                    // Cập nhật lại ca trực với slot đã được đặt
                    slotRef.update("slots", slots).addOnSuccessListener {
                        Toast.makeText(this, "Đã cập nhật ca trực!", Toast.LENGTH_SHORT).show()
                        slotBooked = true // Đặt thành công
                    }.addOnFailureListener {
                        Toast.makeText(this, "Lỗi khi cập nhật slot", Toast.LENGTH_SHORT).show()
                        slotBooked = false
                    }
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Lỗi khi truy vấn ca trực", Toast.LENGTH_SHORT).show()
            slotBooked = false
        }
        return slotBooked
    }


    // Hiển thị danh sách bác sĩ phù hợp trong Dialog
    private fun showDentistSelectionDialog(availableDentists: List<String>, availableDentistsDetails: List<Triple<String, String, String>>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Chọn bác sĩ")
        builder.setItems(availableDentists.toTypedArray()) { _, which ->
            // Lấy thông tin của bác sĩ đã chọn
            val selectedDentist = availableDentistsDetails[which]
            val dentistName = selectedDentist.first
            val dentistEmail = selectedDentist.second
            val dentistPhoneNumber = selectedDentist.third

            // Cập nhật các TextView trên giao diện với thông tin bác sĩ đã chọn
            findViewById<TextView>(R.id.tVAConfAppDentist).text = "Nha sĩ: $dentistName"
            //findViewById<TextView>(R.id.tVAConfAppDentistEmail).text = " |  $dentistEmail"
            findViewById<TextView>(R.id.tVAConfAppDentistPhoneNumber).text = " |  $dentistPhoneNumber"

            Toast.makeText(this, "Bác sĩ đã chọn: $dentistName", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Hủy") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun confAppointment(appointmentId: String, userCurrentId: String, dentistId: String) {
        db.collection("appointments").document(appointmentId)
            .update("status", "Đặt lịch thành công", "id_dentist", dentistId )
            .addOnSuccessListener {
                //sendCancellationNotification(userId)
                //Toast.makeText(this, "Đặt lịch thành công!: $appointmentId", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("CancelAppointment", "Error updating document", e)
            }
    }
}
