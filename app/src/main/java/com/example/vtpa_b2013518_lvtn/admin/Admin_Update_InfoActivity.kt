package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R

class Admin_Update_InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_update_info)
        val iVBackUpdateAdmin = findViewById<ImageView>(R.id.iVBackUpdateAdmin)
        iVBackUpdateAdmin.setOnClickListener {
            finish()
        }
    }
}