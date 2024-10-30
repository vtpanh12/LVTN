package com.example.vtpa_b2013518_lvtn.dentist

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.admin.AdminActivity
import com.example.vtpa_b2013518_lvtn.admin.AdminAppointmentActivity

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
    }
}