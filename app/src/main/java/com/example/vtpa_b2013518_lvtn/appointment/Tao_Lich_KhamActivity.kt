package com.example.vtpa_b2013518_lvtn.appointment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
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

class Tao_Lich_KhamActivity : AppCompatActivity() {

    lateinit var spinnerServices: Spinner
    lateinit var tVDate: TextView
    lateinit var tVHour: TextView
    lateinit var eTNote: EditText
    private lateinit var btnCreateApp: Button
    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tao_lich_kham)
        // Khai báo Spinner và Button
        spinnerServices = findViewById(R.id.spinner_DichVu)
        tVDate = findViewById(R.id.tVDate)
        tVHour = findViewById(R.id.tVHour)
        eTNote = findViewById(R.id.eTNote)
        btnCreateApp = findViewById(R.id.btnCreateApp)

        val iVBackCreate = findViewById<ImageView>(R.id.iVBackCreateApp)
        iVBackCreate.setOnClickListener {
            finish()
        }
        // Tạo danh sách các dịch vụ
        val services = arrayOf("Trám răng", "Nhổ răng", "Cạo vôi", "Phục hình", "Chữa tủy", "Tẩy răng")

        // Thiết lập Adapter cho Spinner

        val adapter = ArrayAdapter(this, R.layout.spinner_layout, services)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerServices.adapter = adapter
        spinnerServices.setSelection(1)
        val selectedService = spinnerServices.selectedItem.toString()
        //Toast.makeText(this, "Dịch vụ đã chọn: $selectedService", Toast.LENGTH_SHORT).show()


        tVDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tVDate.text = formattedDate
            }, year, month, day)

            datePickerDialog.show()
        }
        tVHour.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val formattedTime = "$selectedHour:$selectedMinute"
                tVHour.text = formattedTime
            }, hour, minute, true)

            timePickerDialog.show()
        }
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        btnCreateApp.setOnClickListener {
            val service = selectedService
            val date = tVDate.text.toString()
            val hour = tVHour.text.toString()
            val note = eTNote.text.toString()
            if (userId != null) {
                // Truy vấn Firestore để lấy thông tin người dùng
                db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            // Lấy username từ tài liệu Firestore
                            val username = document.getString("username")
                            val phoneNumber = document.getString("phoneNumber")

                            // Kiểm tra nếu username không null
                            if (service.isNotEmpty() && date.isNotEmpty() && hour.isNotEmpty() && username!=null && phoneNumber!= null) {
                                // Tạo dialog với thông tin lịch hẹn và thông tin người dùng
                                val dialogView = layoutInflater.inflate(R.layout.appointment_dialog, null)
                                val dialogBuilder = AlertDialog.Builder(this)
                                    .setView(dialogView)

                                // Hiển thị username và userId trong dialog
                                dialogView.findViewById<TextView>(R.id.tVCUser).text = "Tên bệnh nhân: $username"
                                dialogView.findViewById<TextView>(R.id.tVCUserId).text = "User ID: $userId"

                                // Các thông tin khác của lịch hẹn
                                dialogView.findViewById<TextView>(R.id.tVCService).text = "Dịch vụ: $service"
                                dialogView.findViewById<TextView>(R.id.tVCDate).text = "Ngày hẹn: $date"
                                dialogView.findViewById<TextView>(R.id.tVCHour).text = "Giờ hẹn: $hour"
                                dialogView.findViewById<TextView>(R.id.tVCPhoneNumber).text = "Số điện thoại: $phoneNumber"

                                // Tạo và hiển thị dialog
                                val alertDialog = dialogBuilder.create()
                                alertDialog.show()

                                // Xử lý khi người dùng nhấn "Xác nhận"
                                dialogView.findViewById<Button>(R.id.btnConf).setOnClickListener {
                                    alertDialog.dismiss()

                                    val appointment = Appointment(
                                        id_app = "",
                                        id_user = userId,
                                        username = username,
                                        service = service,
                                        date = date,
                                        hour = hour,
                                        note = note,
                                        phoneNumber = phoneNumber,
                                        status = "Chờ xác nhận"
                                    )

                                    db.collection("appointments")
                                        .add(appointment)
                                        .addOnSuccessListener {
                                                documentReference ->
                                            // Lấy ID của appointment vừa tạo
                                            val appointmentId = documentReference.id

                                            // Cập nhật lại ID vào appointment
                                            db.collection("appointments").document(appointmentId)
                                                .update("id_app", appointmentId)
                                                .addOnSuccessListener {
                                                    Toast.makeText(this, "Lịch hẹn đã được tạo với ID: $appointmentId", Toast.LENGTH_SHORT).show()

                                                    // Bạn có thể hiển thị thông tin ID trong dialog hoặc textview
                                                    Log.d("Appointment ID", "ID lịch hẹn: $appointmentId")
                                                }
                                            db.collection("appointments").document(appointmentId)
                                                .get()
                                                .addOnSuccessListener { document ->
                                                    if (document != null) {

                                                        var id_app = document.getString("id_app")
                                                        val id_user = document.getString("id_user")
                                                        val username = document.getString("username")
                                                        val service = document.getString("service")
                                                        val date = document.getString("date")
                                                        val hour = document.getString("hour")
                                                        val phoneNumber = document.getString("phoneNumber")
                                                        val note = document.getString("note")
                                                        val status = document.getString("status")

                                                        // Tạo dialog hiển thị thông tin appointment
                                                        val dialogView = layoutInflater.inflate(R.layout.appointment_info_dialog, null)
                                                        val dialogBuilder = AlertDialog.Builder(this)
                                                            .setView(dialogView)

                                                        // Lấy các TextView từ dialog
                                                        dialogView.findViewById<TextView>(R.id.tVIUserId).text = "User ID: $id_user"
                                                        dialogView.findViewById<TextView>(R.id.tVIAppId).text = "Appointment ID: $id_app"
                                                        dialogView.findViewById<TextView>(R.id.tVIUser).text = "Tên bệnh nhân: $username"
                                                        dialogView.findViewById<TextView>(R.id.tVIService).text = "Dịch vụ: $service"
                                                        dialogView.findViewById<TextView>(R.id.tVIDate).text = "Ngày hẹn: $date"
                                                        dialogView.findViewById<TextView>(R.id.tVIHour).text = "Giờ hẹn: $hour"
                                                        dialogView.findViewById<TextView>(R.id.tVIPhoneNumber).text = "Số điện thoại: $phoneNumber"
                                                        dialogView.findViewById<TextView>(R.id.tVINote).text = "Ghi chú: $note"
                                                        dialogView.findViewById<TextView>(R.id.tVIStatus).text = "Trạng thái: $status"

                                                        // Tạo và hiển thị dialog
                                                        val alertDialog = dialogBuilder.create()
                                                        alertDialog.show()

                                                        // Xử lý khi người dùng nhấn "Đóng"
                                                        dialogView.findViewById<Button>(R.id.btnClose).setOnClickListener {
                                                            alertDialog.dismiss()
                                                        }
                                                    }

                                                }
                                            Toast.makeText(this, "Đặt lịch thành công!", Toast.LENGTH_SHORT).show()


                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }

                                // Xử lý khi người dùng nhấn "Hủy"
                                dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                                    alertDialog.dismiss()
                                }
                            } else {
                                Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Lỗi khi lấy thông tin người dùng: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show()
            }
        }
    }
}