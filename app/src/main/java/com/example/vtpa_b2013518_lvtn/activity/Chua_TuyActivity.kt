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

class Chua_TuyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chua_tuy)
        val ivBackCT = findViewById<ImageView>(R.id.iVBackCT)
        ivBackCT.setOnClickListener {
            finish()
        }
        val btnChua_Tuy = findViewById<Button>(R.id.btnChua_Tuy)
        btnChua_Tuy.setOnClickListener {
            startActivity(Intent(this, Dat_KhamActivity::class.java))
        }
    }
}