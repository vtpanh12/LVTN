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
import com.example.vtpa_b2013518_lvtn.users.UpdatePasswordActivity

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin)

        val iVBackAdmin = findViewById<ImageView>(R.id.iVBackAdmin)
        iVBackAdmin.setOnClickListener {
            finish()
        }
        val linearIndexAdmin = findViewById<LinearLayout>(R.id.linearIndexAdmin)
        linearIndexAdmin.setOnClickListener {
            startActivity(Intent(this, Admin_Update_InfoActivity::class.java))
        }
    }
}