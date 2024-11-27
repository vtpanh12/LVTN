package com.example.vtpa_b2013518_lvtn.appointment

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R

class AppointmentEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_edit)
    }
    private fun displayAppointmentInfo(email: String?, username: String?,
                                       date: String?, hour: String?, phoneNumber: String?,
                                       note: String?, service: String?, status: String?,
                                       dentistUsername: String?, dentistID: String?,
                                      dentistInfo: String?
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
        findViewById<TextView>(R.id.tVAEDentistUsername).text = "Nha sĩ: $dentistUsername"
        findViewById<TextView>(R.id.tVAEDentistInfo).text = "Thông tin nha sĩ: $dentistInfo"

    }
}