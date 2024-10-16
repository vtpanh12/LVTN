package com.example.vtpa_b2013518_lvtn.appointment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity

class Dat_KhamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dat_kham)
        val btnDatKham= findViewById<Button>(R.id.btnDatKham)
        btnDatKham.setOnClickListener {
            startActivity(Intent(this, Tao_Lich_KhamActivity::class.java))
        }
        val iVBackApp = findViewById<ImageView>(R.id.iVBackApp)
        iVBackApp.setOnClickListener {
            finish()
        }
    }
}