package com.example.vtpa_b2013518_lvtn.dentist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Shift
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class DentistAppointmentDetailActivity : AppCompatActivity() {
    private lateinit var btnDentistAppDetail: Button
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    val dentistCurrentId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_appointment_detail)

        val iVBackDentist = findViewById<ImageView>(R.id.iVBackDentistAppDetail)
        iVBackDentist.setOnClickListener {
            startActivity(Intent(this, DentistAppointmentActivity::class.java))
        }
    }
//    private fun getInfoApp(dentistId:String, hour:String, date: String, shiftId: String){
//        val shiftRef = db.collection("dentists").document(dentistId)
//            .collection("shifts").document("${date}_$shiftId")
//        shiftRef.get().addOnSuccessListener { shiftDoc ->
//            if (shiftDoc.exists()) {
//                val shift = shiftDoc.toObject(Shift::class.java)
//                val slots = shift?.slots?.toMutableMap() ?: mutableMapOf()
//                val slotKey = findMatchingSlot(hour, shift?.slots ?: emptyMap())
//                val selectedSlot = slots[slotKey]
//                if (selectedSlot != null) {
//                    selectedSlot.isBooked = true
//                    selectedSlot.id_app = appointmentId
//
//                    // Cập nhật lại shift với slot đã được đặt
//                    shiftRef.update("slots", slots).addOnSuccessListener {
//                        Toast.makeText(this, "Đã cập nhật ca trực!", Toast.LENGTH_SHORT).show()
//                    }.addOnFailureListener {
//                        Toast.makeText(this, "Lỗi khi cập nhật slot", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }.addOnFailureListener {
//            Toast.makeText(this, "Lỗi khi truy vấn ca trực", Toast.LENGTH_SHORT).show()
//        }
//    }
}