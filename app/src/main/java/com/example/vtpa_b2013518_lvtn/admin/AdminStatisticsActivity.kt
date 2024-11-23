package com.example.vtpa_b2013518_lvtn.admin

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.MedicalRecord
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class AdminStatisticsActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var barChartMR: BarChart
    private lateinit var barChartApp: BarChart
    private val adminCurrentId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_statistics)
        barChartMR = findViewById(R.id.barChartMR)
        barChartApp = findViewById(R.id.barChartApp)
        val iVBackAdminStatistics = findViewById<ImageView>(R.id.iVBackAdminStatistics)
        iVBackAdminStatistics.setOnClickListener {
            finish()
        }
        val tVASApp = findViewById<TextView>(R.id.tVASApp)
        val tVASMR = findViewById<TextView>(R.id.tVASMRedcord)
        tVASApp.setBackgroundColor(Color.rgb(224,224,224))
        tVASApp.setTextColor(Color.rgb(0,0,102))
        countAppByService()

        tVASApp.setOnClickListener {
            countAppByService()
            // Tạo hiệu ứng thay đổi kích thước
            val scaleUp = ObjectAnimator.ofFloat(tVASApp, "scaleX", 1f, 1.2f)
            scaleUp.duration = 200
            scaleUp.start()

            if (barChartMR.visibility == View.VISIBLE) {
                tVASMR.setBackgroundColor(Color.WHITE)
                tVASApp.setTextColor(Color.rgb(0,0,102))
                tVASApp.setBackgroundColor(Color.rgb(224,224,224))
                //tVASApp.setTextColor(Color.rgb(0,0,102))
                barChartMR.visibility = View.GONE  // Hiển thị nội dung
                barChartApp.visibility = View.VISIBLE
            }else {
                barChartApp.visibility = View.VISIBLE  // Ẩn nội dung
                barChartMR.visibility = View.GONE
            }
        }

        tVASMR.setOnClickListener {
            countMedicalRecordsByDate()
            // Tạo hiệu ứng thay đổi kích thước
            val scaleUp = ObjectAnimator.ofFloat(tVASMR, "scaleX", 1f, 1.2f)
            scaleUp.duration = 200
            scaleUp.start()
            tVASApp.setBackgroundColor(Color.WHITE)
            // Thực hiện hành động sau khi click
            if (barChartApp.visibility == View.VISIBLE) {
                tVASMR.setTextColor(Color.rgb(0,0,102))
                tVASMR.setBackgroundColor(Color.rgb(224,224,224))
                barChartMR.visibility = View.VISIBLE  // Hiển thị nội dung
                barChartApp.visibility = View.GONE
            }
        }

    }
    private fun countMedicalRecordsByDate()     {
            if (adminCurrentId != null) {
                db.collection("medicalrecords")
                    .whereEqualTo("status", "Đã khám")
                    .get()
                    .addOnSuccessListener { documents ->
                        val dateCountMap = mutableMapOf<String, Int>()
                        for (document in documents) {
                            val date = document.getString("date")
                            if (date != null) {
                                dateCountMap[date] = dateCountMap.getOrDefault(date, 0) + 1
                            }
                        }
                        // Tạo danh sách labels (ngày khám) và các BarEntry
                        val labels = dateCountMap.keys.toList()
                        val entries = dateCountMap.entries.mapIndexed { index, entry ->
                            BarEntry(index.toFloat(), entry.value.toFloat())
                        }
                        // Tạo BarDataSet từ danh sách các BarEntry
                        val dataSet = BarDataSet(entries, "Số lượt đã khám theo ngày")
                        dataSet.color = Color.BLUE // Màu cột
                        dataSet.valueTextColor = Color.BLACK // Màu chữ hiển thị trên cột
                        dataSet.valueTextSize = 12f // Kích thước chữ hiển thị

                         //Định dạng giá trị dưới dạng số nguyên
                        dataSet.valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return value.toInt().toString() // Chuyển giá trị thành số nguyên
                            }
                        }
                        val yAxisLeft = barChartMR.axisLeft
                        yAxisLeft.setDrawGridLines(true) // Hiển thị đường lưới ngang
                        yAxisLeft.textColor = Color.BLACK // Màu chữ
                        yAxisLeft.textSize = 12f // Kích thước chữ
                        yAxisLeft.axisMaximum = 10f
                        yAxisLeft.axisMinimum = 0f
                        barChartMR.axisRight.isEnabled = false

                        val xAxis = barChartMR.xAxis
                        xAxis.setDrawGridLines(false)
                        xAxis.axisMinimum = 0f // Giá trị nhỏ nhất trên trục X
                        xAxis.axisMaximum = 10f // Giá trị lớn nhất trên trục X
                        barChartMR.axisRight.isEnabled = false
                        xAxis.labelRotationAngle = -45f // Xoay label để tránh đè nhau
                        xAxis.position = XAxis.XAxisPosition.BOTTOM
                        xAxis.valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                val index = value.toInt()
                                return if (index in labels.indices) labels[index] else ""
                            }
                        }
                            // Tạo BarData từ BarDataSet và thiết lập cho BarChart
                        val barData = BarData(dataSet)
                        barChartMR.data = barData
                        barChartMR.invalidate() // Làm mới biểu đồ
                        barChartMR.description.text = "Ngày khám"

                    }
                    .addOnFailureListener { exception ->
                        Log.w("Firestore", "Error getting documents: ", exception)
                        Toast.makeText(this, "Error loading medical records", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please log in again", Toast.LENGTH_SHORT).show()
            }
        }


    private fun countAppByService() {
        if (adminCurrentId != null) {
            // Danh sách tất cả các loại dịch vụ
            val allServices = listOf("Trám răng", "Nhổ răng", "Cạo vôi", "Phục hình", "Chữa tủy", "Tẩy răng")
            db.collection("appointments")
                .whereEqualTo("status", "Đặt lịch thành công") // Lọc theo trạng thái
                .get()
                .addOnSuccessListener { documents ->
                    val serviceCountMap = mutableMapOf<String, Int>()
                    // Khởi tạo tất cả các dịch vụ với giá trị 0
                    allServices.forEach { service ->
                        serviceCountMap[service] = 0
                    }
                    // Cập nhật số lượng cho những dịch vụ có trong cơ sở dữ liệu
                    for (document in documents) {
                        val service = document.getString("service") // Lấy loại dịch vụ
                        if (service != null && service in allServices) {
                            serviceCountMap[service] = serviceCountMap.getOrDefault(service, 0) + 1
                        }
                    }
                    // Tạo danh sách labels (dịch vụ) và các BarEntry
                    val labels = allServices // Sử dụng danh sách đầy đủ các dịch vụ
                    val entries = labels.mapIndexed { index, service ->
                        BarEntry(index.toFloat(), serviceCountMap[service]?.toFloat() ?: 0f)
                    }
                    // Tạo BarDataSet từ danh sách các BarEntry
                    val dataSet = BarDataSet(entries, "Số lượt đặt khám thành công theo dịch vụ")
                    dataSet.color = Color.BLUE // Màu cột
                    dataSet.valueTextColor = Color.BLACK // Màu chữ hiển thị trên cột
                    dataSet.valueTextSize = 12f // Kích thước chữ hiển thị

                    // Định dạng giá trị dưới dạng số nguyên
                    dataSet.valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            return value.toInt().toString() // Chuyển giá trị thành số nguyên
                        }
                    }
                    // Cấu hình biểu đồ
                    val yAxisLeft = barChartApp.axisLeft
                    yAxisLeft.setDrawGridLines(true) // Hiển thị đường lưới ngang
                    yAxisLeft.textColor = Color.BLACK // Màu chữ
                    yAxisLeft.textSize = 12f // Kích thước chữ
                    yAxisLeft.axisMinimum = 0f
                    barChartApp.axisRight.isEnabled = false

                    val xAxis = barChartApp.xAxis
                    xAxis.setDrawGridLines(false)
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.labelRotationAngle = -45f // Xoay label để tránh đè nhau

                    // Gán nhãn cho trục X
                    xAxis.valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            val index = value.toInt()
                            return if (index in labels.indices) labels[index] else ""
                        }
                    }
                    // Tạo BarData từ BarDataSet và thiết lập cho BarChart
                    val barData = BarData(dataSet)
                    barChartApp.data = barData
                    barChartApp.invalidate() // Làm mới biểu đồ
                    barChartApp.description.text = "Loại dịch vụ"
                }
                .addOnFailureListener { exception ->
                    Log.w("Firestore", "Error getting documents: ", exception)
                    Toast.makeText(this, "Error loading appointments", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please log in again", Toast.LENGTH_SHORT).show()
        }
    }


}
