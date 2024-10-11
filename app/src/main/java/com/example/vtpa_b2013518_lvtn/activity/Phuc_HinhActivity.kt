package com.example.vtpa_b2013518_lvtn.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R

class Phuc_HinhActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phuc_hinh)
        val ivBackPH = findViewById<ImageView>(R.id.iVBackPH)
        ivBackPH.setOnClickListener {
            finish()
        }
    }
}