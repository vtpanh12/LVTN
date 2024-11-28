package com.example.vtpa_b2013518_lvtn.users

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.FeedBack
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class UserFeedBackActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_feed_back)
        val iVBack = findViewById<ImageView>(R.id.iVBackUserFeedBack)
        iVBack.setOnClickListener {
            finish()
        }
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val eTFeedBack = findViewById<EditText>(R.id.eTFeedBack)
        val btnUserFeedBack = findViewById<Button>(R.id.btnUserFeedBack)

        btnUserFeedBack.setOnClickListener {
            val feedBackContent = eTFeedBack.text.toString().trim()
            if (feedBackContent.isEmpty()) {
                Toast.makeText(this, "Hãy nhập phản hồi và ý kiến!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (userId.isNullOrEmpty()) {
                Toast.makeText(this, "Không thể tìm thấy ID người dùng!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val feedBack = FeedBack(
                id_user = userId,
                feedBack = feedBackContent
            )
//            db.collection("feedbacks").document(userId)
//                .set(feedBack)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Gửi ý kkiến và phản hồi thành công!", Toast.LENGTH_SHORT).show()
//                    //getSalary(dentistId)
//                }
//                .addOnFailureListener { e ->
//                    Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
//                }

            val docRef = db.collection("feedbacks").document(userId)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Lấy chuỗi feedbacks đã có trong Firestore
                        val existingFeedbacks = document.getString("feedBack") ?: ""

                        // Kết hợp chuỗi cũ với chuỗi mới (có thể thêm dấu phân cách như "\n" để xuống dòng)
                        val updatedFeedbacks = existingFeedbacks + "\n" + feedBackContent

                        // Cập nhật lại trường feedbacks trong Firestore
                        docRef.update("feedBack", updatedFeedbacks)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Gửi ý kkiến và phản hồi thành công!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        // Nếu tài liệu chưa có, tạo mới tài liệu với feedbacks ban đầu
                        docRef.set(mapOf("feedbacks" to feedBackContent))
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Gửi ý kkiến và phản hồi thành công!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
                }

        }
    }
}