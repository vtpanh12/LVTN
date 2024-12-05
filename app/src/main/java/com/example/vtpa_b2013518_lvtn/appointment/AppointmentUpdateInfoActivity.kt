package com.example.vtpa_b2013518_lvtn.appointment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.util.Calendar

class AppointmentUpdateInfoActivity : AppCompatActivity() {
    lateinit var spinnerServicesAUI: Spinner
    lateinit var tVDateAUI: TextView
    lateinit var tVHourAUI: TextView
    lateinit var eTNoteAUI: EditText
    private lateinit var btnUpdateAppAUI: Button
    val db = Firebase.firestore
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_update_info)
        val iVBackAUIApp= findViewById<ImageView>(R.id.iVBackAUIApp)
        iVBackAUIApp.setOnClickListener {
            startActivity(Intent(this, AppointmentEditActivity::class.java))
        }
        spinnerServicesAUI = findViewById(R.id.spinner_DichVuAUI)
        tVDateAUI = findViewById(R.id.tVDateAUI)
        tVHourAUI = findViewById(R.id.tVHourAUI)
        eTNoteAUI = findViewById(R.id.eTNoteAUI)
        btnUpdateAppAUI = findViewById(R.id.btnUpdateAppAUI)
        val email = intent.getStringExtra("email")
        val username = intent.getStringExtra("username")
        val date = intent.getStringExtra("date")
        val hour = intent.getStringExtra("hour")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val note = intent.getStringExtra("notes")
        val status = intent.getStringExtra("status")
        val service = intent.getStringExtra("service")
        val id_app = intent.getStringExtra("id_app")

        // Tạo danh sách các dịch vụ
        val services = arrayOf("Trám răng", "Nhổ răng", "Cạo vôi", "Phục hình", "Chữa tủy", "Tẩy răng")

        // Thiết lập Adapter cho Spinner
        var selectedService: String = ""
        val adapter = ArrayAdapter(this, R.layout.spinner_layout, services)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerServicesAUI.adapter = adapter
        //spinnerServicesAUI.setSelection(1)
        val position = services.indexOf(service)

        if (position >= 0) {
            spinnerServicesAUI.setSelection(position)
        } else {
            // Nếu không tìm thấy, đặt một giá trị mặc định (ví dụ: vị trí đầu tiên)
            spinnerServicesAUI.setSelection(0)
            Toast.makeText(this, "Dịch vụ không hợp lệ, đã chọn giá trị mặc định", Toast.LENGTH_SHORT).show()
        }

//        selectedService = spinnerServicesAUI.selectedItem.toString()
//        Toast.makeText(this, "Dịch vụ đã chọn: $selectedService", Toast.LENGTH_SHORT).show()
        // Lắng nghe sự kiện khi người dùng thay đổi mục trong Spinner
        spinnerServicesAUI.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Cập nhật giá trị `selectedService` mỗi khi mục được chọn thay đổi
                selectedService = services[position]
                //Toast.makeText(this@AppointmentUpdateInfoActivity, "$selectedService", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Không làm gì nếu không có mục nào được chọn
            }
        }
        // Khởi tạo giá trị mặc định cho ngày (nếu có sẵn)
        //val date = "15-11-2024" // Giá trị mặc định, có thể truyền từ intent hoặc lấy từ DB
        tVDateAUI.text = date

        tVDateAUI.setOnClickListener {
            // Lấy giá trị hiện tại từ TextView (nếu đã được đặt)
            val currentText = tVDateAUI.text.toString()
            val calendar = Calendar.getInstance()

            if (currentText.isNotEmpty()) {
                // Chuyển đổi giá trị từ TextView thành ngày
                val dateParts = currentText.split("-")
                if (dateParts.size == 3) {
                    val day = dateParts[0].toInt()
                    val month = dateParts[1].toInt() - 1 // Tháng bắt đầu từ 0 trong Calendar
                    val year = dateParts[2].toInt()
                    calendar.set(year, month, day)
                }
            }

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear)
                tVDateAUI.text = formattedDate
            }, year, month, day)

            // Thiết lập ngày tối thiểu là hôm nay
            val today = Calendar.getInstance()
            datePickerDialog.datePicker.minDate = today.timeInMillis

            // Thiết lập ngày tối đa là 7 ngày sau hôm nay
            today.add(Calendar.DAY_OF_MONTH, 7)
            datePickerDialog.datePicker.maxDate = today.timeInMillis

            // Hiển thị DatePicker
            datePickerDialog.show()
        }
        // Gán giá trị mặc định cho giờ (nếu đã có)
        //val hourText = "14:30" // Giá trị mặc định, có thể truyền từ intent hoặc lấy từ DB
        tVHourAUI.text = hour

        tVHourAUI.setOnClickListener {
            val currentText = tVHourAUI.text.toString()
            val calendar = Calendar.getInstance()

            if (currentText.isNotEmpty()) {
                // Chuyển đổi giá trị từ TextView thành giờ và phút
                val timeParts = currentText.split(":")
                if (timeParts.size == 2) {
                    val hour = timeParts[0].toInt()
                    val minute = timeParts[1].toInt()
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                }
            }

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                tVHourAUI.text = formattedTime
            }, hour, minute, true) // `true` để hiển thị định dạng 24 giờ

            timePickerDialog.show()
        }
        eTNoteAUI.setText(note)
        btnUpdateAppAUI.setOnClickListener {
            val service = selectedService
            val date = tVDateAUI.text.toString()
            val hour = tVHourAUI.text.toString()
            val note = eTNoteAUI.text.toString()
            if (id_app != null) {
                updateAppointment(id_app, note, date, hour, service)
            }
        }
    }
    private fun updateAppointment(appointmentId: String,
    note: String, date: String, hour: String, service: String) {
        db.collection("appointments").document(appointmentId)
            .update(
                    "service", service,
                    "date", date,
                    "hour", hour,
                    "note", note
                    )
            .addOnSuccessListener {
                Toast.makeText(this, "Lịch khám đã cập nhật thành công!: $appointmentId", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("CancelAppointment", "Error updating document", e)
            }
    }
}