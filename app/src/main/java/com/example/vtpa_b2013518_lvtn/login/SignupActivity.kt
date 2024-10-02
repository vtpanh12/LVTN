package com.example.vtpa_b2013518_lvtn.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignupActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var etConfPass: EditText
    private lateinit var btnSignUp: Button
//    lateinit var tvRedirectLogin: TextView

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSUDangKy.setOnClickListener {
            createAccount()
            //startActivity(Intent(this, LoginActivity::class.java))
        }

        // View Bindings
        etEmail = findViewById(R.id.etSUEmail)
        etConfPass = findViewById(R.id.eTSUCMatKhau)
        etPass = findViewById(R.id.eTSUMatKhau)
        btnSignUp = findViewById(R.id.btnSUDangKy)
       // tvRedirectLogin = findViewById(R.id.tvDangky)
        // Initialize Firebase Auth
        auth = Firebase.auth


    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload()
        }
    }
    private fun createAccount(){

        val email = etEmail.text.toString()
        val password = etPass.text.toString()
        val confirmPassword = etConfPass.text.toString()

        // check pass
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Hãy điền vào email và mật khẩu", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Mật khẩu không đồng bộ", Toast.LENGTH_SHORT)
                .show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Đăng ký không thành công",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)
                }
            }
    }
//    private fun getCurrent(){
//        val user = Firebase.auth.currentUser
//        user?.let {
//            // Name, email address, and profile photo Url
//            val name = it.displayName
//            val email = it.email
//            val photoUrl = it.photoUrl
//
//            // Check if user's email is verified
//            val emailVerified = it.isEmailVerified
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getIdToken() instead.
//            val uid = it.uid
//        }
//    }
}