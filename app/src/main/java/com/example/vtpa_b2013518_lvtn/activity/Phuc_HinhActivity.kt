package com.example.vtpa_b2013518_lvtn.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.appointment.Dat_KhamActivity

class Phuc_HinhActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phuc_hinh)
        val ivBackPH = findViewById<ImageView>(R.id.iVBackPH)
        ivBackPH.setOnClickListener {
            finish()
        }
        val btnPhuc_hinh = findViewById<Button>(R.id.btnPhuc_hinh)
        btnPhuc_hinh.setOnClickListener {
            startActivity(Intent(this, Dat_KhamActivity::class.java))
        }
    }
}