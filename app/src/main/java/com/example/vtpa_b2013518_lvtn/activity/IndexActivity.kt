package com.example.vtpa_b2013518_lvtn.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.appointment.Dat_KhamActivity
import com.example.vtpa_b2013518_lvtn.databinding.ActivityIndexBinding
import com.example.vtpa_b2013518_lvtn.login.SignupActivity
import com.example.vtpa_b2013518_lvtn.news.NewsActivity
import com.example.vtpa_b2013518_lvtn.users.IndexUserActivity
import com.example.vtpa_b2013518_lvtn.users.UserMedicalRecordActivity
import com.example.vtpa_b2013518_lvtn.users.UsersActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class IndexActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIndexBinding
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndexBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivTayRang.setOnClickListener {
            startActivity(Intent(this, Tay_RangActivity::class.java))
        }
        binding.ivNhoRang.setOnClickListener {
            startActivity(Intent(this, Nho_RangActivity::class.java))
        }
        binding.ivChuaTuy.setOnClickListener {
            startActivity(Intent(this, Chua_TuyActivity::class.java))
        }
        binding.ivPhucHinh.setOnClickListener {
            startActivity(Intent(this, Phuc_HinhActivity::class.java))
        }
        binding.ivTramRang.setOnClickListener {
            startActivity(Intent(this, Tram_RangActivity::class.java))
        }
        binding.ivCaoVoi.setOnClickListener {
            startActivity(Intent(this, Cao_VoiActivity::class.java))
        }
        binding.btnDatKham.setOnClickListener {
            startActivity(Intent(this, Dat_KhamActivity::class.java))
        }
        val btnSupport = findViewById<Button>(R.id.btnHoTroDatKham)
        btnSupport.setOnClickListener {
            showBottomSheet()
        }
        val btnAppointment= findViewById<LinearLayout>(R.id.linearLichKham)
        btnAppointment.setOnClickListener {
            startActivity(Intent(this, Dat_KhamActivity::class.java))
        }
        val btnTrangChu= findViewById<LinearLayout>(R.id.linearTrangchu)
        btnTrangChu.setOnClickListener {
            startActivity(Intent(this, IndexActivity::class.java))
        }
        val btnUsers= findViewById<LinearLayout>(R.id.linearUsers)
        btnUsers.setOnClickListener {
            startActivity(Intent(this, IndexUserActivity::class.java))
        }
        val linearMR = findViewById<LinearLayout>(R.id.linearUserMedicalRecord)
        linearMR.setOnClickListener {
            startActivity(Intent(this, UserMedicalRecordActivity::class.java))
        }
        val tVUser = findViewById<TextView>(R.id.tVUser)
        if (currentUserId != null) {
            db.collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val username = snapshot.getString("username") // Lấy giá trị của 'username'
                        tVUser.text = "Chào mừng ${username}"
                    } else {
                        tVUser.text = "Username not found"
                    }
                }
                .addOnFailureListener { exception ->
                    tVUser.text = "Error: ${exception.message}"
                }
        } else {
            tVUser.text = "User not logged in"
        }
    }
    private fun showBottomSheet() {
        // Khởi tạo BottomSheetDialog
        val bottomSheetDialog = BottomSheetDialog(this)
        // Inflate layout cho BottomSheet
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, null)
        // Bắt sự kiện đóng BottomSheet khi người dùng click vào nút "Đóng"
        val iVClose = view.findViewById<ImageView>(R.id.iVClose)
        iVClose.setOnClickListener {
            bottomSheetDialog.dismiss() // Đóng BottomSheet
        }
        // TextView số điện thoại
        val tvPhoneNumber = view.findViewById<TextView>(R.id.tVPhoneNumber)
        val phoneNumber = "0123456789" // Thay bằng số điện thoại thực tế
        tvPhoneNumber.text = "Tổng đài đặt khám: ${phoneNumber}"
        // Bắt sự kiện click vào TextView để gọi điện
        tvPhoneNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber") // Chuẩn bị dữ liệu là số điện thoại
            }
            startActivity(intent) // Khởi chạy giao diện gọi điện
        }
        val tvZalo = view.findViewById<TextView>(R.id.tVZalo)
        val zalo = "0123456789"
        tvZalo.text = "Hỗ trợ đặt khám qua zalo: ${zalo}"
        // Bắt sự kiện click vào TextView để mở Zalo
        tvZalo.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    // URI để mở Zalo với số điện thoại
                    data = Uri.parse("https://zalo.me/$zalo")
                }
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Không thể mở Zalo. Vui lòng kiểm tra ứng dụng!", Toast.LENGTH_SHORT).show()
            }
        }
        // Gán view cho BottomSheetDialog
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
}