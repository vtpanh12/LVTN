package com.example.vtpa_b2013518_lvtn.admin

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.FirebaseFirestore

class AdminStatisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_statistics)

        val db = FirebaseFirestore.getInstance()
        val medicalRecords = mutableListOf<MedicalRecord>()

        db.collection("medicalrecords")
            .whereEqualTo("status", "Đặt khám")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val record = document.toObject(MedicalRecord::class.java)
                    medicalRecords.add(record)
                }
                // Sau khi lấy dữ liệu, vẽ biểu đồ
                drawBarChart(medicalRecords)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching data", e)
            }

    }
    private fun drawBarChart(records: List<MedicalRecord>) {
        // Nhóm dữ liệu theo ngày
        val groupedData = records.groupingBy { it.date }.eachCount()

        val barEntries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        groupedData.entries.forEachIndexed { index, entry ->
            barEntries.add(BarEntry(index.toFloat(), entry.value.toFloat())) // entry.value là số lượng
            labels.add(entry.key) // entry.key là ngày
        }

        // Tạo BarDataSet
        val barDataSet = BarDataSet(barEntries, "Số lượt đặt khám")
        barDataSet.color = Color.BLUE

        // Tạo BarData
        val barData = BarData(barDataSet)
        barData.barWidth = 0.9f

        // Cấu hình BarChart
        val barChart: BarChart = findViewById(R.id.barChart)
        barChart.data = barData
        barChart.setFitBars(true) // Đảm bảo cột hiển thị đầy đủ
        barChart.description.text = "Lượt đặt khám theo ngày"
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels) // Hiển thị tên ngày
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.axisRight.isEnabled = false // Tắt trục phải
        barChart.invalidate() // Refresh biểu đồ
    }

}