package com.example.vtpa_b2013518_lvtn.users

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
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class IndexUserActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_index_user)
        val iVBack = findViewById<ImageView>(R.id.iVBackIndexUser)
        iVBack.setOnClickListener {
            finish()
        }
        val tVPVSS = findViewById<TextView>(R.id.tVPassVSSer)
        tVPVSS.setOnClickListener {
            startActivity(Intent(this, UpdatePasswordActivity::class.java))
        }
        val linearUser = findViewById<LinearLayout>(R.id.linearIndexUser)
        linearUser.setOnClickListener {
            startActivity(Intent(this, UsersActivity::class.java))
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