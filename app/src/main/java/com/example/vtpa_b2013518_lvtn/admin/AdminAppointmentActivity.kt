package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
import com.google.firebase.firestore.FirebaseFirestore

class AdminAppointmentActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppointmentAdmin
    private lateinit var appointmentList: MutableList<Appointment>
    private lateinit var eTAASearch: EditText
    private lateinit var iVAASearch: ImageView
    private lateinit var tVAASearchNoResults: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_appointment)
        val iVBackAdminApp = findViewById<ImageView>(R.id.iVBackAdminApp)
        iVBackAdminApp.setOnClickListener {
            finish()
        }
        recyclerView = findViewById(R.id.recyclerAdminAppointment)
        recyclerView.layoutManager = LinearLayoutManager(this)
        appointmentList = mutableListOf()
//        adapter = AppointmentAdmin(appointmentList)
//        recyclerView.adapter = adapter
        // Cập nhật và sắp xếp danh sách
        //adapter.updateAppointments(appointmentList)
        loadAppointments()
        eTAASearch = findViewById(R.id.eTAASearch)
        iVAASearch = findViewById(R.id.iVAASearch)
        tVAASearchNoResults = findViewById(R.id.tVAASearchNoResults)

        iVAASearch.setOnClickListener {
            val email= eTAASearch.text.toString().trim()
            if (email.isNotEmpty()) {
                searchUserByEmail(email)
            } else {
                Toast.makeText(this, "Hãy nhập ngày DD-MM-YYYY!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun loadAppointments() {
        val db = FirebaseFirestore.getInstance()
        // Lắng nghe các thay đổi
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
                    // Làm sạch danh sách cũ
                    appointmentList.clear()
                    for (doc in snapshots!!) {
                        val appointment = doc.toObject(Appointment::class.java)
                        appointmentList.add(appointment)
                    }
                    //adapter.notifyDataSetChanged()
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
            adapter = AppointmentAdmin(appointmentList)
            recyclerView.adapter = adapter
            // Cập nhật và sắp xếp danh sách
            adapter.updateAppointments(appointmentList)
        }
    }
    private fun searchUserByEmail(email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("appointments")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                appointmentList.clear() // Xóa danh sách cũ
                if (!documents.isEmpty) {
                    // Có kết quả
                    for (document in documents) {
                        val app = document.toObject(Appointment::class.java)
                        appointmentList.add(app)
                    }
                    tVAASearchNoResults.visibility = View.GONE // Ẩn TextView
                } else {
                    // Không có kết quả
                    tVAASearchNoResults.visibility = View.VISIBLE
                }
                updateRecyclerView() // Sử dụng lại phương thức này để cập nhật RecyclerView
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

//    private fun searchUserByEmail(email: String) {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("appointments")
//            .whereEqualTo("email", email)
//            .get()
//            .addOnSuccessListener { documents ->
//                appointmentList.clear() // Xóa danh sách cũ
//                if (!documents.isEmpty) {
//                    // Có kết quả
//                    for (document in documents) {
//                        val app = document.toObject(Appointment::class.java)
//                        appointmentList.add(app)
//                    }
//                    tVAASearchNoResults.visibility = View.GONE // Ẩn TextView
//                } else {
//                    // Không có kết quả
//                    tVAASearchNoResults.visibility = View.VISIBLE
//                }
//                adapter.notifyDataSetChanged() // Cập nhật RecyclerView
//            }
//            .addOnFailureListener { exception ->
//                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
//            }
//    }
}