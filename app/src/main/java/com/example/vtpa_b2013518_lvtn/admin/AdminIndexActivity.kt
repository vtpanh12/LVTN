package com.example.vtpa_b2013518_lvtn.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.example.vtpa_b2013518_lvtn.adapter.AppointmentAdapter
import com.example.vtpa_b2013518_lvtn.adapter.AppointmentAdmin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminIndexActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_index)
        val linearAdmin = findViewById<LinearLayout>(R.id.linearAdmin)
        linearAdmin.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }
        val linearLichKham= findViewById<LinearLayout>(R.id.linearAdminLichKham)
        linearLichKham.setOnClickListener {
            startActivity(Intent(this, AdminAppointmentActivity::class.java))
        }
        val linearDentist= findViewById<LinearLayout>(R.id.linearAdminDentist)
        linearDentist.setOnClickListener {
            startActivity(Intent(this, AdminDentistActivity::class.java))
        }
        val iVDentist = findViewById<ImageView>(R.id.iVAdminDentist)
        iVDentist.setOnClickListener {
            startActivity(Intent(this, AdminDentistActivity::class.java))
        }
        val tVDentist = findViewById<TextView>(R.id.tVAdminDentist)
        tVDentist.setOnClickListener {
            startActivity(Intent(this, AdminDentistActivity::class.java))
        }
        val linearAuth= findViewById<LinearLayout>(R.id.linearAdminAuth)
        linearAuth.setOnClickListener {
            startActivity(Intent(this, AdminAuthActivity::class.java))
        }
        val iVAuth = findViewById<ImageView>(R.id.iVAdminAuth)
        iVAuth.setOnClickListener {
            startActivity(Intent(this, AdminAuthActivity::class.java))
        }
        val tVAuth = findViewById<TextView>(R.id.tVAdminAuth)
        tVAuth.setOnClickListener {
            startActivity(Intent(this, AdminAuthActivity::class.java))
        }
        val linearAssignment= findViewById<LinearLayout>(R.id.linearAdminAssignment)
        linearAssignment.setOnClickListener {
            startActivity(Intent(this, AdminDentistAssignmentActivity::class.java))
        }
        val iVAssignment = findViewById<ImageView>(R.id.iVAdminAssignment)
        iVAssignment.setOnClickListener {
            startActivity(Intent(this, AdminDentistAssignmentActivity::class.java))
        }
        val tVAssignment = findViewById<TextView>(R.id.tVAdminAssignment)
        tVAssignment.setOnClickListener {
            startActivity(Intent(this, AdminDentistAssignmentActivity::class.java))
        }
        val linearMedicalRecord= findViewById<LinearLayout>(R.id.linearAdminMedicalRecord)
        linearMedicalRecord.setOnClickListener {
            startActivity(Intent(this, AdminMedicalRecordActivity::class.java))
        }
        val iVMedicalRecord = findViewById<ImageView>(R.id.iVAdminMedicalRecord)
        iVMedicalRecord.setOnClickListener {
            startActivity(Intent(this, AdminMedicalRecordActivity::class.java))
        }
        val tVMedicalRecord = findViewById<TextView>(R.id.tVAdminMedicalRecord)
        tVMedicalRecord.setOnClickListener {
            startActivity(Intent(this, AdminMedicalRecordActivity::class.java))
        }
        val tVlinearAdminStatistics = findViewById<LinearLayout>(R.id.linearAdminStatistics)
        tVlinearAdminStatistics.setOnClickListener {
            startActivity(Intent(this, AdminStatisticsActivity::class.java))
        }
    }
}