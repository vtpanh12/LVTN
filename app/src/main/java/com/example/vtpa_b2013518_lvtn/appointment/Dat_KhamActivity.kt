package com.example.vtpa_b2013518_lvtn.appointment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.example.vtpa_b2013518_lvtn.adapter.AppointmentAdapter
import com.example.vtpa_b2013518_lvtn.adapter.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Dat_KhamActivity : AppCompatActivity() {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppointmentAdapter
    private lateinit var appointmentList: MutableList<Appointment>
    private lateinit var iVDatKham: ImageView
    private lateinit var tVDatKham: TextView
    private lateinit var eTDKSearch: EditText
    private lateinit var iVDKSearch: ImageView
    private lateinit var tVDKSearchNoResults: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dat_kham)
        val btnDatKham= findViewById<Button>(R.id.btnDatKham)
        btnDatKham.setOnClickListener {
            startActivity(Intent(this, Tao_Lich_KhamActivity::class.java))
        }
        val iVBackApp = findViewById<ImageView>(R.id.iVBackApp)
        iVBackApp.setOnClickListener {
            startActivity(Intent(this, IndexActivity::class.java))
        }
        iVDatKham = findViewById(R.id.iVDatKham)
        tVDatKham = findViewById(R.id.tVDatKham)
        recyclerView = findViewById(R.id.recyclerAppointment)
        recyclerView.layoutManager = LinearLayoutManager(this)
        appointmentList = mutableListOf()

        loadAppointments()

        eTDKSearch = findViewById(R.id.eTDKSearch)
        iVDKSearch = findViewById(R.id.iVDKSearch)
        tVDKSearchNoResults = findViewById(R.id.tVDKSearchNoResults)

        iVDKSearch.setOnClickListener {
            val date = eTDKSearch.text.toString().trim()
            if (date.isNotEmpty()) {
                searchUserByDate(date)
            } else {
                Toast.makeText(this, "Hãy nhập ngày DD-MM-YYYY!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun loadAppointments() {
        val db = FirebaseFirestore.getInstance()
        db.collection("appointments")
            .whereEqualTo("id_user", userId) // Lọc theo ID của user nếu cần
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Toast.makeText(this, "Lỗi khi tải dữ liệu: ${e.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }
                // Kiểm tra nếu không có lịch hẹn
                if (snapshots != null && snapshots.isEmpty) {
                    Toast.makeText(this, "Chưa có lịch khám.", Toast.LENGTH_SHORT).show()
                    recyclerView.visibility = View.GONE
                } else {
                    tVDatKham.visibility = View.GONE
                    iVDatKham.visibility = View.GONE
                    appointmentList.clear() // Làm sạch danh sách cũ
                    for (doc in snapshots!!) {
                        val appointment = doc.toObject(Appointment::class.java)
                        appointmentList.add(appointment)
                    }
                    updateRecyclerView() // Cập nhật lại RecyclerView
                }
            }
    }

    private fun updateRecyclerView() {
        if (appointmentList.isEmpty()) {
            // Hiển thị thông báo khi không có lịch hẹn
            Toast.makeText(this, "Chưa có lịch khám.", Toast.LENGTH_SHORT).show()
        } else {
            // Khởi tạo adapter nếu chưa có hoặc cập nhật dữ liệu
            adapter = AppointmentAdapter(appointmentList)
            recyclerView.adapter = adapter
        }
    }
    private fun searchUserByDate(date: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("appointments")
            .whereEqualTo("id_user", userId)
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { documents ->
                appointmentList.clear() // Xóa danh sách cũ
                if (!documents.isEmpty) {
                    // Có kết quả
                    for (document in documents) {
                        val app = document.toObject(Appointment::class.java)
                        appointmentList.add(app)
                    }
                    tVDKSearchNoResults.visibility = View.GONE // Ẩn TextView
                } else {
                    // Không có kết quả
                    tVDKSearchNoResults.visibility = View.VISIBLE
                }
                adapter.notifyDataSetChanged() // Cập nhật RecyclerView
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}