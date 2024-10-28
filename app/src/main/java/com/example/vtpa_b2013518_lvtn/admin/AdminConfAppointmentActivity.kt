package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R

class AdminConfAppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_conf_appointment)
        val iVBackAdminApp = findViewById<ImageView>(R.id.iVBackAdminApp)
        iVBackAdminApp.setOnClickListener {
            finish()
        }

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
    }
}