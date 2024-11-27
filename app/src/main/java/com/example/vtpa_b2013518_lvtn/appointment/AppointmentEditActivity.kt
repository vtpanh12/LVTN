package com.example.vtpa_b2013518_lvtn.appointment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity

class AppointmentEditActivity : AppCompatActivity() {
    private lateinit var tVAECancelApp: TextView
    private lateinit var tVAEEdit: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_edit)

        tVAECancelApp = findViewById(R.id.tVAECancelApp)
        tVAEEdit = findViewById(R.id.tVAEEdit)
        val linearAppEdit = findViewById<LinearLayout>(R.id.linearAppEdit)
        val iVBackDentistMedicalRecord = findViewById<ImageView>(R.id.iVBackAppEdit)
        iVBackDentistMedicalRecord.setOnClickListener {
            startActivity(Intent(this, Dat_KhamActivity::class.java))
        }
        val dentistID = intent.getStringExtra("dentistId")
        val email = intent.getStringExtra("email")
        val username = intent.getStringExtra("username")
        val date = intent.getStringExtra("date")
        val hour = intent.getStringExtra("hour")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val note = intent.getStringExtra("notes")
        val status = intent.getStringExtra("status")
        val service = intent.getStringExtra("service")
        displayAppointmentInfo(email, username, date, hour, phoneNumber, note,
            service, status, dentistID)
        tVAEEdit.setOnClickListener {
            linearAppEdit.visibility = View.VISIBLE
        }

    }
    private fun displayAppointmentInfo(email: String?, username: String?,
                                       date: String?, hour: String?, phoneNumber: String?,
                                       note: String?, service: String?, status: String?,
                                       dentistID: String?
    ) {
        findViewById<TextView>(R.id.tVAEEmail).text = "Email: $email"
        findViewById<TextView>(R.id.tVAEUser).text = "Họ và tên khách hàng:: $username"
        findViewById<TextView>(R.id.tVAEPhoneNumber).text = "Số điện thoại: $phoneNumber"
        findViewById<TextView>(R.id.tVAEDate).text = "Ngày hẹn: $date"
        findViewById<TextView>(R.id.tVAEHour).text = "Giờ hẹn: $hour"
        findViewById<TextView>(R.id.tVAENote).text = " Ghi chú: $note"
        findViewById<TextView>(R.id.tVAEStatus).text = "Trạng thái: $status"
        findViewById<TextView>(R.id.tVAEService).text = " Dịch vụ: $service"
        findViewById<TextView>(R.id.tVAEDentistId).text = "ID Nha sĩ: $dentistID"
        //findViewById<TextView>(R.id.tVAEDentistUsername).text = "Nha sĩ: $dentistUsername"
        //findViewById<TextView>(R.id.tVAEDentistInfo).text = "Thông tin nha sĩ: $dentistInfo"

    }
}