package com.example.vtpa_b2013518_lvtn.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity
import com.example.vtpa_b2013518_lvtn.databinding.ActivityResetPwactivityBinding

class ResetPWActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPwactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPwactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAuthPW.setOnClickListener {
            startActivity(Intent(this, IndexActivity::class.java))
        }

    }

//    val emailAddress = "user@example.com"
//
//    Firebase.auth.sendPasswordResetEmail(emailAddress)
//    .addOnCompleteListener { task ->
//        if (task.isSuccessful) {
//            Log.d(TAG, "Email sent.")
//        }
//    }
}