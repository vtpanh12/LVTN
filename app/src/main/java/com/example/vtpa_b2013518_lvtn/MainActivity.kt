package com.example.vtpa_b2013518_lvtn

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.databinding.ActivityMainBinding
import com.example.vtpa_b2013518_lvtn.login.LoginActivity
import com.example.vtpa_b2013518_lvtn.login.SignupActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnDangnhap.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnDangky.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }

}