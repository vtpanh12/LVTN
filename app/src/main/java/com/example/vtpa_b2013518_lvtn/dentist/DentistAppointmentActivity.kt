package com.example.vtpa_b2013518_lvtn.dentist

import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DentistAppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_appointment)

        val iVBackDentistApp = findViewById<ImageView>(R.id.iVBackDentistApp)
        iVBackDentistApp.setOnClickListener {
            finish()
        }

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)

        // Định dạng ngày
//        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Xử lý khi người dùng chọn ngày trên CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            tvSelectedDate.text = "Ngày đã chọn: $selectedDate"
        }


    }

}