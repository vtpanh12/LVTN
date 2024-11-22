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
import com.example.vtpa_b2013518_lvtn.login.LoginActivity

class Cao_VoiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cao_voi)
        val ivBackCVR = findViewById<ImageView>(R.id.iVBackCVR)
        ivBackCVR.setOnClickListener {
            finish()
        }
        val btnCao_voi = findViewById<Button>(R.id.btnCao_voi)
        btnCao_voi.setOnClickListener {
            startActivity(Intent(this, Dat_KhamActivity::class.java))
        }
    }
}