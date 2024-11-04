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
import com.example.vtpa_b2013518_lvtn.adapter.Shift
import com.example.vtpa_b2013518_lvtn.adapter.Slot
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AdminDentistEditActivity : AppCompatActivity() {
    lateinit var spinnerSpecialty: Spinner
    lateinit var spinnerRole: Spinner
    lateinit var btnUpdateDentist: Button
    val db = Firebase.firestore
    val userCurrentId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dentist_edit)

        val iVBackAdminDentistEdit = findViewById<ImageView>(R.id.iVBackAdminDentistEdit)
        iVBackAdminDentistEdit.setOnClickListener {
            finish()
        }
        spinnerSpecialty = findViewById(R.id.spinner_Specialty)

        val specialtyList = arrayOf("Trám răng", "Nhổ răng", "Cạo vôi", "Phục hình", "Chữa tủy", "Tẩy răng")
        // Thiết lập Adapter cho Spinner
        val adapterSpeciatly = ArrayAdapter(this, R.layout.spinner_layout_dentist, specialtyList)
        adapterSpeciatly.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSpecialty.adapter = adapterSpeciatly
        spinnerSpecialty.setSelection(1)
       // val selectedSpeciatly = spinnerSpecialty.selectedItem.toString()
        spinnerRole = findViewById(R.id.spinner_Role)

        val roleList = arrayOf("dentist", "user", "admin")
        // Thiết lập Adapter cho Spinner
        val adapterRole = ArrayAdapter(this, R.layout.spinner_layout_dentist, roleList)
        adapterRole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRole.adapter = adapterRole
        spinnerRole.setSelection(1)
        //val selectedRole = spinnerRole.selectedItem.toString()


        val email = intent.getStringExtra("email")
        val dentistId = intent.getStringExtra("dentistId")
        val username = intent.getStringExtra("username")
        val date = intent.getStringExtra("date")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val gender = intent.getStringExtra("gender")
        val address = intent.getStringExtra("address")




        val tVUserName = findViewById<TextView>(R.id.tVADEDentist)
        val tVEmail = findViewById<TextView>(R.id.tVADEEmail)
        val tVDentistId = findViewById<TextView>(R.id.tVADEDentistId)
        val tVDate = findViewById<TextView>(R.id.tVADEDate)
        val tVPhoneNumber = findViewById<TextView>(R.id.tVADEPhoneNumber)
        val tVGender = findViewById<TextView>(R.id.tVADEGender)
        val tVAddress = findViewById<TextView>(R.id.tVADEAddress)

        tVEmail.text = "Email: ${email}"
        tVDentistId.text = "ID Nha sĩ: ${dentistId}"
        tVUserName.text = "Họ và tên: ${username}"
        tVDate.text = "Ngày sinh: ${date}"
        tVPhoneNumber.text = "Số điện thoại: ${phoneNumber}"
        tVGender.text = "Giới tính: ${gender}"
        tVAddress.text = "Địa chỉ: ${address}"


        btnUpdateDentist = findViewById(R.id.btnUpdateDentist)
        btnUpdateDentist.setOnClickListener {
            val role = spinnerRole.selectedItem.toString()
            val specialty = spinnerSpecialty.selectedItem.toString()
            if (dentistId != null && userCurrentId != null){
                updateDentist(dentistId, userCurrentId, role, specialty)
            }

        }
    }
    private fun updateDentist(dentistId: String, userCurrentId: String, role: String, speciatly: String) {

        db.collection("dentists").document(dentistId)
            .update("role", role, "specialty", speciatly)
            .addOnSuccessListener {
                //sendCancellationNotification(userId)
                Toast.makeText(this, "Cap nhat thành công!: $dentistId", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("CancelAppointment", "Error updating document", e)
            }
    }

}