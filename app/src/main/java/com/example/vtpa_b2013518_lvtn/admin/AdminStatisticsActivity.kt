package com.example.vtpa_b2013518_lvtn.admin

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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

class AdminStatisticsActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var barChart: BarChart
    private val adminCurrentId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_statistics)

        barChart = findViewById(R.id.barChart)
        countMedicalRecordsByDate()

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

                        // Chuyển đổi dữ liệu thành danh sách các BarEntry
//                        val entries = dateCountMap.entries.mapIndexed { index, entry ->
//                            BarEntry(index.toFloat(), entry.value.toFloat())
//                        }
                        // Tạo danh sách labels (ngày khám) và các BarEntry
                        val labels = dateCountMap.keys.toList()
                        val entries = dateCountMap.entries.mapIndexed { index, entry ->
                            BarEntry(index.toFloat(), entry.value.toFloat())
                        }


                        // Tạo BarDataSet từ danh sách các BarEntry
                        val dataSet = BarDataSet(entries, "Số lượt 'Đã khám' theo ngày")
                        dataSet.color = Color.BLUE // Màu cột
                        dataSet.valueTextColor = Color.BLACK // Màu chữ hiển thị trên cột
                        dataSet.valueTextSize = 12f // Kích thước chữ hiển thị

                         //Định dạng giá trị dưới dạng số nguyên
                        dataSet.valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return value.toInt().toString() // Chuyển giá trị thành số nguyên
                            }
                        }

                        val yAxisLeft = barChart.axisLeft
                        yAxisLeft.setDrawGridLines(true) // Hiển thị đường lưới ngang
                        yAxisLeft.textColor = Color.BLACK // Màu chữ
                        yAxisLeft.textSize = 12f // Kích thước chữ
                        yAxisLeft.axisMaximum = 10f
                        yAxisLeft.axisMinimum = 0f
                        barChart.axisRight.isEnabled = false


                        val xAxis = barChart.xAxis
                        xAxis.setDrawGridLines(false)
                        xAxis.axisMinimum = 0f // Giá trị nhỏ nhất trên trục X
                        xAxis.axisMaximum = 10f // Giá trị lớn nhất trên trục X
                        barChart.axisRight.isEnabled = false
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
                        barChart.data = barData
                        barChart.invalidate() // Làm mới biểu đồ
                        barChart.description.text = "Ngày khám"

                    }
                    .addOnFailureListener { exception ->
                        Log.w("Firestore", "Error getting documents: ", exception)
                        Toast.makeText(this, "Error loading medical records", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please log in again", Toast.LENGTH_SHORT).show()
            }
        }


}
