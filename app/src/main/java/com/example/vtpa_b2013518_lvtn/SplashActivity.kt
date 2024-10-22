package com.example.vtpa_b2013518_lvtn

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity
import com.example.vtpa_b2013518_lvtn.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set giao diện cho SplashActivity
        setContentView(R.layout.activity_splash)

        // Thực hiện các công việc tải dữ liệu hoặc kiểm tra trạng thái đăng nhập
        Handler(Looper.getMainLooper()).postDelayed({
            // Kiểm tra trạng thái đăng nhập
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser

            if (currentUser != null) {
                startActivity(Intent(this, IndexActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }

            finish()
        }, 2000) // Chờ 2 giây trước khi chuyển tiếp
    }
}
