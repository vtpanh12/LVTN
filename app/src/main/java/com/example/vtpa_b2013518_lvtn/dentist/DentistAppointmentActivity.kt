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

            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            for (i in 0 until 7) {
                val date = dateFormat.format(calendar.time)
                // Kiểm tra nếu có dentistId và selectedDate đã được chọn
                if (dentistId != null) {
                    addEmptyShiftsForDentist(dentistId, date)
                } else {
                    Toast.makeText(
                        this,
                        "Vui lòng chọn ngày và đảm bảo đăng nhập.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }
        }
    }

    // Hàm thêm ca trực rỗng cho bác sĩ vào ngày đã chọn
    private fun addEmptyShiftsForDentist(dentistId: String, date: String) {
        val morningShift = Shift(
            id_dentist = dentistId,
            date = date,
            shiftId = "1",
            startTime = "08:00",
            endTime = "12:00",
            schedules = emptyMap()
        )

        val afternoonShift = Shift(
            id_dentist = dentistId,
            date = date,
            shiftId = "2",
            startTime = "13:00",
            endTime = "17:00",
            schedules = emptyMap()
        )

        // Thêm ca sáng
        db.collection("dentists")
            .document(dentistId)
            .collection("shifts")  // Đảm bảo tên collection là "shifts"
            .document("${date}_1")
            .set(morningShift)
            .addOnSuccessListener {
                Log.d("Firestore", "Ca sáng đã được thêm cho bác sĩ $dentistId vào ngày $date.")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Lỗi khi thêm ca sáng: ", e)
            }

        // Thêm ca chiều
        db.collection("dentists")
            .document(dentistId)
            .collection("shifts")
            .document("${date}_2")
            .set(afternoonShift)
            .addOnSuccessListener {
                Log.d("Firestore", "Ca chiều đã được thêm cho bác sĩ $dentistId vào ngày $date.")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Lỗi khi thêm ca chiều: ", e)
            }
    }
}
