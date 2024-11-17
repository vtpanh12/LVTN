package com.example.vtpa_b2013518_lvtn.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
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

class IndexActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIndexBinding
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
        // Gán view cho BottomSheetDialog
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
}