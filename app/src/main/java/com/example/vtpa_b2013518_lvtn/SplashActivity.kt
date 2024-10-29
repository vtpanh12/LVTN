package com.example.vtpa_b2013518_lvtn

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity
import com.example.vtpa_b2013518_lvtn.admin.AdminIndexActivity
import com.example.vtpa_b2013518_lvtn.dentist.DentistActivity
import com.example.vtpa_b2013518_lvtn.dentist.DentistIndexActivity
import com.example.vtpa_b2013518_lvtn.login.LoginActivity
import com.example.vtpa_b2013518_lvtn.users.IndexUserActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class SplashActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Sử dụng Handler để tạo độ trễ trước khi điều hướng
        Handler(Looper.getMainLooper()).postDelayed({
            checkUserRole()
        }, 2000) // Độ trễ 2 giây
    }

    private fun checkUserRole() {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            db.collection("users").document(userId).get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val role = document.getString("role")
                    when (role) {
                        "admin" -> startActivity(Intent(this, AdminIndexActivity::class.java))
                        "dentist" -> startActivity(Intent(this, DentistActivity::class.java))
                        "user" -> startActivity(Intent(this, IndexActivity::class.java))
                        else -> startActivity(Intent(this, MainActivity::class.java))
                    }
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                finish() // Đảm bảo Activity hiện tại kết thúc sau khi điều hướng
            }.addOnFailureListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

