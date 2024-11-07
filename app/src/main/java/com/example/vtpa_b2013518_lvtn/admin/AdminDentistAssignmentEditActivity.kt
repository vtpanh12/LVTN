package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Shift
import com.example.vtpa_b2013518_lvtn.adapter.Slot
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AdminDentistAssignmentEditActivity : AppCompatActivity() {
    private lateinit var btnUpdateDentistAss: Button
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    val userCurrentId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dentist_assignment_edit)

        val email = intent.getStringExtra("email")
        val dentistId = intent.getStringExtra("dentistId")
        val username = intent.getStringExtra("username")
        val date = intent.getStringExtra("date")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val gender = intent.getStringExtra("gender")
        val address = intent.getStringExtra("address")
        val role= intent.getStringExtra("role")
        val specialty = intent.getStringExtra("specialty")


        val tVUserName = findViewById<TextView>(R.id.tVAssDentist)
        val tVEmail = findViewById<TextView>(R.id.tVAssEmail)
        val tVDentistId = findViewById<TextView>(R.id.tVAssDentistId)
        val tVDate = findViewById<TextView>(R.id.tVAssDate)
        val tVPhoneNumber = findViewById<TextView>(R.id.tVAssPhoneNumber)
        val tVGender = findViewById<TextView>(R.id.tVAssGender)
        val tVAddress = findViewById<TextView>(R.id.tVAssAddress)
        val tVRole = findViewById<TextView>(R.id.tVAssRole)
        val tVSpecialty = findViewById<TextView>(R.id.tVAssSpecialty)

        tVEmail.text = "Email: ${email}"
        tVDentistId.text = "ID Nha sĩ: ${dentistId}"
        tVUserName.text = "Họ và tên: ${username}"
        tVDate.text = "Ngày sinh: ${date}"
        tVPhoneNumber.text = "Số điện thoại: ${phoneNumber}"
        tVGender.text = "Giới tính: ${gender}"
        tVAddress.text = "Địa chỉ: ${address}"
        tVRole.text = "Chức vụ: ${role}"
        tVSpecialty.text = "Chuyên khoa: ${specialty}"

        val iVBackAdminDentistEditAss = findViewById<ImageView>(R.id.iVBackAdminDentistEditAss)
        iVBackAdminDentistEditAss.setOnClickListener {
            finish()
        }

        btnUpdateDentistAss = findViewById(R.id.btnUpdateDentistAss)
        btnUpdateDentistAss.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            for (i in 0 .. 7) {
                val date = dateFormat.format(calendar.time)
                // Kiểm tra nếu có dentistId và selectedDate đã được chọn
                if (dentistId != null) {
                    addEmptyShiftsForDentist(dentistId, date)
                } else {
                    Toast.makeText(
                        this,
                        "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }
        }
    }
    private fun addEmptyShiftsForDentist(dentistId: String, date: String) {
        val morningSlots = mapOf(
            "07:00" to Slot(),
            "08:00" to Slot(),
            "09:00" to Slot(),
            "10:00" to Slot(),
            "11:00" to Slot()
        )
        val morningShift = Shift(
            id_dentist = dentistId,
            date = date,
            shiftId = "1",
            startTime = "07:00",
            endTime = "12:00",
            slots = morningSlots
        )

        val afternoonSlots = mapOf(
            "13:00" to Slot(),
            "14:00" to Slot(),
            "15:00" to Slot(),
            "16:00" to Slot(),
            "17:00" to Slot()
        )
        val afternoonShift = Shift(
            id_dentist = dentistId,
            date = date,
            shiftId = "2",
            startTime = "13:00",
            endTime = "18:00",
            slots = afternoonSlots
        )

        val shiftsCollection = db.collection("dentists")
            .document(dentistId)
            .collection("shifts")

        // Kiểm tra và thêm ca sáng nếu chưa tồn tại
        shiftsCollection.document("${date}_1").get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    shiftsCollection.document("${date}_1").set(morningShift)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Ca sáng đã được thêm cho bác sĩ $dentistId vào ngày $date.")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Lỗi khi thêm ca sáng: ", e)
                        }
                } else {
                    Log.d("Firestore", "Ca sáng đã tồn tại cho bác sĩ $dentistId vào ngày $date.")
                }
            }

        // Kiểm tra và thêm ca chiều nếu chưa tồn tại
        shiftsCollection.document("${date}_2").get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    shiftsCollection.document("${date}_2").set(afternoonShift)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Ca chiều đã được thêm cho bác sĩ $dentistId vào ngày $date.")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Lỗi khi thêm ca chiều: ", e)
                        }
                } else {
                    Log.d("Firestore", "Ca chiều đã tồn tại cho bác sĩ $dentistId vào ngày $date.")
                }
            }
    }
}