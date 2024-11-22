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

class Tay_RangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tay_rang)
        val ivBackTayR = findViewById<ImageView>(R.id.iVBackTayR)
        ivBackTayR.setOnClickListener {
            finish()
        }
        val btnTay_rang = findViewById<Button>(R.id.btnTay_rang)
        btnTay_rang.setOnClickListener {
            startActivity(Intent(this, Dat_KhamActivity::class.java))
        }
    }
}