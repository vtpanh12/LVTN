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
    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
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


        val tVUserName = findViewById<TextView>(R.id.tVAConfAppUser)
        val tVEmail = findViewById<TextView>(R.id.tVAConfAppEmail)
        val tVAppId = findViewById<TextView>(R.id.tVAConfAppAppId)
        val tVUserId = findViewById<TextView>(R.id.tVAConfAppUserId)
        val tVNote = findViewById<TextView>(R.id.tVAConfAppNote)
        val tVService = findViewById<TextView>(R.id.tVAConfAppService)
        val tVHour = findViewById<TextView>(R.id.tVAConfAppHour)
        val tVDate = findViewById<TextView>(R.id.tVAConfAppDate)
        val tVStatus = findViewById<TextView>(R.id.tVAConfAppStatus)
        val tVPhoneNumber = findViewById<TextView>(R.id.tVAConfAppPhoneNumber)

        tVEmail.text = "Email: ${email}"
        tVAppId.text = "Appointment ID: ${appointmentId}"
        tVUserId.text = "User ID: ${userId}"
        tVUserName.text = "Họ và tên: ${username}"
        tVService.text = "Dịch vụ: ${service}"
        tVDate.text = "Ngày hẹn: ${date}"
        tVHour.text = "Giờ hẹn: ${hour}"
        tVNote.text = "Ghi chú: ${note}"
        tVPhoneNumber.text = "Số điện thoại: ${phoneNumber}"
        tVStatus.text = "Trạng thái: ${status}"

        btnConfApp = findViewById(R.id.btnConfApp)
        val shiftId = determineShiftId(intent.getStringExtra("hour"))

        btnConfApp.setOnClickListener {
            val appointmentId = intent.getStringExtra("appointmentId")

            if (appointmentId != null && service != null && date != null && shiftId != null) {
                selectDentistForAppointment(service, date, shiftId)
            } else {
                Toast.makeText(this, "Thiếu thông tin để chọn bác sĩ  $service $date $shiftId ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Xác định ca trực dựa trên giờ hẹn
    private fun determineShiftId(hour: String?): String? {
        // Tách giờ từ chuỗi "HH:mm"
        val hourOnly = hour?.split(":")?.getOrNull(0)?.toIntOrNull()

        return when (hourOnly) {
            in 8..12 -> "1"  // Ca sáng
            in 13..17 -> "2" // Ca chiều
            else -> null
        }
    }

    private fun selectDentistForAppointment(service: String, date: String, shiftId: String) {
        //so sanh speciatly == service => kq duoc luu vao dentistDocuments
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
                            availableDentists.add(dentistName)
                        }
                        if (dentistDoc == dentistDocuments.last()) {
                            if (availableDentists.isNotEmpty()) {
                                showDentistSelectionDialog(availableDentists)
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

