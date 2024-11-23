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
import com.example.vtpa_b2013518_lvtn.adapter.Dentist
import com.example.vtpa_b2013518_lvtn.adapter.DentistAdapter
import com.example.vtpa_b2013518_lvtn.adapter.User
import com.example.vtpa_b2013518_lvtn.adapter.UserAdapter
import com.google.firebase.firestore.FirebaseFirestore

class AdminAuthActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var userList: MutableList<User>
    private lateinit var eTAdminAuthSearch: EditText
    private lateinit var iVAdminAuthSearch: ImageView
    private lateinit var tVAdminAuthSearchNoResults: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_auth)
        val iVBackAuth = findViewById<ImageView>(R.id.iVBackAdminAuth)
        iVBackAuth.setOnClickListener {
            finish()
        }
        recyclerView = findViewById(R.id.recyclerAdminUser)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userList = mutableListOf()
        adapter = UserAdapter(userList)
        recyclerView.adapter = adapter
        loadAppointments()
        eTAdminAuthSearch = findViewById(R.id.eTAdminAuthSearch)
        iVAdminAuthSearch = findViewById(R.id.iVAdminAuthSearch)
        tVAdminAuthSearchNoResults = findViewById(R.id.tVAdminAuthSearchNoResults)

        iVAdminAuthSearch.setOnClickListener {
            val email = eTAdminAuthSearch.text.toString().trim()
            if (email.isNotEmpty()) {
                searchUserByEmail(email)
            } else {
                Toast.makeText(this, "Hãy nhập địa chỉ email!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun loadAppointments() {
        val db = FirebaseFirestore.getInstance()
        // Lắng nghe các thay đổi trong collection "appointments"
        db.collection("users")
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

                    userList.clear() // Làm sạch danh sách cũ
                    for (doc in snapshots!!) {
                        val user = doc.toObject(User::class.java)
                        userList.add(user)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
    }
    private fun searchUserByEmail(email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                userList.clear()
                if (!documents.isEmpty) {
                    // Có kết quả
                    for (document in documents) {
                        val user = document.toObject(User::class.java)
                        userList.add(user)
                    }
                    tVAdminAuthSearchNoResults.visibility = View.GONE
                } else {
                    tVAdminAuthSearchNoResults.visibility = View.VISIBLE
                }
                // Cập nhật RecyclerView
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}