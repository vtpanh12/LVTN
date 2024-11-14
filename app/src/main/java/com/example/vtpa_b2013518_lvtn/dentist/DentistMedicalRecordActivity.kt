package com.example.vtpa_b2013518_lvtn.dentist

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity
import java.util.Calendar

class DentistMedicalRecordActivity : AppCompatActivity() {
    lateinit var tVDentistMRDates: TextView
    lateinit var tVDentistMRNextAppointment: TextView
    lateinit var eTDentistMRTreatment: EditText
    lateinit var eTDentistMRPrescription: EditText
    lateinit var eTDentistMRNotes: EditText
    private lateinit var btnDentistMedicalRecord: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_medical_record)
        tVDentistMRDates = findViewById(R.id.tVDentistMedicalRecordDates)
        tVDentistMRNextAppointment = findViewById(R.id.tVDentistMedicalRecordNextAppointment)
        eTDentistMRTreatment = findViewById(R.id.eTDentistMedicalRecordTreatment)
        eTDentistMRPrescription = findViewById(R.id.eTDentistMedicalRecordPrescription)
        eTDentistMRNotes = findViewById(R.id.eTDentistMedicalRecordNotes)
        btnDentistMedicalRecord = findViewById(R.id.btnDentistMedicalRecord)

        val iVBackMedicalRecordDetail = findViewById<ImageView>(R.id.iVBackMedicalRecordDetail)
        iVBackMedicalRecordDetail.setOnClickListener {
            startActivity(Intent(this, DentistAppointmentActivity::class.java))
        }
        tVDentistMRDates.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear)
                tVDentistMRDates.text = formattedDate
            }, year, month, day)
            // Thiết lập ngày tối thiểu là hôm nay
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }
        tVDentistMRNextAppointment.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear)
                tVDentistMRNextAppointment.text = formattedDate
            }, year, month, day)
            // Thiết lập ngày tối thiểu là hôm nay
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }
        val email = intent.getStringExtra("email")
        val username = intent.getStringExtra("username")
        val service = intent.getStringExtra("service")
        val date = intent.getStringExtra("date")
        val hour = intent.getStringExtra("hour")
        val note = intent.getStringExtra("note")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val status = intent.getStringExtra("status")

        // Hiển thị thông tin lên giao diện hoặc xử lý theo yêu cầu của bạn
        displayAppointmentInfo(email, username, service, date, hour, note, phoneNumber, status)

    }
    private fun displayAppointmentInfo(email: String?, username: String?, service: String?,
                                       date: String?, hour: String?, note: String?,
                                       phoneNumber: String?, status: String?
    ) {
        findViewById<TextView>(R.id.tVDentistMedicalRecordEmail).text = "$email"
        findViewById<TextView>(R.id.tVDentistMedicalRecordlUser).text = "$username"
        findViewById<TextView>(R.id.tVDentistMedicalRecordService).text = "$service"
        findViewById<TextView>(R.id.tVDentistMedicalRecordDate).text = "$date"
        findViewById<TextView>(R.id.tVDentistMedicalRecordHour).text = "$hour"
        findViewById<TextView>(R.id.tVDentistMedicalRecordNote).text = "$note"
        findViewById<TextView>(R.id.tVDentistMedicalRecordPhoneNumber).text = "$phoneNumber"
        findViewById<TextView>(R.id.tVDentistMedicalRecordStatus).text = "$status"
    }
}