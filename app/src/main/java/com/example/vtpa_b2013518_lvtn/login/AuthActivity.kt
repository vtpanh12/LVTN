package com.example.vtpa_b2013518_lvtn.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.activity.IndexActivity
import com.example.vtpa_b2013518_lvtn.databinding.ActivityAuthBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var resendButton: Button
    private lateinit var timerTextView: TextView

    //tạo 1 handler de xu ly tac vu tren luong chinh (MainLooper)
    private val handler = Handler(Looper.getMainLooper())  // Sử dụng để kiểm tra xem email da duoc xac thuc hay chua

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val userEmail = intent.getStringExtra("USER_EMAIL")
        val noidung = binding.tvEmail
        noidung.text= "Chúng tôi đã gửi đến địa chỉ email của bạn “$userEmail” đường liên kết để xác thực tài khoản của bạn"
        timerTextView = findViewById(R.id.tvCountTimer)
        resendButton = findViewById(R.id.btnResendAuth)

        resendButton.isEnabled = false

        // Khởi tạo bộ đếm ngược 60 giây
        startCountDownTimer()

        // Xử lý sự kiện khi người dùng nhấn nút "Gửi lại email"
        resendButton.setOnClickListener {
            resendVerificationEmail()
        }

        startCheckingEmailVerification()
    }

    private fun startCountDownTimer() {
        //bo dem nguoc 60s
        object : CountDownTimer(60000, 1000) {  // 60000ms = 60s
            //ham duoc goi khi thoi gian giam xuong /1000 = 1s
            override fun onTick(millisUntilFinished: Long) {
                // Cập nhật giao diện mỗi giây (1000)
                val secondsRemaining = millisUntilFinished / 1000
                timerTextView.text = "Gửi lại email sau: $secondsRemaining giây"
            }

            override fun onFinish() {
                // Khi đếm ngược kết thúc, cho phép người dùng gửi lại email
                timerTextView.text = "Bạn có thể gửi lại email xác thực."
                resendButton.isEnabled = true  // Cho phép người dùng nhấn nút gui lai
            }
            //start(): duoc goi de bat dau qua trinh dem nguoc
        }.start()
    }

    private fun resendVerificationEmail() {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Email xác thực đã được gửi lại.", Toast.LENGTH_SHORT).show()
                resendButton.isEnabled = false  // Vô hiệu hóa gửi lại email
                startCountDownTimer()  // Khởi động lại bộ đếm ngược
            } else {
                Toast.makeText(this, "Gửi lại email xác thực thất bại.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun startCheckingEmailVerification() {
        //postDelayed thuc thi 1 Runnable sau 1 khoang thoi gian deplay
        handler.postDelayed(object : Runnable {
            override fun run() {
                //lay user hien tai cua Firebase
                val user = auth.currentUser
                //user?.reload() gọi lại Firebase để cập nhật trạng thái của người dùng hiện tại.
                user?.reload()?.addOnCompleteListener { task ->
                    if (user.isEmailVerified) {
                        // Email đã được xác thực, chuyển sang IndexActivity
                        Toast.makeText(this@AuthActivity, "Email đã được xác thực.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@AuthActivity, IndexActivity::class.java)
                        startActivity(intent)
                        finish()  // dong SignupActivity
                    } else {
                        // Kiểm tra lại sau 3 giây
                        //Giup tao 1 loop de kiem tra xem email da duoc xac thuc chua
                        handler.postDelayed(this, 3000)
                    }
                }
            }
        }, 3000)  // Bắt đầu kiểm tra sau 3 giây
    }

    override fun onDestroy() {
        //onDestroy(): Đây là phương thức được gọi khi Activity bị hủy.
        // Trong hàm này, handler.removeCallbacksAndMessages(null) sẽ xóa bỏ mọi callback
        // hoặc thông điệp đang chờ xử lý để đảm bảo rằng không có kiểm tra nào được thực hiện sau khi Activity bị đóng.
        super.onDestroy()
        //huy tat ca cac callback va messages ma chua duoc thuc thi
        handler.removeCallbacksAndMessages(null)  // Ngừng kiểm tra khi Activity bị hủy
    }
}