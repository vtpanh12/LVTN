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
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.example.vtpa_b2013518_lvtn.adapter.Shift
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class AdminUserEditActivity : AppCompatActivity() {
    lateinit var spinnerRole: Spinner
    lateinit var btnUpdateUser: Button
    val db = Firebase.firestore
    val userCurrentId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_user_edit)
        val iVBackAdminUserEdit = findViewById<ImageView>(R.id.iVBackAdminUserEdit)
        iVBackAdminUserEdit.setOnClickListener {
            finish()
        }
        spinnerRole = findViewById(R.id.spinner_Role)
        val roleList = arrayOf("dentist", "user", "admin")
        // Thiết lập Adapter cho Spinner
        val adapterRole = ArrayAdapter(this, R.layout.spinner_layout_dentist, roleList)
        adapterRole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRole.adapter = adapterRole
        spinnerRole.setSelection(1)
        val selectedRole = spinnerRole.selectedItem.toString()

        val email = intent.getStringExtra("email")
        val userId = intent.getStringExtra("userId")
        val username = intent.getStringExtra("username")
        val date = intent.getStringExtra("date")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val gender = intent.getStringExtra("gender")
        val address = intent.getStringExtra("address")

        val tVUserName = findViewById<TextView>(R.id.tVAUEDentist)
        val tVEmail = findViewById<TextView>(R.id.tVAUEEmail)
        val tVUserId = findViewById<TextView>(R.id.tVAUEDentistId)
        val tVDate = findViewById<TextView>(R.id.tVAUEDate)
        val tVPhoneNumber = findViewById<TextView>(R.id.tVAUEPhoneNumber)
        val tVGender = findViewById<TextView>(R.id.tVAUEGender)
        val tVAddress = findViewById<TextView>(R.id.tVAUEAddress)

        tVEmail.text = "Email: ${email}"
        tVUserId.text = "ID Nha sĩ: ${userId}"
        tVUserName.text = "Họ và tên: ${username}"
        tVDate.text = "Ngày sinh: ${date}"
        tVPhoneNumber.text = "Số điện thoại: ${phoneNumber}"
        tVGender.text = "Ngày sinh: ${gender}"
        tVAddress.text = "Ngày sinh: ${address}"

        btnUpdateUser = findViewById(R.id.btnUpdateUser)
        btnUpdateUser.setOnClickListener {
            val role = spinnerRole.selectedItem.toString()
            val specialty = spinnerRole.selectedItem.toString()
            if (userId != null && userCurrentId != null) {
                updateUser(userId, userCurrentId, role)
            }

        }
    }
        private fun updateUser(userId: String, userCurrentId: String, role: String) {
                db.collection("users").document(userId)
                    .update("role", role)
                    .addOnSuccessListener {
                        //sendCancellationNotification(userId)
                        Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w("CancelAppointment", "Error updating document", e)
                    }
                }


}
//private fun getInfoAppointment(
//    appointmentId: String,
//    dentistId: String,
//    date: String,
//    shiftId: String
//) {
//    val slot = db.collection("dentists").document(dentistId)
//        .collection("shifts").document("${date}_$shiftId")
//    slot.get().addOnSuccessListener { shiftDoc ->
//        //kiem tra xem shift co ton tai khong
//        if (shiftDoc.exists()) {
//            val shift = shiftDoc.toObject(Shift::class.java)
//            //fliterValues: loc cac slot da duoc dat
//            val bookedAppointments =
//                shift?.slots?.filterValues { it.isBooked == true } // Lọc các slot đã đặt
//            // forEach Duyệt qua tung slot đã được đặt và lấy thông tin appointment
//            bookedAppointments?.forEach { (slotKey, slot) ->
//                val appointmentId = slot.id_app
//
//                if (appointmentId != null) {
//                    db.collection("appointments").document(appointmentId).get()
//                        .addOnSuccessListener { appointmentDoc ->
//                            if (appointmentDoc.exists()) {
//                                val appointment =
//                                    appointmentDoc.toObject(Appointment::class.java)
//                                // Xử lý appointment ở đây, ví dụ: hiển thị hoặc thêm vào danh sách
//                                Log.d("Appointment", "Cuộc hẹn: ${appointment}")
//                            }
//                        }.addOnFailureListener {
//                            Log.e(
//                                "Error",
//                                "Không thể lấy thông tin cuộc hẹn với ID: $appointmentId"
//                            )
//                        }
//                }
//            }
//        }
//    }.addOnFailureListener {
//        Log.e("Error", "Lỗi khi truy vấn ca trực")
//    }
//}