package com.example.vtpa_b2013518_lvtn.users

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        val linearUser = findViewById<LinearLayout>(R.id.linearIndexUser)
        linearUser.setOnClickListener {
            startActivity(Intent(this, UsersActivity::class.java))
        }
        val linearSignOut = findViewById<LinearLayout>(R.id.linearSignOut)
        linearSignOut.setOnClickListener {
            Firebase.auth.signOut()
        }
    }
}