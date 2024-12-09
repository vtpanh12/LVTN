package com.example.vtpa_b2013518_lvtn.dentist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class DentistSalaryActivity : AppCompatActivity() {
    private lateinit var tVNumberShift: TextView
    private lateinit var tVSalaryDentist: TextView
    val db = Firebase.firestore
    private val dentistId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_salary)
        val iVBackDentistSalary = findViewById<ImageView>(R.id.iVBackDentistSalary)
        iVBackDentistSalary.setOnClickListener {
            startActivity(Intent(this, DentistIndexActivity::class.java))
        }
        tVNumberShift = findViewById(R.id.tVNumberShift)
        tVSalaryDentist = findViewById(R.id.tVSalaryDentist)
        if (dentistId != null) {
            getSalary(dentistId)
        }
        else{
            Toast.makeText(this, "Loi", Toast.LENGTH_SHORT).show()
        }

    }
    private fun getSalary(dentistId: String){
        if (dentistId != null) {
            db.collection("salary").document(dentistId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // Hiển thị thông tin lên các view
                        val numberShiftString = document.getString("numberShift")
                        val numberShift = numberShiftString?.toIntOrNull() ?: 0
                        tVNumberShift.text = numberShiftString

                        // Tính toán lương và hiển thị
                        val salary = numberShift * 300
                        tVSalaryDentist.text = salary.toString()

                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Lỗi khi tải thông tin: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

//    private fun getSalary(dentistId: String){
//        if (dentistId != null) {
//            db.collection("salary").document(dentistId).get()
//                .addOnSuccessListener { document ->
//                    if (document != null) {
//                        // Hiển thị thông tin lên các view
//                        var numberShift = document.getString("numberShift")
//                        tVNumberShift.text = document.getString("numberShift")
//                        var salary = ${numberShift} * 300
//                        tVSalaryDentist.text = salary
//
//                        // Cập nhật giới tính
//                    } else {
//                        Log.d("TAG", "No such document")
//
//                    }
//                }
//                .addOnFailureListener { e ->
//                    Toast.makeText(this, "Lỗi khi tải thông tin: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
//        }
//    }
}