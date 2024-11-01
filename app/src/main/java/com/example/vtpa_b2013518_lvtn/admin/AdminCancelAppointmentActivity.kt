package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AdminCancelAppointmentActivity : AppCompatActivity() {
    lateinit var btnCancelApp: Button
    val db = Firebase.firestore
    val userCurrentId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_cancel_appointment)

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


        val tVUserName = findViewById<TextView>(R.id.tVACAppUser)
        val tVEmail = findViewById<TextView>(R.id.tVACAppEmail)
        val tVAppId = findViewById<TextView>(R.id.tVACAppAppId)
        val tVUserId = findViewById<TextView>(R.id.tVACAppUserId)
        val tVNote = findViewById<TextView>(R.id.tVACAppNote)
        val tVService = findViewById<TextView>(R.id.tVACAppService)
        val tVHour = findViewById<TextView>(R.id.tVACAppHour)
        val tVDate = findViewById<TextView>(R.id.tVACAppDate)
        val tVStatus = findViewById<TextView>(R.id.tVACAppStatus)
        val tVPhoneNumber = findViewById<TextView>(R.id.tVACAppPhoneNumber)

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


        btnCancelApp = findViewById(R.id.btnCancelApp)
        btnCancelApp.setOnClickListener {
            if (appointmentId != null && userCurrentId != null){
                cancelAppointment(appointmentId, userCurrentId)
            }
        }
    }
    private fun cancelAppointment(appointmentId: String, userCurrentId: String) {
        db.collection("appointments").document(appointmentId)
            .update("status", "Hủy")
            .addOnSuccessListener {
                //sendCancellationNotification(userId)
                Toast.makeText(this, "Lịch khám đã bị hủy thành công!: $appointmentId", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("CancelAppointment", "Error updating document", e)
            }
    }
}