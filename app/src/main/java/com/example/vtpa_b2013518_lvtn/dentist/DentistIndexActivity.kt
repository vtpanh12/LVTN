package com.example.vtpa_b2013518_lvtn.dentist

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.admin.AdminActivity
import com.example.vtpa_b2013518_lvtn.admin.AdminAppointmentActivity
import com.example.vtpa_b2013518_lvtn.admin.AdminDentistAssignmentActivity

class DentistIndexActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dentist_index)
        val linearDentist = findViewById<LinearLayout>(R.id.linearDentist)
        linearDentist.setOnClickListener {
            startActivity(Intent(this, DentistActivity::class.java))
        }
        val linearLichKham= findViewById<LinearLayout>(R.id.linearLichKham)
        linearLichKham.setOnClickListener {
            startActivity(Intent(this, DentistAppointmentActivity::class.java))
        }
        val linearSalary= findViewById<LinearLayout>(R.id.linearSalary)
        linearSalary.setOnClickListener {
            startActivity(Intent(this, DentistSalaryActivity::class.java))
        }
        val iVSalary = findViewById<ImageView>(R.id.iVSalary)
        iVSalary.setOnClickListener {
            startActivity(Intent(this, DentistSalaryActivity::class.java))
        }
        val tVSalary = findViewById<TextView>(R.id.tVSalary)
        tVSalary.setOnClickListener {
            startActivity(Intent(this, DentistSalaryActivity::class.java))
        }
        val linearMedicalRecord= findViewById<LinearLayout>(R.id.linearMedicalRecord)
        linearMedicalRecord.setOnClickListener {
            startActivity(Intent(this, DentistMedicalRecordListActivity::class.java))
        }
        val iVMedicalRecord = findViewById<ImageView>(R.id.iVMedicalRecord)
        iVMedicalRecord.setOnClickListener {
            startActivity(Intent(this, DentistMedicalRecordListActivity::class.java))
        }
        val tVMedicalRecord = findViewById<TextView>(R.id.tVMedicalRecord)
        tVMedicalRecord.setOnClickListener {
            startActivity(Intent(this, DentistMedicalRecordListActivity::class.java))
        }
    }
}