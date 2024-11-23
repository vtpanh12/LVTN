package com.example.vtpa_b2013518_lvtn.admin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.MainActivity
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.users.UpdatePasswordActivity
import com.example.vtpa_b2013518_lvtn.users.UsersActivity
import com.google.firebase.auth.FirebaseAuth

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
        val tVPVSS = findViewById<TextView>(R.id.tVPassVSSer)
        tVPVSS.setOnClickListener {
            startActivity(Intent(this, UpdatePasswordActivity::class.java))
        }
        val linearSignOut = findViewById<LinearLayout>(R.id.linearSignOut)
        linearSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            // Xóa hết các activity trước đó
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}