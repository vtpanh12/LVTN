package com.example.vtpa_b2013518_lvtn.dentist

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Shift
import com.example.vtpa_b2013518_lvtn.adapter.Slot
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DentistAppointmentActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private var selectedDate: String? = null // Biến toàn cục lưu ngày đã chọn
    private val dentistId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_appointment)

        val iVBackDentistApp = findViewById<ImageView>(R.id.iVBackDentistApp)
        iVBackDentistApp.setOnClickListener {
            finish()
        }

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)

        // Xử lý khi người dùng chọn ngày trên CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Lưu selectedDate vào biến toàn cục
            selectedDate = "$dayOfMonth-${month + 1}-$year"
            tvSelectedDate.text = "Ngày đã chọn: $selectedDate"
        }

        val addShiftButton = findViewById<Button>(R.id.btnaddShiftButton)
        addShiftButton.setOnClickListener {

        }
    }

    // Hàm thêm ca trực rỗng cho bác sĩ vào ngày đã chọn

}
