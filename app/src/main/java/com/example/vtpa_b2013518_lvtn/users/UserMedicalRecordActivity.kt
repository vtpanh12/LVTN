package com.example.vtpa_b2013518_lvtn.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.example.vtpa_b2013518_lvtn.adapter.CombinedData
import com.example.vtpa_b2013518_lvtn.adapter.CombinedDataAdapter
import com.example.vtpa_b2013518_lvtn.adapter.CombinedUserAdapter
import com.example.vtpa_b2013518_lvtn.adapter.Dentist
import com.example.vtpa_b2013518_lvtn.adapter.MedicalRecord
import com.example.vtpa_b2013518_lvtn.admin.AdminIndexActivity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class UserMedicalRecordActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_medical_record)

        val iVBackDentistMedicalRecord = findViewById<ImageView>(R.id.iVBackUserMedicalRecord)
        iVBackDentistMedicalRecord.setOnClickListener {
            startActivity(Intent(this, IndexActivity::class.java))
        }
        val recyclerView: RecyclerView = findViewById(R.id.ryUserMedialRecord)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (userId != null) {
            fetchCombinedData(userId) { combinedDataList ->
                val adapter = CombinedUserAdapter(combinedDataList)
                recyclerView.adapter = adapter
            }
        }
    }
        fun fetchCombinedData(userId: String, callback: (List<CombinedData>) -> Unit) {
            // Lọc các medicalrecords theo userId
            db.collection("medicalrecords")
                .whereEqualTo("id_user", userId)
                .get()
                .addOnSuccessListener { mrSnapshot ->
                    val combinedList = mutableListOf<CombinedData>()
                    val mrs = mrSnapshot.toObjects(MedicalRecord::class.java)

                    if (mrs.isEmpty()) {
                        callback(emptyList()) // Không có bản ghi nào
                        return@addOnSuccessListener
                    }

                    mrs.forEach { mr ->
                        val dentistTask = mr.id_dentist?.let { db.collection("dentists").document(it).get() }
                        val appointmentTask = mr.id_app?.let { db.collection("appointments").document(it).get() }

                        // Khi cả hai task đều hoàn thành
                        Tasks.whenAllSuccess<DocumentSnapshot>(dentistTask, appointmentTask)
                            .addOnSuccessListener { result ->
                                val dentist = result[0].toObject(Dentist::class.java)
                                val appointment = result[1].toObject(Appointment::class.java)

                                if (dentist != null && appointment != null) {
                                    combinedList.add(CombinedData(appointment, dentist, mr))
                                }

                                // Callback khi xử lý xong tất cả bản ghi
                                if (combinedList.size == mrs.size) {
                                    callback(combinedList)
                                }
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    callback(emptyList()) // Xử lý lỗi bằng cách trả về danh sách rỗng
                    Log.e("FetchDataError", "Error fetching medical records: ", exception)
                }
        }

}