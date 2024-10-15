package com.example.vtpa_b2013518_lvtn.users

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import java.util.Calendar

class UsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_users)
        val tVNgaySinh = findViewById<TextView>(R.id.tVDatePicker)
        val iVBack = findViewById<ImageView>(R.id.iVBackUser)
        iVBack.setOnClickListener {
            finish()
        }

        tVNgaySinh.setOnClickListener {
            // Lấy ngày hiện tại để làm mặc định
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog, // Dùng theme spinner
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Cập nhật TextView khi người dùng chọn ngày
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    tVNgaySinh.text = formattedDate
                },
                year, month, day
            )

            // Hiển thị DatePickerDialog
            datePicker.show()
            datePicker.window?.setBackgroundDrawableResource(android.R.color.transparent)
            // Thay đổi văn bản của nút "OK" thành "Xác nhận" và "Cancel" thành "Hủy"
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).text = "Xác nhận"
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).text = "Hủy"
        }

    }
}