package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
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
            Toast.makeText(this, "$service, $date, $shiftId, $hour", Toast.LENGTH_SHORT).show()
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
    private fun displayAppointmentInfo(email: String?, appointmentId: String?, userId: String?, username: String?, service: String?, date: String?, hour: String?, note: String?, phoneNumber: String?, status: String?) {
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
            in 8..12 -> "1"  // Ca sáng
            in 13..17 -> "2" // Ca chiều
            else -> null
        }
    }

    private fun selectDentistForAppointment(service: String, date: String, shiftId: String, appointmentHour: String, appointmentId: String) {
        db.collection("dentists").whereEqualTo("specialty", service).get()
            .addOnSuccessListener { dentistDocuments ->
                val availableDentists = mutableListOf<String>()

                // Duyệt từng bác sĩ và kiểm tra lịch trống
                for (dentistDoc in dentistDocuments) {
                    val dentistId = dentistDoc.id
                    val dentistName = dentistDoc.getString("username") ?: "Unknown Dentist"

                    val shiftRef = db.collection("dentists").document(dentistId)
                        .collection("shifts").document("${date}_$shiftId")

                    shiftRef.get().addOnSuccessListener { shiftDoc ->
                        if (shiftDoc.exists()) {
                            val shift = shiftDoc.toObject(Shift::class.java)
                            val matchingSlotKey = findMatchingSlot(appointmentHour, shift?.slots ?: emptyMap())
                            if (matchingSlotKey != null) {
                                // Cập nhật slot
                                Toast.makeText(this,
                                        "$dentistName vào giờ $appointmentHour", Toast.LENGTH_SHORT).show()
                                bookSlot(dentistId, date, shiftId, matchingSlotKey, appointmentId)
                                availableDentists.add(dentistName)
                            }
                            else{
                                Toast.makeText(this,
                                        "$dentistName vào giờ $appointmentHour", Toast.LENGTH_SHORT).show()
                            }
                        }
                        // Kiểm tra tất cả bác sĩ đã được duyệt
                        if (dentistDoc == dentistDocuments.last()) {
                            if (availableDentists.isNotEmpty()) {
                                showDentistSelectionDialog(availableDentists)
                            } else {

                                //Toast.makeText(this, "$service, $date, $shiftId", Toast.LENGTH_SHORT).show()
                                //Toast.makeText(this, "Không có bác sĩ phù hợp", Toast.LENGTH_SHORT).show()
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
        val appointmentHour = appointmentTime.substringBefore(":").toIntOrNull()

        return appointmentHour?.let {
            // Định dạng giờ thành chuỗi "HH:00"
            val formattedHour = String.format("%02d:00", appointmentHour)

            // Tìm kiếm khóa định dạng trong shiftSlots
            shiftSlots.keys.find { slotHour -> slotHour == formattedHour }
        }
    }


    // Cập nhật slot khi đặt lịch
    private fun bookSlot(dentistId: String, date: String, shiftId: String, slotKey: String, appointmentId: String) {
        val slotRef = db.collection("dentists").document(dentistId)
            .collection("shifts").document("${date}_$shiftId")

        slotRef.get().addOnSuccessListener { shiftDoc ->
            if (shiftDoc.exists()) {
                val shift = shiftDoc.toObject(Shift::class.java)
                val slots = shift?.slots?.toMutableMap() ?: mutableMapOf()
                slots[slotKey]?.isBooked = true
                slots[slotKey]?.id_app = appointmentId // Gán ID lịch khám

                // Cập nhật lại ca trực với slot đã được đặt
                slotRef.update("slots", slots).addOnSuccessListener {
                    Toast.makeText(this, "Đặt lịch thành công!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Lỗi khi cập nhật slot", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Lỗi khi truy vấn ca trực", Toast.LENGTH_SHORT).show()
        }
    }

    // Hiển thị danh sách bác sĩ phù hợp trong Dialog
    private fun showDentistSelectionDialog(availableDentists: List<String>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Chọn bác sĩ")
        builder.setItems(availableDentists.toTypedArray()) { _, which ->
            val selectedDentist = availableDentists[which]
            Toast.makeText(this, "Bác sĩ đã chọn: $selectedDentist", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Hủy") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }
}



