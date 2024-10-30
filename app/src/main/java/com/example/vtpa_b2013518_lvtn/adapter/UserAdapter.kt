package com.example.vtpa_b2013518_lvtn.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.adapter.DentistAdapter.DentistAdapterViewHolder
import com.example.vtpa_b2013518_lvtn.admin.AdminDentistEditActivity
import com.example.vtpa_b2013518_lvtn.admin.AdminUserEditActivity

class UserAdapter (private val users: List<User>):
    RecyclerView.Adapter<UserAdapter.UserAdapterViewHolder>(){
    class UserAdapterViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val email: TextView = view.findViewById(R.id.tVUIEmail)
        val id_user: TextView = view.findViewById(R.id.tVUIUserId)
        val username: TextView = view.findViewById(R.id.tVUIUser)
        val date: TextView = view.findViewById(R.id.tVUIDate)
        val phoneNumber: TextView = view.findViewById(R.id.tVUIPhoneNumber)
        val gender: TextView = view.findViewById(R.id.tVUIGender)
        val address: TextView = view.findViewById(R.id.tVUIAddress)
        val role: TextView = view.findViewById(R.id.tVUIRole)
        val iVUIEdit: ImageView = itemView.findViewById(R.id.iVUIEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent,false)
        return UserAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapterViewHolder, position: Int) {
        val user = users[position]
        holder.email.text = "Email: ${user.email}"
        holder.id_user.text = "ID Người dùng: ${user.id_user}"
        holder.username.text = "Họ và tên: ${user.username}"
        holder.phoneNumber.text = "Dịch vụ: ${user.phoneNumber}"
        holder.date.text = "Ngày sinh: ${user.date}"
        holder.gender.text = "Giới tính: ${user.gender}"
        holder.address.text = "Địa chỉ: ${user.address}"
        holder.role.text = "Vai trò: ${user.role}"
        holder.iVUIEdit.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AdminUserEditActivity::class.java)
            intent.putExtra("email", user.email) // Truyền ID của Appointment
            intent.putExtra("userId", user.id_user)
            intent.putExtra("username", user.username)
            intent.putExtra("date", user.date)
            intent.putExtra("phoneNumber", user.phoneNumber)
            intent.putExtra("gender", user.gender)
            intent.putExtra("address", user.address)
            intent.putExtra("role", user.role)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() =users.size
}