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
import com.example.vtpa_b2013518_lvtn.adapter.FeedBack
import com.example.vtpa_b2013518_lvtn.adapter.FeedBackAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AdminFeedBackActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedBackAdapter
    private lateinit var feedbackList: MutableList<FeedBack>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_feed_back)

        val iVBackAdminFBack = findViewById<ImageView>(R.id.iVBackAdminFBack)
        iVBackAdminFBack.setOnClickListener {
            finish()
        }
        recyclerView = findViewById(R.id.recyclerAdminFeedBack)
        recyclerView.layoutManager = LinearLayoutManager(this)
        feedbackList = mutableListOf()
        adapter = FeedBackAdapter(feedbackList)
        recyclerView.adapter = adapter
        loadAFeedBacks()
    }

//    private fun loadAppointments() {
//        val db = FirebaseFirestore.getInstance()
//        // Lắng nghe các thay đổi
//        db.collection("feedbacks")
//            .addSnapshotListener { snapshots, e ->
//                if (e != null) {
//                    Toast.makeText(this, "Lỗi khi tải dữ liệu: ${e.message}", Toast.LENGTH_SHORT).show()
//                    return@addSnapshotListener
//                }
//
//                // Kiểm tra nếu không có lịch hẹn
//                if (snapshots != null && snapshots.isEmpty) {
//                    Toast.makeText(this, "Chưa có lịch khám.", Toast.LENGTH_SHORT).show()
//                    recyclerView.visibility = View.GONE
//                } else {
//                    // Làm sạch danh sách cũ
//                    feedbackList.clear()
//                    for (doc in snapshots!!) {
//                        val feedback = doc.toObject(FeedBack::class.java)
//                        feedbackList.add(feedback)
//                    }
//                    adapter.notifyDataSetChanged()
//                }
//            }
//    }
    private fun loadAFeedBacks(){
        CoroutineScope(Dispatchers.Main).launch {
            val db = FirebaseFirestore.getInstance()
            try {
                val snapshot = db.collection("feedbacks")
                    .get()
                    .await()
                feedbackList.clear()
                for (doc in snapshot.documents){
                    val feedback = doc.toObject(FeedBack::class.java)
                    feedback?.let { feedbackList.add(it) }
                }
                adapter.notifyDataSetChanged()
                if (feedbackList.isEmpty()) {
                    Toast.makeText(this@AdminFeedBackActivity, "Chưa có phản hồi.", Toast.LENGTH_SHORT).show()
                    recyclerView.visibility = View.GONE
                }
            }catch (e: Exception){
                Toast.makeText(this@AdminFeedBackActivity, "Lỗi khi tải dữ liệu: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}