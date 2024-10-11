package com.example.vtpa_b2013518_lvtn.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R

class Tram_RangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tram_rang)
        val ivBackTramR = findViewById<ImageView>(R.id.iVBackTramR)
        ivBackTramR.setOnClickListener {
            finish()
        }
    }
}