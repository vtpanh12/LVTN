package com.example.vtpa_b2013518_lvtn.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.databinding.ActivityIndexBinding
import com.example.vtpa_b2013518_lvtn.login.SignupActivity

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
            startActivity(Intent(this, Cao_VoiActivity::class.java))
        }
    }
}