package com.example.vtpa_b2013518_lvtn.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSUDangKy.setOnClickListener {
            createAccount()
            //startActivity(Intent(this, AuthActivity::class.java))
        }

        // View Bindings
        etEmail = findViewById(R.id.etSUEmail)
        etPass = findViewById(R.id.eTSUMatKhau)
        etConfPass = findViewById(R.id.eTSUCMatKhau)
        btnSignUp = findViewById(R.id.btnSUDangKy)

        // Initialize Firebase Auth
        auth = Firebase.auth


        val doDai = findViewById<TextView>(R.id.goiy8KyTu)
        val inHoa = findViewById<TextView>(R.id.goiyInHoa)
        val inThuong = findViewById<TextView>(R.id.goiyInThuong)
        val coSo = findViewById<TextView>(R.id.goiySo)
        val kytuDacBiet = findViewById<TextView>(R.id.goiyKyTuDacBiet)
        val checkMatKhau = findViewById<LinearLayout>(R.id.goiyMatKhau)
        etPass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Khi EditText có focus, hiển thị các gợi ý
                checkMatKhau.visibility = View.VISIBLE
                etConfPass.visibility = View.GONE
                btnSignUp.visibility = View.GONE
            } else {
                // Khi EditText mất focus, ẩn các gợi ý
                checkMatKhau.visibility = View.GONE

            }
        }

        // Lắng nghe sự thay đổi trong EditText
        etPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()

                // Kiểm tra từng tiêu chí và cập nhật màu cho TextView
                if (password.length >= 8) {
                    doDai.setTextColor(ContextCompat.getColor(this@SignupActivity, android.R.color.holo_green_dark))
                } else {
                    doDai.setTextColor(ContextCompat.getColor(this@SignupActivity, android.R.color.holo_red_dark))
                }

                if (hasUpperCase(password)) {
                    inHoa.setTextColor(ContextCompat.getColor(this@SignupActivity, android.R.color.holo_green_dark))
                } else {
                    inHoa.setTextColor(ContextCompat.getColor(this@SignupActivity, android.R.color.holo_red_dark))
                }

                if (hasLowerCase(password)) {
                    inThuong.setTextColor(ContextCompat.getColor(this@SignupActivity, android.R.color.holo_green_dark))
                } else {
                    inThuong.setTextColor(ContextCompat.getColor(this@SignupActivity, android.R.color.holo_red_dark))
                }

                if (hasDigit(password)) {
                    coSo.setTextColor(ContextCompat.getColor(this@SignupActivity, android.R.color.holo_green_dark))
                } else {
                    coSo.setTextColor(ContextCompat.getColor(this@SignupActivity, android.R.color.holo_red_dark))
                }

                if (hasSpecialChar(password)) {
                    kytuDacBiet.setTextColor(ContextCompat.getColor(this@SignupActivity, android.R.color.holo_green_dark))
                } else {
                    kytuDacBiet.setTextColor(ContextCompat.getColor(this@SignupActivity, android.R.color.holo_red_dark))
                }
                // Kiểm tra xem mật khẩu có hợp lệ chưa
                if (isPasswordValid(password)) {
                    // Ẩn gợi ý nếu mật khẩu đã hợp lệ
                    checkMatKhau.visibility = View.GONE
                    etConfPass.visibility = View.VISIBLE
                    btnSignUp.visibility = View.VISIBLE
                } else {
                    // Hiển thị lại gợi ý nếu mật khẩu chưa hợp lệ
                    checkMatKhau.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

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
//                    if(user?.isEmailVerified == true){
//                        println("Đăng nhập thành công và email đã được xác thực.")
//                    }
//                    else{
//                        println("Đăng nhập khong thành công")
//                    }

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

    // Hàm kiểm tra có ít nhất một ký tự in hoa
    private fun hasUpperCase(password: String) = password.any { it.isUpperCase() }

    // Hàm kiểm tra có ít nhất một ký tự in thường
    private fun hasLowerCase(password: String) = password.any { it.isLowerCase() }

    // Hàm kiểm tra có ít nhất một chữ số
    private fun hasDigit(password: String) = password.any { it.isDigit() }

    // Hàm kiểm tra có ít nhất một ký tự đặc biệt
    private fun hasSpecialChar(password: String) = password.any { !it.isLetterOrDigit() }

    // Hàm kiểm tra mật khẩu có thỏa mãn tất cả các điều kiện hay không
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8 &&
                hasUpperCase(password) &&
                hasLowerCase(password) &&
                hasDigit(password) &&
                hasSpecialChar(password)
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