package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.example.vtpa_b2013518_lvtn.adapter.Dentist
import com.example.vtpa_b2013518_lvtn.adapter.DentistConfAppAdapter
import com.example.vtpa_b2013518_lvtn.adapter.Shift
import com.example.vtpa_b2013518_lvtn.adapter.Slot
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AdminConfAppointmentActivity : AppCompatActivity() {
    private lateinit var btnConfApp: Button
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    val userCurrentId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_conf_appointment)

        val iVBackAdminApp = findViewById<ImageView>(R.id.iVBackAdminApp)
        iVBackAdminApp.setOnClickListener {
            finish()
        }

        btnConfApp = findViewById(R.id.btnConfApp)
        val email = intent.getStringExtra("email")
        val appointmentId = intent.getStringExtra("appointmentId")
        val userId = intent.getStringExtra("userId")
        val username = intent.getStringExtra("username")
        val service = intent.getStringExtra("service")
        val date = intent.getStringExtra("date")
        val hour = intent.getStringExtra("hour")
        val note = intent.getStringExtra("note")
        val status = intent.getStringExtra("status")
        val phoneNumber = intent.getStringExtra("phoneNumber")

        // Hiển thị thông tin cuộc hẹn
        displayAppointmentInfo(email, appointmentId, userId, username, service,
            date, hour, note, phoneNumber, status
        )
