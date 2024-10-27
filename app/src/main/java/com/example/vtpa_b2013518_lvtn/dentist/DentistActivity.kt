package com.example.vtpa_b2013518_lvtn.dentist

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
import com.example.vtpa_b2013518_lvtn.admin.Admin_Update_InfoActivity
import com.example.vtpa_b2013518_lvtn.users.UpdatePasswordActivity
import com.google.firebase.auth.FirebaseAuth

class DentistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist)
        val iVBackDentist = findViewById<ImageView>(R.id.iVBackDentist)
        iVBackDentist.setOnClickListener {
            finish()
        }
        val linearIndexDentist = findViewById<LinearLayout>(R.id.linearIndexDentist)
        linearIndexDentist.setOnClickListener {
            startActivity(Intent(this, Dentist_Update_InfoActivity::class.java))
        }
        val tVPVSS = findViewById<TextView>(R.id.tVPassVSSer)
        tVPVSS.setOnClickListener {
            startActivity(Intent(this, UpdatePasswordActivity::class.java))
        }
        val linearSignOut = findViewById<LinearLayout>(R.id.linearSignOut)
        linearSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Xóa hết các activity trước đó
            startActivity(intent)
        }
    }
}