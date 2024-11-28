package com.example.vtpa_b2013518_lvtn.users

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
import com.example.vtpa_b2013518_lvtn.adapter.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.Calendar

class UsersActivity : AppCompatActivity() {

    lateinit var eTHoTen: EditText
    lateinit var tVNgaySinh: TextView
    lateinit var eTSDT: EditText
    lateinit var rGGioiTinh : RadioGroup
    lateinit var eTDiaChi: EditText
    lateinit var eTEmail: EditText
    private lateinit var btnUpdateUser: Button

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_users)
        tVNgaySinh = findViewById(R.id.tVNgaySinh)
        btnUpdateUser = findViewById(R.id.btnCapNhatUser)
        eTHoTen = findViewById(R.id.eTHoVaTen)
        eTSDT = findViewById(R.id.eTSDT)
        rGGioiTinh = findViewById(R.id.rGGioiTinh)
        eTDiaChi = findViewById(R.id.eTDiaChi)
        eTEmail = findViewById(R.id.eTEmail)
        val iVBack = findViewById<ImageView>(R.id.iVBackUser)

        iVBack.setOnClickListener {
            finish()
        }

        tVNgaySinh.setOnClickListener {
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
                    tVNgaySinh.text = formattedDate
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
            eTEmail.setText(Editable.Factory.getInstance().newEditable(email))

            //Toast.makeText(this, "Email của người dùng: $email", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Không có người dùng nào đang đăng nhập", Toast.LENGTH_SHORT).show()
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // Hiển thị thông tin lên các view
                        eTHoTen.setText(document.getString("username"))
                        eTSDT.setText(document.getString("phoneNumber"))
                        eTDiaChi.setText(document.getString("address"))
                        tVNgaySinh.text = document.getString("date") // Hiển thị ngày sinh


                        // Cập nhật giới tính
                        val gender = document.getString("gender")
                        if (gender != null) {
                            val rbNam = findViewById<RadioButton>(R.id.rBNam)
                            val rbNu = findViewById<RadioButton>(R.id.rBNu)

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


        btnUpdateUser.setOnClickListener {
            val username = eTHoTen.text.toString()
            val phoneNumber = eTSDT.text.toString()
            val address = eTDiaChi.text.toString()
            val date = tVNgaySinh.text.toString()
            val selectedGenderId = rGGioiTinh.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                val selectedGender = findViewById<RadioButton>(selectedGenderId)
                selectedGender.text.toString()
            } else {
                ""
            }
            if (username.isNotEmpty() && phoneNumber.isNotEmpty() && address.isNotEmpty() && gender.isNotEmpty() && date.isNotEmpty()) {
                // Tạo đối tượng User
                val user = User(
                    id_user = userId,
                    username = username,
                    date = date,
                    phoneNumber = phoneNumber,
                    gender = gender,
                    address = address,
                    email = getEmail(),
                    role = "user")

                // Lưu vào Firestore, ví dụ sử dụng UID người dùng làm khóa
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null){
                    db.collection("users").document(userId)
                        .set(user)
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