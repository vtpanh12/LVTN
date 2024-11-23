package com.example.vtpa_b2013518_lvtn.dentist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Shift
import com.example.vtpa_b2013518_lvtn.admin.AdminDentistAssignmentEditActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class DentistAppointmentDetailActivity : AppCompatActivity() {
    private lateinit var btnDentistAppDetail: Button
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_appointment_detail)

        val iVBackDentist = findViewById<ImageView>(R.id.iVBackDentistAppDetail)
        iVBackDentist.setOnClickListener {
            startActivity(Intent(this, DentistAppointmentActivity::class.java))
        }

        btnDentistAppDetail = findViewById(R.id.btnDentistAppDetail)
        val appointmentId = intent.getStringExtra("appointmentId")
        if (appointmentId != null) {
            db.collection("appointments").document(appointmentId).get()
                .addOnSuccessListener { app ->
                    var email = app.getString("email")
                    var username = app.getString("username")
                    var service = app.getString("service")
                    var date = app.getString("date")
                    var hour = app.getString("hour")
                    var phoneNumber = app.getString("phoneNumber")
                    var note = app.getString("note")
                    var status = app.getString("status")
                    displayAppointmentInfo(email, username, service, date, hour, note, phoneNumber, status)
                }.addOnFailureListener{
                    Toast.makeText(this, "Lỗi khi tải thông tin:", Toast.LENGTH_SHORT).show()
                }
        }
            btnDentistAppDetail.setOnClickListener {
                val intent = Intent(this, DentistMedicalRecordActivity::class.java)
                intent.putExtra("email", findViewById<TextView>(R.id.tVDentistAppDetailEmail).text.toString())
                intent.putExtra("username", findViewById<TextView>(R.id.tVDentistAppDetailUser).text.toString())
                intent.putExtra("service", findViewById<TextView>(R.id.tVDentistAppDetailService).text.toString())
                intent.putExtra("date", findViewById<TextView>(R.id.tVDentistAppDetailDate).text.toString())
                intent.putExtra("hour", findViewById<TextView>(R.id.tVDentistAppDetailHour).text.toString())
                intent.putExtra("note", findViewById<TextView>(R.id.tVDentistAppDetailNote).text.toString())
                intent.putExtra("phoneNumber", findViewById<TextView>(R.id.tVDentistAppDetailPhoneNumber).text.toString())
                intent.putExtra("status", findViewById<TextView>(R.id.tVDentistAppDetailStatus).text.toString())
                intent.putExtra("appointmentId", appointmentId)
                startActivity(intent)

        }
    }
    private fun displayAppointmentInfo(email: String?, username: String?, service: String?,
                                       date: String?, hour: String?, note: String?,
                                       phoneNumber: String?, status: String?
    ) {
        findViewById<TextView>(R.id.tVDentistAppDetailEmail).text = "Email: $email"
        findViewById<TextView>(R.id.tVDentistAppDetailUser).text = "Họ và tên: $username"
        findViewById<TextView>(R.id.tVDentistAppDetailService).text = "Dịch vụ: $service"
        findViewById<TextView>(R.id.tVDentistAppDetailDate).text = "Ngày hẹn: $date"
        findViewById<TextView>(R.id.tVDentistAppDetailHour).text = "Giờ hẹn: $hour"
        findViewById<TextView>(R.id.tVDentistAppDetailNote).text = "Ghi chú: $note"
        findViewById<TextView>(R.id.tVDentistAppDetailPhoneNumber).text = "Số điện thoại: $phoneNumber"
        findViewById<TextView>(R.id.tVDentistAppDetailStatus).text = "Trạng thái: $status"
    }
}