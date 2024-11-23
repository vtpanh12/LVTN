package com.example.vtpa_b2013518_lvtn.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.databinding.ActivityFpassWordBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FPassWordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFpassWordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFpassWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email: EditText = findViewById(R.id.eTFPEmail)
        val btnFPAuth: Button = findViewById(R.id.btnFPAuth)

       btnFPAuth.setOnClickListener {
            val email = email.text.toString().trim()
            if (email.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập địa chỉ email của bạn", Toast.LENGTH_SHORT).show()
            }
            else{
                resetPassWord(email)
            }
        }

    }
    private fun resetPassWord(email: String){
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    Toast.makeText(this, "Đã gửi email đặt lại mật khẩu", Toast.LENGTH_SHORT).show()
                }
                else {
                    // Nếu email không tồn tại hoặc có lỗi xảy ra
                    Toast.makeText(this, "Lỗi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

}