package com.example.vtpa_b2013518_lvtn.admin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.example.vtpa_b2013518_lvtn.adapter.CombinedData
import com.example.vtpa_b2013518_lvtn.adapter.CombinedDataAdapter
import com.example.vtpa_b2013518_lvtn.adapter.Dentist
import com.example.vtpa_b2013518_lvtn.adapter.MedicalRecord
import com.example.vtpa_b2013518_lvtn.dentist.DentistIndexActivity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class AdminMedicalRecordActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    //val dentistCurrentId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_medical_record)
        val iVBackDentistMedicalRecord = findViewById<ImageView>(R.id.iVBackAdminMedicalRecord)
        iVBackDentistMedicalRecord.setOnClickListener {
            startActivity(Intent(this, AdminIndexActivity::class.java))
        }
        val recyclerView: RecyclerView = findViewById(R.id.ryAdminMedialRecord)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchCombinedData { combinedDataList ->
            val adapter = CombinedDataAdapter(combinedDataList)
            recyclerView.adapter = adapter
        }

    }

    fun fetchCombinedData(callback: (List<CombinedData>) -> Unit) {
        db.collection("medicalrecords").get().addOnSuccessListener { mrSnapshot ->
            val combinedList = mutableListOf<CombinedData>()
            val mrs = mrSnapshot.toObjects(MedicalRecord::class.java)


            mrs.forEach { mr ->
                val dentistTask = mr.id_dentist?.let { db.collection("dentists").document(it).get() }
                val appointmentTask = mr.id_app?.let { db.collection("appointments").document(it).get() }

                Tasks.whenAllSuccess<DocumentSnapshot>(dentistTask, appointmentTask)
                    .addOnSuccessListener { result ->
                        val dentist = result[0].toObject(Dentist::class.java)
                        val appointment = result[1].toObject(Appointment::class.java)
                        if (dentist != null && appointment != null) {
                            combinedList.add(CombinedData(appointment, dentist,mr))
                        }
                        if (combinedList.size == mrs.size) {
                            callback(combinedList)
                        }
                    }
            }
        }
    }


}