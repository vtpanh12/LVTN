package com.example.vtpa_b2013518_lvtn.dentist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Shift
import com.example.vtpa_b2013518_lvtn.adapter.SlotAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DentistAppointmentActivity : AppCompatActivity() {
    private lateinit var recyclerViewMorning: RecyclerView
    private lateinit var recyclerViewAfternoon: RecyclerView
    private lateinit var tvNoAppointmentsAfter: TextView
    private lateinit var tvNoAppointmentsMorn: TextView
    private lateinit var morningAdapter: SlotAdapter
    private lateinit var afternoonAdapter: SlotAdapter
    private val db = Firebase.firestore
    private var selectedDate: String? = null
    private val dentistId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_appointment)

        val iVBackDentistApp = findViewById<ImageView>(R.id.iVBackDentistApp)
        iVBackDentistApp.setOnClickListener {
            startActivity(Intent(this, DentistIndexActivity::class.java))
        }

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%02d-%02d-%d", dayOfMonth, month + 1, year)
            tvSelectedDate.text = "Ngày đã chọn: $selectedDate"

            if (dentistId != null && selectedDate != null) {
                loadDentistSlots(dentistId, selectedDate!!, "1", morningAdapter)
                loadDentistSlots(dentistId, selectedDate!!, "2", afternoonAdapter)
            }
        }

        recyclerViewMorning = findViewById(R.id.recyclerMorning)
        recyclerViewMorning.layoutManager = LinearLayoutManager(this)
        morningAdapter = SlotAdapter(emptyList())
        recyclerViewMorning.adapter = morningAdapter

        recyclerViewAfternoon = findViewById(R.id.recyclerAfternoon)
        recyclerViewAfternoon.layoutManager = LinearLayoutManager(this)
        afternoonAdapter = SlotAdapter(emptyList())
        recyclerViewAfternoon.adapter = afternoonAdapter
        tvNoAppointmentsMorn = findViewById(R.id.tvNoAppointmentsMorn)
        tvNoAppointmentsAfter = findViewById(R.id.tvNoAppointmentAfter)

    }

    private fun loadDentistSlots(dentistId: String, date: String, shiftId: String, adapter: SlotAdapter) {
        val shiftRef = db.collection("dentists")
            .document(dentistId)
            .collection("shifts")
            .document("${date}_$shiftId")

        shiftRef.get().addOnSuccessListener { document ->
            val shift = document.toObject(Shift::class.java)
            if (shift != null && shift.slots.isNotEmpty()) {
                // Có dữ liệu
                tvNoAppointmentsMorn.visibility = View.GONE
                tvNoAppointmentsAfter.visibility = View.GONE
                val slotList = shift.slots.entries.map { (time, slot) -> time to slot }
                adapter.updateData(slotList)
            } else {
                // Không có dữ liệu
                tvNoAppointmentsMorn.visibility = View.VISIBLE
                tvNoAppointmentsAfter.visibility = View.VISIBLE // Hiện thông báo

                adapter.updateData(emptyList()) // Đảm bảo adapter trống
            }
        }.addOnFailureListener { e ->
            Log.w("Firestore", "Lỗi khi lấy ca trực: ", e)
        }
    }
}



