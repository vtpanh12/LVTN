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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class SplashActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set giao diện cho SplashActivity
        setContentView(R.layout.activity_splash)

        // Thực hiện các công việc tải dữ liệu hoặc kiểm tra trạng thái đăng nhập
        Handler(Looper.getMainLooper()).postDelayed({
            // Kiểm tra trạng thái đăng nhập
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (currentUser != null && userId != null) {
                getUserRole(userId)
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }

            finish()
        }, 2000) // Chờ 2 giây trước khi chuyển tiếp
    }
    fun getUserRole(userId: String) {

        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Lấy thông tin người dùng và vai trò từ Firestore
                    val role = document.getString("role")
                    when (role) {
                        "admin" -> {
                            // Chuyển hướng người dùng đến Admin Activity
                            val intent = Intent(this, AdminIndexActivity::class.java)
                            startActivity(intent)
                            finish() // Kết thúc Activity hiện tại

                        }
                        "dentist" -> {
                            val intent = Intent(this, DentistIndexActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else -> {
                            val intent = Intent(this, IndexActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                    }
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { e ->
                Log.d("Firestore", "get failed with ", e)
            }
    }
}
