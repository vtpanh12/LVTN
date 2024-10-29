package com.example.vtpa_b2013518_lvtn.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
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
        val linearLichKham= findViewById<LinearLayout>(R.id.linearLichKham)
        linearLichKham.setOnClickListener {
            startActivity(Intent(this, AdminAppointmentActivity::class.java))
        }
        val linearDentist= findViewById<LinearLayout>(R.id.linearDentist)
        linearDentist.setOnClickListener {
            startActivity(Intent(this, AdminDentistActivity::class.java))
        }
        val linearAuth= findViewById<LinearLayout>(R.id.linearAuth)
        linearAuth.setOnClickListener {
            startActivity(Intent(this, AdminAuthActivity::class.java))
        }
    }
}