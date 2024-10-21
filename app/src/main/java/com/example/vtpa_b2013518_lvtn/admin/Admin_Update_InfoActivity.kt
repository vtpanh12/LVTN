package com.example.vtpa_b2013518_lvtn.admin

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
import com.example.vtpa_b2013518_lvtn.adapter.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.util.Calendar

class Admin_Update_InfoActivity : AppCompatActivity() {

    lateinit var eTHoTenAdmin: EditText
    lateinit var tVNgaySinhAdmin: TextView
    lateinit var eTSDTAdmin: EditText
    lateinit var rGGioiTinhAdmin : RadioGroup
    lateinit var eTDiaChiAdmin: EditText
    lateinit var eTEmailAdmin: EditText
    private lateinit var btnUpdateAdmin: Button

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_update_info)
        tVNgaySinhAdmin = findViewById(R.id.tVNgaySinhAdmin)
        btnUpdateAdmin = findViewById(R.id.btnCapNhatAdmin)
        eTHoTenAdmin = findViewById(R.id.eTHoVaTenAdmin)
        eTSDTAdmin = findViewById(R.id.eTSDTAdmin)
        rGGioiTinhAdmin = findViewById(R.id.rGGioiTinhAdmin)
        eTDiaChiAdmin = findViewById(R.id.eTDiaChiAdmin)
        eTEmailAdmin = findViewById(R.id.eTEmailAdmin)
        val iVBackAdmin = findViewById<ImageView>(R.id.iVBackUpdateAdmin)

        iVBackAdmin.setOnClickListener {
            finish()
        }

        tVNgaySinhAdmin.setOnClickListener {
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
                    tVNgaySinhAdmin.text = formattedDate
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
            eTEmailAdmin.setText(Editable.Factory.getInstance().newEditable(email))

            Toast.makeText(this, "Email của người dùng: $email", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Không có người dùng nào đang đăng nhập", Toast.LENGTH_SHORT).show()
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("admins").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // Hiển thị thông tin lên các view
                        eTHoTenAdmin.setText(document.getString("username"))
                        eTSDTAdmin.setText(document.getString("phoneNumber"))
                        eTDiaChiAdmin.setText(document.getString("address"))
                        tVNgaySinhAdmin.text = document.getString("date") // Hiển thị ngày sinh


                        // Cập nhật giới tính
                        val gender = document.getString("gender")
                        if (gender != null) {
                            val rbNam = findViewById<RadioButton>(R.id.rBNamAdmin)
                            val rbNu = findViewById<RadioButton>(R.id.rBNuAdmin)

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


        btnUpdateAdmin.setOnClickListener {
            val username = eTHoTenAdmin.text.toString()
            val phoneNumber = eTSDTAdmin.text.toString()
            val address = eTDiaChiAdmin.text.toString()
            val date = tVNgaySinhAdmin.text.toString()
            val selectedGenderId = rGGioiTinhAdmin.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                val selectedGender = findViewById<RadioButton>(selectedGenderId)
                selectedGender.text.toString()
            } else {
                ""
            }
            if (username.isNotEmpty() && phoneNumber.isNotEmpty() && address.isNotEmpty() && gender.isNotEmpty() && date.isNotEmpty()) {
                // Tạo đối tượng User
                val admin = Admin(
                    id_admin = userId,
                    username = username,
                    date = date,
                    phoneNumber = phoneNumber,
                    gender = gender,
                    address = address,
                    email = getEmail(),
                    role = "user")

                // Lưu vào Firestore, ví dụ sử dụng UID người dùng làm khóa
                val userId = FirebaseAuth.getInstance().currentUser?.uid // Bạn sẽ thay bằng user ID thực từ Firebase Auth
                if (userId != null){
                    db.collection("admins").document(userId)
                        .set(admin)
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