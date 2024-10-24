package com.example.vtpa_b2013518_lvtn.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.example.vtpa_b2013518_lvtn.adapter.AppointmentAdapter
import com.example.vtpa_b2013518_lvtn.adapter.AppointmentAdmin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminIndexActivity : AppCompatActivity() {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppointmentAdmin
    private lateinit var appointmentList: MutableList<Appointment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_index)
        val linearAdmin = findViewById<LinearLayout>(R.id.linearAdmin)
        linearAdmin.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }
        recyclerView = findViewById(R.id.recyclerAppointmentAdmin)
        recyclerView.layoutManager = LinearLayoutManager(this)
        appointmentList = mutableListOf()
        adapter = AppointmentAdmin(appointmentList)
        recyclerView.adapter = adapter
//        updateRecyclerView()
        // Gọi hàm lấy dữ liệu từ Firestore và hiển thị trên RecyclerView
        loadAppointments()
    }
    private fun loadAppointments() {
        val db = FirebaseFirestore.getInstance()

        // Lắng nghe các thay đổi trong collection "appointments"
        db.collection("appointments")
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

                    appointmentList.clear() // Làm sạch danh sách cũ
                    for (doc in snapshots!!) {
                        val appointment = doc.toObject(Appointment::class.java)
                        appointmentList.add(appointment)
                    }
//                    updateRecyclerView() // Cập nhật lại RecyclerView
                    adapter.notifyDataSetChanged()

                }
            }
    }

    private fun updateRecyclerView() {
        if (appointmentList.isEmpty()) {
            // Hiển thị thông báo khi không có lịch hẹn
        } else {
            // Khởi tạo adapter nếu chưa có hoặc cập nhật dữ liệu
            adapter = AppointmentAdmin(appointmentList)
            recyclerView.adapter = adapter
        }
    }
}