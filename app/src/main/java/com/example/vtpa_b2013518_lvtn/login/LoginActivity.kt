package com.example.vtpa_b2013518_lvtn.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity
import com.example.vtpa_b2013518_lvtn.adapter.User
import com.example.vtpa_b2013518_lvtn.admin.AdminIndexActivity
import com.example.vtpa_b2013518_lvtn.databinding.ActivityLoginBinding
import com.example.vtpa_b2013518_lvtn.databinding.ActivityMainBinding
import com.example.vtpa_b2013518_lvtn.dentist.DentistActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class LoginActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var iVLIPW : ImageView
    private lateinit var btnSignIn: Button


    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // View Bindings
        etEmail = findViewById(R.id.eTLIEmail)
        etPass = findViewById(R.id.eTLIMatKhau)
        btnSignIn = findViewById(R.id.btnLIDangNhap)
        iVLIPW = findViewById<ImageView>(R.id.iVLIPW)
        var isPasswordVisible = false
        iVLIPW.setOnClickListener {
            if (isPasswordVisible) {
                // Ẩn mật khẩu
                etPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                iVLIPW.setImageResource(R.drawable.hide_pw) // Đổi icon về "ẩn"
            } else {
                // Hiển thị mật khẩu
                etPass.inputType = InputType.TYPE_CLASS_TEXT
                iVLIPW.setImageResource(R.drawable.show_pw) // Đổi icon về "hiện"
            }

            // Di chuyển con trỏ về cuối đoạn văn bản
            etPass.setSelection(etPass.text.length)

            // Đổi trạng thái cờ
            isPasswordVisible = !isPasswordVisible
        }

        // Initialize Firebase Auth
        auth = Firebase.auth
        binding.tVLIQuenMatKhau.setOnClickListener {
            startActivity(Intent(this, FPassWordActivity::class.java))
        }

        binding.btnLIDangNhap.setOnClickListener {
            login()
        }

        binding.tVLIDangky.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
        binding.tVLIQuenMatKhau.setOnClickListener {
            startActivity(Intent(this, FPassWordActivity::class.java))
        }
    }

    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        // calling signInWithEmailAndPassword(email, pass)
        // function using Firebase auth object
        // On successful response Display a Toast
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    // Lấy thông tin vai trò từ Firestore
                    getUserRole(userId)
                    saveUserToFirestore(userId, email, "user")
                }
            } else
                Toast.makeText(this, "Đăng nhập thất bại ", Toast.LENGTH_SHORT).show()
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
                        "user" -> {
                            // Chuyển hướng người dùng đến User Activity
                            val intent = Intent(this, IndexActivity::class.java)
                            startActivity(intent)
                            finish() // Kết thúc Activity hiện tại

                        }
                        "dentist" -> {
                            val intent = Intent(this, DentistActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else -> {
                            // Vai trò không xác định, có thể thông báo lỗi
                            Log.w("RoleCheck", "Unknown role")
                        }
                    }
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
            }
    }

//        // Create a new user with a first and last name
//        val user = hashMapOf(
//            "first" to "Ada",
//            "last" to "Lovelace",
//            "born" to 1815
//        )

// Add a new document with a generated ID
//    private fun creatUser(user: User){
//    db.collection("users")
//        .add(user)
//        .addOnSuccessListener { documentReference ->
//            Toast.makeText(this, "save", Toast.LENGTH_SHORT).show()
//            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//        }
//        .addOnFailureListener { e ->
//            Log.w(TAG, "Error adding document", e)
//        }
//    }


}