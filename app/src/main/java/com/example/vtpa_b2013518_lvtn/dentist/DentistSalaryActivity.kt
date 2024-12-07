package com.example.vtpa_b2013518_lvtn.dentist

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R

class DentistSalaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_salary)
        val iVBackDentistSalary = findViewById<ImageView>(R.id.iVBackDentistSalary)
        iVBackDentistSalary.setOnClickListener {
            startActivity(Intent(this, DentistIndexActivity::class.java))
        }
    }
}