package com.example.vtpa_b2013518_lvtn.dentist

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity
import com.example.vtpa_b2013518_lvtn.adapter.Appointment
import com.example.vtpa_b2013518_lvtn.adapter.MedicalRecord
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.util.Calendar

class DentistMedicalRecordActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    lateinit var tVDentistMRDates: TextView
    lateinit var tVDentistMRNextAppointment: TextView
    lateinit var eTDentistMRTreatment: EditText
    lateinit var eTDentistMRPrescription: EditText
    lateinit var eTDentistMRNotes: EditText
    lateinit var eTDentistMRDiagnosis: EditText
    private lateinit var btnDentistMedicalRecord: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentist_medical_record)
        tVDentistMRDates = findViewById(R.id.tVDentistMedicalRecordDates)
        tVDentistMRNextAppointment = findViewById(R.id.tVDentistMedicalRecordNextAppointment)
        eTDentistMRTreatment = findViewById(R.id.eTDentistMedicalRecordTreatment)
        eTDentistMRPrescription = findViewById(R.id.eTDentistMedicalRecordPrescription)
        eTDentistMRNotes = findViewById(R.id.eTDentistMedicalRecordNotes)
        eTDentistMRDiagnosis = findViewById(R.id.eTDentistMedicalRecordDiagnosis)
        btnDentistMedicalRecord = findViewById(R.id.btnDentistMedicalRecord)

        val iVBackMedicalRecordDetail = findViewById<ImageView>(R.id.iVBackMedicalRecordDetail)
        iVBackMedicalRecordDetail.setOnClickListener {
            startActivity(Intent(this, DentistAppointmentActivity::class.java))
        }
        tVDentistMRDates.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear)
                tVDentistMRDates.text = formattedDate
            }, year, month, day)
            // Thiết lập ngày tối thiểu là hôm nay
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }
        tVDentistMRNextAppointment.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear)
                tVDentistMRNextAppointment.text = formattedDate
            }, year, month, day)
            // Thiết lập ngày tối thiểu là hôm nay
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }
        val email = intent.getStringExtra("email")
        val username = intent.getStringExtra("username")
        val service = intent.getStringExtra("service")
        val date = intent.getStringExtra("date")
        val hour = intent.getStringExtra("hour")
        val note = intent.getStringExtra("note")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val status = intent.getStringExtra("status")
        val appointmentId = intent.getStringExtra("appointmentId")

        displayAppointmentInfo(email, username, service, date, hour, note, phoneNumber, status)
        btnDentistMedicalRecord.setOnClickListener {
            if (appointmentId != null) {
                db.collection("medicalrecords")
                    .whereEqualTo("id_app", appointmentId)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            Toast.makeText(this, "Hồ sơ bệnh án đã được tạo cho lịch khám này.", Toast.LENGTH_SHORT).show()
                        } else {
                            // Nếu chưa có hồ sơ bệnh án, tiếp tục lấy dữ liệu và lưu
                            val date = tVDentistMRDates.text.toString()
                            val nextAppointment = tVDentistMRNextAppointment.text.toString()
                            val prescription = eTDentistMRPrescription.text.toString()
                            val notes = eTDentistMRNotes.text.toString()
                            val diagnosis = eTDentistMRDiagnosis.text.toString()
                            val treatment = eTDentistMRTreatment.text.toString()

                            db.collection("appointments").document(appointmentId).get()
                                .addOnSuccessListener { app ->
                                    val id_app = app.getString("id_app")
                                    val id_dentist = app.getString("id_dentist")
                                    val id_user = app.getString("id_user")
                                    if (id_dentist != null && id_app != null && id_user != null) {
                                        saveMedicalRecord(id_app, id_user, id_dentist, diagnosis, date, notes, prescription, treatment, nextAppointment)
                                    }
                                }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Lỗi khi kiểm tra hồ sơ bệnh án:", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
    private fun displayAppointmentInfo(email: String?, username: String?, service: String?,
                                       date: String?, hour: String?, note: String?,
                                       phoneNumber: String?, status: String?
    ) {
        findViewById<TextView>(R.id.tVDentistMedicalRecordEmail).text = "$email"
        findViewById<TextView>(R.id.tVDentistMedicalRecordlUser).text = "$username"
        findViewById<TextView>(R.id.tVDentistMedicalRecordService).text = "$service"
        findViewById<TextView>(R.id.tVDentistMedicalRecordDate).text = "$date"
        findViewById<TextView>(R.id.tVDentistMedicalRecordHour).text = "$hour"
        findViewById<TextView>(R.id.tVDentistMedicalRecordNote).text = "$note"
        findViewById<TextView>(R.id.tVDentistMedicalRecordPhoneNumber).text = "$phoneNumber"
        findViewById<TextView>(R.id.tVDentistMedicalRecordStatus).text = "$status"
    }
    private fun saveMedicalRecord(id_app: String, id_user:String, id_dentist:String, diagnosis: String, date: String, notes: String,
                                  prescription: String, treatment: String, nextAppointment: String ) {
        val medicalrecord = MedicalRecord(
            id_mr = "",
            id_app = id_app,
            id_user = id_user,
            id_dentist = id_dentist,
            diagnosis = diagnosis,
            treatment = treatment,
            prescription = prescription,
            date = date,
            notes = notes,
            nextAppointment = nextAppointment,
            status = "Đã khám",
            price = ""
        )

        db.collection("medicalrecords")
            .add(medicalrecord)
            .addOnSuccessListener { documentReference ->
                val medicalrecordId = documentReference.id
                updateMedicalRecordId(medicalrecordId)

            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun updateMedicalRecordId(medicalrecordId: String) {
        db.collection("medicalrecords").document(medicalrecordId)
            .update("id_mr", medicalrecordId)
            .addOnSuccessListener {
                Toast.makeText(this, "Hồ sơ bệnh án đã được tạo với ID: $medicalrecordId", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Lỗi: Không thể tạo hồ sơ bệnh án!", Toast.LENGTH_SHORT).show()
            }
    }
}