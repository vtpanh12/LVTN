package com.example.vtpa_b2013518_lvtn.appointment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Dat_KhamActivity : AppCompatActivity() {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppointmentAdapter
    private lateinit var appointmentList: MutableList<Appointment>
    private lateinit var iVDatKham: ImageView
    private lateinit var tVDatKham: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dat_kham)
        val btnDatKham= findViewById<Button>(R.id.btnDatKham)
        btnDatKham.setOnClickListener {
            startActivity(Intent(this, Tao_Lich_KhamActivity::class.java))
        }
        val iVBackApp = findViewById<ImageView>(R.id.iVBackApp)
        iVBackApp.setOnClickListener {
            finish()
        }
        iVDatKham = findViewById(R.id.iVDatKham)
        tVDatKham = findViewById(R.id.tVDatKham)
        recyclerView = findViewById(R.id.recyclerAppointment)
        recyclerView.layoutManager = LinearLayoutManager(this)
        appointmentList = mutableListOf()

        // Gọi hàm lấy dữ liệu từ Firestore và hiển thị trên RecyclerView
        loadAppointments()
    }
    private fun loadAppointments() {
        val db = FirebaseFirestore.getInstance()

        // Lắng nghe các thay đổi trong collection "appointments"
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
}