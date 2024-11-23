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
import com.example.vtpa_b2013518_lvtn.adapter.AssignmentAdapter
import com.example.vtpa_b2013518_lvtn.adapter.Dentist
import com.example.vtpa_b2013518_lvtn.adapter.DentistAdapter
import com.google.firebase.firestore.FirebaseFirestore

class AdminDentistAssignmentActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AssignmentAdapter
    private lateinit var dentistList: MutableList<Dentist>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dentist_assignment)
        val iVBackDentist = findViewById<ImageView>(R.id.iVBackAdminDentisAss)
        iVBackDentist.setOnClickListener {
            finish()
        }
        recyclerView = findViewById(R.id.recyclerAdminDentistAss)
        recyclerView.layoutManager = LinearLayoutManager(this)
        dentistList = mutableListOf()
        adapter = AssignmentAdapter(dentistList)
        recyclerView.adapter = adapter
        loadDentists()
    }
    private fun loadDentists() {
        val db = FirebaseFirestore.getInstance()
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