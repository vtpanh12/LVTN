package com.example.vtpa_b2013518_lvtn.dentist

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R

class DentistMedicalRecordListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_medical_record_list)
        val iVBackDentistMedicalRecord = findViewById<ImageView>(R.id.iVBackDentistMedicalRecord)
        iVBackDentistMedicalRecord.setOnClickListener {
            startActivity(Intent(this, DentistIndexActivity::class.java))
        }
    }
}