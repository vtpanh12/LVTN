package com.example.vtpa_b2013518_lvtn.dentist

import android.content.Intent
import android.os.Bundle
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class DentistAppointmentDetailActivity : AppCompatActivity() {
    private lateinit var btnDentistAppDetail: Button
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    val dentistCurrentId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_appointment_detail)

        val iVBackDentist = findViewById<ImageView>(R.id.iVBackDentistAppDetail)
        iVBackDentist.setOnClickListener {
            startActivity(Intent(this, DentistAppointmentActivity::class.java))
        }
        val appointmentId = intent.getStringExtra("appointmentId")
        findViewById<TextView>(R.id.tVDentistAppDetailUser).text = "Nha sĩ: $appointmentId"
    }
    private fun displayAppointmentInfo(email: String?, appointmentId: String?, userId: String?,
                                       username: String?, service: String?, date: String?, hour: String?,
                                       note: String?, phoneNumber: String?, status: String?
    ) {
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
}