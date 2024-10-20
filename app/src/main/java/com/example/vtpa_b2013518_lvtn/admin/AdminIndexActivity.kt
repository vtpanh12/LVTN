package com.example.vtpa_b2013518_lvtn.admin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R

class AdminIndexActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_index)
        val linearAdmin = findViewById<LinearLayout>(R.id.linearAdmin)
        linearAdmin.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }
    }
}