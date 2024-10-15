package com.example.vtpa_b2013518_lvtn.appointment

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R

class Tao_Lich_KhamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tao_lich_kham)
        // Khai báo Spinner và Button
        val spinnerServices = findViewById<Spinner>(R.id.spinner_DichVu)

        // Tạo danh sách các dịch vụ
        val services = arrayOf("Trám răng", "Nhổ răng", "Cạo vôi", "Phục hình", "Chữa tủy", "Tẩy răng")

        // Thiết lập Adapter cho Spinner

        val adapter = ArrayAdapter(this, R.layout.spinner_layout, services)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerServices.adapter = adapter
        spinnerServices.setSelection(1)
        val selectedService = spinnerServices.selectedItem.toString()
        Toast.makeText(this, "Dịch vụ đã chọn: $selectedService", Toast.LENGTH_SHORT).show()

    }
}