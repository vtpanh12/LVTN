package com.example.vtpa_b2013518_lvtn.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.User
import com.example.vtpa_b2013518_lvtn.databinding.ActivitySignupBinding
import com.example.vtpa_b2013518_lvtn.users.UsersActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class SignupActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var etConfPass: EditText
    private lateinit var btnSignUp: Button
    lateinit var iVSUPW : ImageView
    lateinit var iVSUCPW : ImageView

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAuthGmail.setOnClickListener {
            createAccount()
        }
        binding.tVSUDangNhap.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        etEmail = findViewById(R.id.etSUEmail)
        etPass = findViewById(R.id.eTSUMatKhau)
        etConfPass = findViewById(R.id.eTSUCMatKhau)
        btnSignUp = findViewById(R.id.btnAuthGmail)
        iVSUPW = findViewById(R.id.iVSUPW)
        iVSUCPW = findViewById(R.id.iVSUCPW)

        binding.eTSUCMatKhau.setOnClickListener{
            var checkpass = etConfPass.text.toString()
            if (checkpass.isEmpty()){
                Toast.makeText(this, "Hãy điền mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }

        var isPasswordVisible = false
        iVSUPW.setOnClickListener {
            if (isPasswordVisible) {
                // Ẩn mật khẩu
                etPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                iVSUPW.setImageResource(R.drawable.hide_pw) // Đổi icon về "ẩn"
            } else {
                // Hiển thị mật khẩu
                etPass.inputType = InputType.TYPE_CLASS_TEXT
                iVSUPW.setImageResource(R.drawable.show_pw) // Đổi icon về "hiện"
            }
            // Di chuyển con trỏ về cuối đoạn văn bản
            etPass.setSelection(etPass.text.length)

            // Đổi trạng thái cờ
            isPasswordVisible = !isPasswordVisible
        }
        iVSUCPW.setOnClickListener {
            if (isPasswordVisible) {
                // Ẩn mật khẩu
                etConfPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                iVSUCPW.setImageResource(R.drawable.hide_pw) // Đổi icon về "ẩn"
            } else {
                // Hiển thị mật khẩu
                etConfPass.inputType = InputType.TYPE_CLASS_TEXT
                iVSUCPW.setImageResource(R.drawable.show_pw) // Đổi icon về "hiện"
            }
            // Di chuyển con trỏ về cuối đoạn văn bản
            etConfPass.setSelection(etConfPass.text.length)

            // Đổi trạng thái cờ
            isPasswordVisible = !isPasswordVisible
        }

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
                iVSUCPW.visibility = View.GONE
            } else {
                // Khi EditText mất focus, ẩn các gợi ý
                checkMatKhau.visibility = View.GONE

            }
        }

        // Lắng nghe sự thay đổi khi click vao editText nhap mat khau, hien thi cac goi y cua mat khau
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
                    iVSUCPW.visibility = View.VISIBLE
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
    private fun createAccount() {

    val email = etEmail.text.toString()
    val password = etPass.text.toString()
    val confirmPassword = etConfPass.text.toString()

    // check
    if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
        Toast.makeText(this, "Hãy điền vào email và mật khẩu", Toast.LENGTH_SHORT).show()
        return
    }
    //check Passw va confPassw
    if (password != confirmPassword) {
        Toast.makeText(this, "Mật khẩu không đồng bộ", Toast.LENGTH_SHORT)
            .show()
        return
    }

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                user?.sendEmailVerification()?.addOnCompleteListener { verifyTask ->
                    if (verifyTask.isSuccessful) {
                        Toast.makeText(this, "Email xác thực đã được gửi.", Toast.LENGTH_SHORT)
                            .show()

                        // neu thanh cong -> qua AuthActivity
                        val userId = FirebaseAuth.getInstance().currentUser?.uid
                        if (userId != null) {
                            saveUserToFirestore(userId, email,"user")
                            // Lưu thông tin người dùng vào Firestore saveUserToFirestore(userId, email, "user") // "user" là role mặc định
                            val intent = Intent(this, AuthActivity::class.java)
                            //truyen email sang AuthActivity
                            intent.putExtra("USER_EMAIL", email)
                            startActivity(intent)
                            finish()
                            // Đóng SignUpActivity để người dùng không quay lại duoc
                        } else {
                            Log.e(
                                "SignUpActivity",
                                "Gửi email xác thực thất bại: ${verifyTask.exception?.message}"
                            )
                            Toast.makeText(this, "Lỗi khi gửi email xác thực.", Toast.LENGTH_SHORT)
                                .show()
                        }

                        Log.d(TAG, "createUserWithEmail:success")

                        //updateUI(user)
                    } else {
                        //Neu ko thanh cong, hien thong bao
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Đăng ký không thành công", Toast.LENGTH_SHORT,)
                            .show()
                    }
                }
            }
        }
    }

    private fun saveUserToFirestore(userId: String, email: String, role: String) {
        val db = FirebaseFirestore.getInstance()

        // Tạo đối tượng User
        val user = User(
            username = "",
            address = "",
            phoneNumber = "",
            email = email,
            role = role
        )

        // Lưu thông tin vào Firestore
        db.collection("users").document(userId).set(user)
            .addOnSuccessListener {
                Log.d("Firestore", "User information successfully saved.")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error saving user information", e)
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
}

