package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.example.vtpa_b2013518_lvtn.adapter.AppointmentAdmin
import com.example.vtpa_b2013518_lvtn.adapter.Dentist
import com.example.vtpa_b2013518_lvtn.adapter.DentistAdapter
import com.google.firebase.firestore.FirebaseFirestore

class AdminDentistActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DentistAdapter
    private lateinit var dentistList: MutableList<Dentist>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_dentist)
        val iVBackDentist = findViewById<ImageView>(R.id.iVBackAdminDentist)
        iVBackDentist.setOnClickListener {
            finish()
        }
        recyclerView = findViewById(R.id.recyclerAdminDentist)
        recyclerView.layoutManager = LinearLayoutManager(this)
        dentistList = mutableListOf()
        adapter = DentistAdapter(dentistList)
        recyclerView.adapter = adapter
        //updateRecyclerView()
        //Gọi hàm lấy dữ liệu từ Firestore và hiển thị trên RecyclerView
        loadAppointments()
    }
    private fun loadAppointments() {
        val db = FirebaseFirestore.getInstance()

        // Lắng nghe các thay đổi trong collection "appointments"
        db.collection("dentists")
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

                    dentistList.clear() // Làm sạch danh sách cũ
                    for (doc in snapshots!!) {
                        val dentist = doc.toObject(Dentist::class.java)
                        dentistList.add(dentist)
                    }
                    adapter.notifyDataSetChanged()

                }
            }
    }
}