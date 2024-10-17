package com.example.vtpa_b2013518_lvtn.users

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
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
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class UpdatePasswordActivity : AppCompatActivity() {
    lateinit var eTCurrentPW: EditText
    lateinit var eTNewPW: EditText
    lateinit var eTCPW: EditText
    lateinit var btnUpdatePW: Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_update_password)
        eTCurrentPW = findViewById(R.id.eTCurrentPW)
        eTNewPW = findViewById(R.id.eTNewPW)
        eTCPW = findViewById(R.id.eTCNewPW)
        btnUpdatePW = findViewById(R.id.btnUpdatePW)
        val iVBackUpdatePW = findViewById<ImageView>(R.id.iVBackUpdatePW)
        iVBackUpdatePW.setOnClickListener {
            finish()
        }
        val user = Firebase.auth.currentUser
//        val authCredential = EmailAuthProvider.getCredential(email, password)
//        user?.reauthenticate(authCredential)
//            ?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("Reauth", "Người dùng đã được xác thực lại thành công.")
//                } else {
//                    Log.e("Reauth", "Xác thực lại thất bại: ${task.exception?.message}")
//                }
//            }

        btnUpdatePW.setOnClickListener {
            val currentPassword = eTCurrentPW.text.toString()
            val newPassword = eTNewPW.text.toString()
            val cNewPassword = eTCPW.text.toString()
            val email = getEmail()
            if (email != null) {
            val authCredential = EmailAuthProvider.getCredential(email, currentPassword)
             user?.reauthenticate(authCredential)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (currentPassword == newPassword && newPassword!= cNewPassword) {
                                    Toast.makeText(this, "Mat khau khong hop le", Toast.LENGTH_SHORT).show()
                                    Log.e(
                                        "UpdatePassword",
                                        "Mật khẩu mới không được giống với mật khẩu cũ."
                                    )
                                } else {
                                    // Bước 3: Cập nhật mật khẩu nếu khác
                                    user.updatePassword(newPassword)
                                        .addOnCompleteListener { updateTask ->
                                            if (updateTask.isSuccessful) {
                                                Log.d("UpdatePassword", "Cập nhật mật khẩu thành công.")
                                            } else {
                                                Log.e(
                                                    "UpdatePassword",
                                                    "Cập nhật mật khẩu thất bại: ${updateTask.exception?.message}"
                                                )
                                            }
                                        }
                                }
                        Log.d("Reauth", "Người dùng đã được xác thực lại thành công.")
                    } else {
                        Log.e("Reauth", "Xác thực lại thất bại: ${task.exception?.message}")
                }
            }
//                auth.signInWithEmailAndPassword(email, currentPassword)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            user?.let {
//                                // Bước 2: So sánh mật khẩu cũ và mật khẩu mới
//                                if (currentPassword == newPassword && newPassword!= cNewPassword) {
//                                    Toast.makeText(this, "Mat khau khong hop le", Toast.LENGTH_SHORT).show()
//                                    Log.e(
//                                        "UpdatePassword",
//                                        "Mật khẩu mới không được giống với mật khẩu cũ."
//                                    )
//                                } else {
//                                    // Bước 3: Cập nhật mật khẩu nếu khác
//                                    user.updatePassword(newPassword)
//                                        .addOnCompleteListener { updateTask ->
//                                            if (updateTask.isSuccessful) {
//                                                Log.d("UpdatePassword", "Cập nhật mật khẩu thành công.")
//                                            } else {
//                                                Log.e(
//                                                    "UpdatePassword",
//                                                    "Cập nhật mật khẩu thất bại: ${updateTask.exception?.message}"
//                                                )
//                                            }
//                                        }
//                                }
//                            }
//                        } else {
//                            // Xử lý khi xác thực thất bại
//                            Log.e("Auth", "Xác thực thất bại: ${task.exception?.message}")
//                        }
                    }
            }
        }

    private fun getEmail(): String? {
        // Lấy người dùng hiện tại từ FirebaseAuth
        val user = FirebaseAuth.getInstance().currentUser

        // Kiểm tra xem người dùng có tồn tại không và trả về email
        return user?.email
    }
}