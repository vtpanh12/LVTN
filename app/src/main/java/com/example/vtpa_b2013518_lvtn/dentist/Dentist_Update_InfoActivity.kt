package com.example.vtpa_b2013518_lvtn.dentist

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.Admin
import com.example.vtpa_b2013518_lvtn.adapter.Dentist
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.util.Calendar

class Dentist_Update_InfoActivity : AppCompatActivity() {

    lateinit var eTHoTenDentist: EditText
    lateinit var tVNgaySinhDentist: TextView
    lateinit var eTSDTDentist: EditText
    lateinit var rGGioiTinhDentist : RadioGroup
    lateinit var eTDiaChiDentist: EditText
    lateinit var eTEmailDentist: EditText
    private lateinit var btnUpdateDentist: Button

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dentist_update_info)
        tVNgaySinhDentist = findViewById(R.id.tVNgaySinhDentist)
        btnUpdateDentist = findViewById(R.id.btnCapNhatDentist)
        eTHoTenDentist = findViewById(R.id.eTHoVaTenDentist)
        eTSDTDentist = findViewById(R.id.eTSDTDentist)
        rGGioiTinhDentist = findViewById(R.id.rGGioiTinhDentist)
        eTDiaChiDentist = findViewById(R.id.eTDiaChiDentist)
        eTEmailDentist = findViewById(R.id.eTEmailDentist)
        val iVBackAdmin = findViewById<ImageView>(R.id.iVBackUpdateDentist)

        iVBackAdmin.setOnClickListener {
            finish()
        }

        tVNgaySinhDentist.setOnClickListener {
            // Lấy ngày hiện tại để làm mặc định
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog, // Dùng theme spinner
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Cập nhật TextView khi người dùng chọn ngày
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    tVNgaySinhDentist.text = formattedDate
                },
                year, month, day
            )

            // Hiển thị DatePickerDialog
            datePicker.show()
            datePicker.window?.setBackgroundDrawableResource(android.R.color.transparent)
            // Thay đổi văn bản của nút "OK" thành "Xác nhận" và "Cancel" thành "Hủy"
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).text = "Xác nhận"
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).text = "Hủy"
        }

        val email = getEmail()

        // Hiển thị email nếu tồn tại, nếu không thì báo lỗi
        if (email != null) {
            //eTEmail.setT = "Email: $email"
            eTEmailDentist.setText(Editable.Factory.getInstance().newEditable(email))

            Toast.makeText(this, "Email của người dùng: $email", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Không có người dùng nào đang đăng nhập", Toast.LENGTH_SHORT).show()
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("dentists").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // Hiển thị thông tin lên các view
                        eTHoTenDentist.setText(document.getString("username"))
                        eTSDTDentist.setText(document.getString("phoneNumber"))
                        eTDiaChiDentist.setText(document.getString("address"))
                        tVNgaySinhDentist.text = document.getString("date") // Hiển thị ngày sinh


                        // Cập nhật giới tính
                        val gender = document.getString("gender")
                        if (gender != null) {
                            val rbNam = findViewById<RadioButton>(R.id.rBNamDentist)
                            val rbNu = findViewById<RadioButton>(R.id.rBNuDentist)

                            when (gender) {
                                "Nam" -> rbNam.isChecked = true
                                "Nữ" -> rbNu.isChecked = true
                            }
                        }
                    } else {
                        Log.d("TAG", "No such document")

                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Lỗi khi tải thông tin: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }


        btnUpdateDentist.setOnClickListener {
            val username = eTHoTenDentist.text.toString()
            val phoneNumber = eTSDTDentist.text.toString()
            val address = eTDiaChiDentist.text.toString()
            val date = tVNgaySinhDentist.text.toString()
            val selectedGenderId = rGGioiTinhDentist.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                val selectedGender = findViewById<RadioButton>(selectedGenderId)
                selectedGender.text.toString()
            } else {
                ""
            }
            if (username.isNotEmpty() && phoneNumber.isNotEmpty() && address.isNotEmpty() && gender.isNotEmpty() && date.isNotEmpty()) {

                val dentist = Dentist(
                    id_dentist = userId,
                    username = username,
                    date = date,
                    phoneNumber = phoneNumber,
                    gender = gender,
                    address = address,
                    email = getEmail(),
                    role = "dentist")

                // Lưu vào Firestore, ví dụ sử dụng UID người dùng làm khóa
                val userId = FirebaseAuth.getInstance().currentUser?.uid // Bạn sẽ thay bằng user ID thực từ Firebase Auth
                if (userId != null){
                    db.collection("dentists").document(userId)
                        .set(dentist)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else{
                    Toast.makeText(this, "Cap nhat khong thanh cong", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getEmail(): String? {
        // Lấy người dùng hiện tại từ FirebaseAuth
        val user = FirebaseAuth.getInstance().currentUser

        // Kiểm tra xem người dùng có tồn tại không và trả về email
        return user?.email
    }
}