package com.example.vtpa_b2013518_lvtn.admin

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AdminMedicalRecordEditActivity : AppCompatActivity() {
    private lateinit var btnAMREEdit:Button
    private val db = Firebase.firestore
    private lateinit var eTPrice : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_medical_record_edit)
        val email = intent.getStringExtra("email")
        val username = intent.getStringExtra("username")
        val date = intent.getStringExtra("date")
        val hour = intent.getStringExtra("hour")
        val id_mr = intent.getStringExtra("id_mr")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val diagnosis = intent.getStringExtra("diagnosis")
        val dentistName = intent.getStringExtra("dentistName")
        val treatment = intent.getStringExtra("treatment")
        val dates = intent.getStringExtra("dates")
        val prescription = intent.getStringExtra("prescription")
        val nextAppointment = intent.getStringExtra("nextAppointment")
        val notes = intent.getStringExtra("notes")
        displayAppointmentInfo(email, username, date, hour, phoneNumber, diagnosis, dentistName,
        treatment, dates, prescription, nextAppointment, notes)

        val iVBackAMREdit = findViewById<ImageView>(R.id.iVBackAMREdit)
        iVBackAMREdit.setOnClickListener {
            finish()
        }
        eTPrice = findViewById(R.id.eTAMREPrice)
        if (id_mr != null) {
            db.collection("medicalrecords").document(id_mr)
                .get()
                .addOnSuccessListener { doc ->
                    if (doc!=null){
                        eTPrice.setText(doc.getString("price"))
                    } else{
                        Log.d("TAG", "No such document")
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, "Lỗi khi tải thông tin:", Toast.LENGTH_SHORT).show()
                }
        }
        btnAMREEdit= findViewById(R.id.btnAMREEdit)
        btnAMREEdit.setOnClickListener {
            var price = eTPrice.text.toString()
            if (id_mr!= null){
                db.collection("medicalrecords").document(id_mr)
                    .update("price", price)
                    .addOnSuccessListener {
                        //sendCancellationNotification(userId)
                        Toast.makeText(this, "Cập nhật giá thành công!: $price", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w("Price", "Error updating document", e)
                    }
            }
        }
    }
    private fun displayAppointmentInfo(email: String?, username: String?,
                                       date: String?, hour: String?, phoneNumber: String?,
                                       diagnosis: String?, dentistName: String?, treatment: String?,
                                       dates: String?, prescription: String?,
                                       nextAppointment: String?, notes: String?
    ) {
        findViewById<TextView>(R.id.tVAMREEmail).text = "Email: $email"
        findViewById<TextView>(R.id.tVAMREUsername).text = "Họ và tên khách hàng:: $username"
        findViewById<TextView>(R.id.tVAMREPhoneNumber).text = "Số điện thoại: $phoneNumber"
        findViewById<TextView>(R.id.tVAMRETime).text = "Thời gian: $date | $hour"
        findViewById<TextView>(R.id.tVAMREDiagnosis).text = "Chẩn đoán: $diagnosis"
        findViewById<TextView>(R.id.tVAMRETreatment).text = "Phương pháp điều trị: $treatment"
        findViewById<TextView>(R.id.tVAMREDentist).text = "Bác sĩ diều trị: $dentistName"
        findViewById<TextView>(R.id.tVAMREDates).text = "Ngày tạo: $dates"
        findViewById<TextView>(R.id.tVAMREPayment).text = "Hình thức thanh toán: Tiền mặt"
        findViewById<TextView>(R.id.tVAMRENotes).text = "Ghi chú (đơn thuốc, ghi chú, lịch hẹn): \n" +
                "$prescription | $notes | $nextAppointment"
    }
}