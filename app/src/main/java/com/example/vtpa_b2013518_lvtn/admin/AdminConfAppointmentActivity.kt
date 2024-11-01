package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
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

        btnConfApp.setOnClickListener {

        }
    }




}