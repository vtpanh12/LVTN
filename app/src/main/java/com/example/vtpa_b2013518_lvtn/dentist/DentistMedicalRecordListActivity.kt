package com.example.vtpa_b2013518_lvtn.dentist

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.vtpa_b2013518_lvtn.adapter.Combined
import com.example.vtpa_b2013518_lvtn.adapter.CombinedAdapter
import com.example.vtpa_b2013518_lvtn.adapter.MedicalRecord
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class DentistMedicalRecordListActivity : AppCompatActivity() {
    private lateinit var adapter: CombinedAdapter
    private lateinit var recordList: MutableList<Combined>
    private lateinit var recyclerView: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    val dentistCurrentId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_medical_record_list)
        val iVBackDentistMedicalRecord = findViewById<ImageView>(R.id.iVBackDentistMedicalRecord)
        iVBackDentistMedicalRecord.setOnClickListener {
            startActivity(Intent(this, DentistIndexActivity::class.java))
        }
        recyclerView = findViewById(R.id.ryMedialRecordList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recordList = mutableListOf()
        adapter = CombinedAdapter(recordList)
        recyclerView.adapter = adapter

        loadData()
    }

    private fun loadData() {
        val combinedList = mutableListOf<Combined>()

        if (dentistCurrentId != null) {
            val medicalRecordsTask = db.collection("medicalrecords")
                .whereEqualTo("id_dentist", dentistCurrentId)
                .get()

            medicalRecordsTask.addOnSuccessListener { medicalRecordsSnapshot ->
                Log.d("Firestore", "Medical records size: ${medicalRecordsSnapshot.size()}")

                if (medicalRecordsSnapshot != null) {
                    val idAppList = mutableListOf<String>()
                    val dentistMap = mutableMapOf<String, Combined>()

                    for (doc in medicalRecordsSnapshot) {
                        Log.d("Firestore", "Medical Record: ${doc.data}")
                        val idApp = doc.getString("id_app")
                        if (idApp != null) {
                            idAppList.add(idApp)
                            Log.d("Firestore", "Found id_app: $idApp")

                            val combined = Combined(
                                medicalRecordDates = doc.getString("date") ?: "",
                                medicalRecordNotes = doc.getString("notes") ?: "",
                                medicalRecordDiagnosis = doc.getString("diagnosis") ?: "",
                                medicalRecordTreatment = doc.getString("treatment") ?: "",
                                medicalRecordPrescription = doc.getString("prescription") ?: "",
                                medicalRecordNextAppointment = doc.getString("nextAppointment") ?: "",
                                appointmentEmail = null,
                                appointmentUsername = null,
                                appointmentDate = null,
                                appointmentHour = null,
                                appointmentPhoneNumber = null
                            )

                            dentistMap[idApp] = combined
                        }
                    }
                    if (idAppList.isNotEmpty()) {
                        val appointmentsTask = db.collection("appointments")
                            .whereIn("id_app", idAppList)
                            .get()

                        appointmentsTask.addOnSuccessListener { appointmentsSnapshot ->
                            Log.d("Firestore", "Appointments size: ${appointmentsSnapshot.size()}")

                            for (appointment in appointmentsSnapshot) {
                                Log.d("Firestore", "Appointment: ${appointment.data}")
                                val appointmentId = appointment.getString("id_app") ?: continue

                                val combined = dentistMap[appointmentId]?.copy(
                                    appointmentUsername = appointment.getString("username") ?: "",
                                    appointmentEmail = appointment.getString("email") ?: "",
                                    appointmentDate = appointment.getString("date") ?: "",
                                    appointmentHour = appointment.getString("hour") ?: "",
                                    appointmentPhoneNumber = appointment.getString("phoneNumber") ?: ""
                                )

                                if (combined == null) {
                                    Log.d("Firestore", "NULL")
                                } else {
                                    combinedList.add(combined)
                                    Log.d("Firestore", "Combined Record: ${combined.medicalRecordNotes}")
                                }
                            }

                            recordList.clear()
                            recordList.addAll(combinedList)
                            adapter.notifyDataSetChanged()

                        }.addOnFailureListener { e ->
                            Log.e("LoadData", "Error loading appointments", e)
                            Toast.makeText(this, "Error loading appointments", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }.addOnFailureListener { e ->
                Log.e("LoadData", "Error loading medical records", e)
                Toast.makeText(this, "Error loading medical records", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please log in again", Toast.LENGTH_SHORT).show()
        }
    }

}