//
        if (appointmentId != null) {
            db.collection("appointments").document(appointmentId).get()
                .addOnSuccessListener { app ->
                    var dentistId = app.getString("id_dentist")
                    if (dentistId != null) {
                        db.collection("dentists").document(dentistId).get()
                            .addOnSuccessListener { doc ->
                                if (doc!=null){
                                    var username = doc.getString("username")
                                    findViewById<TextView>(R.id.tVAConfAppDentist).text = "Nha sĩ: $username"
                                } else{
                                    Log.d("TAG", "No such document")
                                }
                            }.addOnFailureListener{
                                Toast.makeText(this, "Lỗi khi tải thông tin:", Toast.LENGTH_SHORT).show()
                            }
                    }
                }.addOnFailureListener{

                }
        }
        btnConfApp.setOnClickListener {
            //lấy shiftId để chọn là ca sáng hay chiều
            val shiftId = determineShiftId(hour)
            if (appointmentId != null && service != null && date != null && shiftId != null) {
                if (hour != null) {
                    selectDentist(service, date, shiftId, hour, appointmentId)
                    //Toast.makeText(this, "$service, $date, $shiftId, $hour", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Thiếu thông tin để chọn bác sĩ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hiển thị thông tin cuộc hẹn cơ bản
    private fun displayAppointmentInfo(email: String?, appointmentId: String?, userId: String?,
        username: String?, service: String?, date: String?, hour: String?,
        note: String?, phoneNumber: String?, status: String?
    ) {
        findViewById<TextView>(R.id.tVAConfAppEmail).text = "Email: $email"
        findViewById<TextView>(R.id.tVAConfAppAppId).text = "Appointment ID: $appointmentId"
        findViewById<TextView>(R.id.tVAConfAppUserId).text = "User ID: $userId"
        findViewById<TextView>(R.id.tVAConfAppUser).text = "Họ và tên: $username"
        findViewById<TextView>(R.id.tVAConfAppService).text = "Dịch vụ: $service"
        findViewById<TextView>(R.id.tVAConfAppDate).text = "Ngày hẹn: $date"
        findViewById<TextView>(R.id.tVAConfAppHour).text = "Giờ hẹn: $hour"
        findViewById<TextView>(R.id.tVAConfAppNote).text = "Ghi chú: $note"
        findViewById<TextView>(R.id.tVAConfAppPhoneNumber).text = "Số điện thoại: $phoneNumber"
        findViewById<TextView>(R.id.tVAConfAppStatus).text = "Trạng thái: $status"
    }
    // Xác định ca trực dựa trên giờ hẹn (ca 1: sáng, ca 2: chiều)
    private fun determineShiftId(hour: String?): String? {
        val hourInt = hour?.substringBefore(":")?.toIntOrNull()
        return when (hourInt) {
            in 7..12 -> "1"  // Ca sáng
            in 13..18 -> "2" // Ca chiều
            else -> null
        }
    }

    // Tìm slot phù hợp với giờ hẹn ( lấy giờ trong appointment, so sánh giờ và giờ trong slot)
    private fun findMatchingSlot(appointmentTime: String, shiftSlots: Map<String, Slot>): String? {
        //lay gio
        val appointmentHour = appointmentTime.substringBefore(":").toIntOrNull()
        return appointmentHour?.let {
            // Định dạng giờ thành chuỗi "HH:00"
            val formattedHour = String.format("%02d:00", appointmentHour)
            // Tìm kiếm khóa định dạng trong shiftSlots
            //keys: là giờ của slot
            shiftSlots.keys.find { slotHour -> slotHour == formattedHour }
        }
    }

    private fun selectDentist(service: String, date: String, shiftId: String,
                              hour: String, appointmentId: String) {
        db.collection("dentists").whereEqualTo("specialty", service).get()
            .addOnSuccessListener { dentists ->
                // Tạo 1 danh sách bác sĩ phù hợp
                val availableDentists = mutableListOf<String>()
                var dentistsChecked = 0 // Biến đếm số bác sĩ đã kiểm tra

                // Duyệt qua danh sách bác sĩ có chuyên khoa phù hợp
                for (dentist in dentists) {
                    // Lấy id_dentist
                    val dentistId = dentist.id
                    // Truy cập vào lịch làm việc của nha sĩ
                    val shift = db.collection("dentists").document(dentistId)
                        .collection("shifts").document("${date}_$shiftId")

                    // Lấy thông tin của shift
                    shift.get().addOnSuccessListener { shiftDoc ->
                        dentistsChecked++ // Tăng số bác sĩ đã kiểm tra
                        if (shiftDoc.exists()) {
                            val shift = shiftDoc.toObject(Shift::class.java)
                            val slots = shift?.slots?.toMutableMap() ?: mutableMapOf()
                            val slotKey = findMatchingSlot(hour, shift?.slots ?: emptyMap())
                            val selectedSlot = slots[slotKey]
                            if (selectedSlot?.isBooked == false) {
                                availableDentists.add(dentistId) // Thêm bác sĩ vào danh sách
                                //Toast.makeText(this, "$dentistId", Toast.LENGTH_SHORT).show()
                            }
                        }
                        // Khi duyệt hết tất cả các nha sĩ
                        if (dentistsChecked == dentists.size()) {
                            if (availableDentists.isNotEmpty()) {
                                // Nếu danh sách bác sĩ có sẵn không rỗng -> hiển thị
                                showDentistSelectionDialog(availableDentists)
                            } else {
                                // Nếu danh sách bác sĩ rỗng, hiện thông báo
                                Toast.makeText(this, "Không có bác sĩ phù hợp", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this, "Lỗi khi truy vấn ca trực", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Lỗi khi truy vấn bác sĩ", Toast.LENGTH_SHORT).show()
            }
    }
    private fun showDentistSelectionDialog(availableDentists: List<String>) {
        val dentistList = mutableListOf<Dentist>()
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_confapp, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewDentists)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val appointmentId = intent.getStringExtra("appointmentId")
        // Lấy thông tin chi tiết của từng bác sĩ dựa trên id và thêm vào dentistList
        for (dentistId in availableDentists) {
            db.collection("dentists").document(dentistId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val dentist = Dentist(
                            id_dentist = dentistId,
                            username = document.getString("username") ?: "N/A",
                            specialty = document.getString("specialty") ?: "N/A",
                            phoneNumber = document.getString("phoneNumber") ?: "N/A",
                            email = document.getString("email") ?: "N/A"
                        )
                        dentistList.add(dentist)

                        if (dentistList.size == availableDentists.size) {
                            // Sau khi lấy đủ thông tin các bác sĩ
                            val adapter = DentistConfAppAdapter(dentistList) { selectedDentist ->
                                // Cập nhật TextView trên giao diện với thông tin bác sĩ đã chọn
                                findViewById<TextView>(R.id.tVAConfAppDentist).text = "Nha sĩ: ${selectedDentist.username}"
                                if (appointmentId != null && selectedDentist.id_dentist != null) {
                                    updateAppointment(appointmentId, selectedDentist.id_dentist!!)
                                    //Toast.makeText(this, "${selectedDentist.username}", Toast.LENGTH_SHORT).show()
                                    //getInfoDentist(selectedDentist.id_dentist!!)
                                }
                                dialog.dismiss()
                            }
                            recyclerView.adapter = adapter
                            dialog.setContentView(view)
                            dialog.show()
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Lỗi khi lấy thông tin bác sĩ", Toast.LENGTH_SHORT).show()
                }
        }
    }
    private fun updateAppointment(appointmentId: String, dentistId: String) {
        db.collection("appointments").document(appointmentId)
            .update("id_dentist", dentistId )
            .addOnSuccessListener {
                Toast.makeText(this, "Đã cập nhật dentistId vào lịch khám: $appointmentId", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e ->
                Log.w("CancelAppointment", "Error updating document", e)
            }
    }
}

