package com.example.vtpa_b2013518_lvtn.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.admin.AdminCancelAppointmentActivity
import com.example.vtpa_b2013518_lvtn.admin.AdminConfAppointmentActivity
import java.text.SimpleDateFormat
import java.util.Locale

class AppointmentAdmin (private var appointments: List<Appointment>):
RecyclerView.Adapter<AppointmentAdmin.AppointmentAdminViewHolder>(){

    class AppointmentAdminViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val email: TextView = view.findViewById(R.id.tVIREmail)
        val id_app: TextView = view.findViewById(R.id.tVIRAppId)
        val id_user: TextView = view.findViewById(R.id.tVIRUserId)
        val username: TextView = view.findViewById(R.id.tVIRUser)
        val service: TextView = view.findViewById(R.id.tVIRService)
        val date: TextView = view.findViewById(R.id.tVIRDate)
        val hour: TextView = view.findViewById(R.id.tVIRHour)
        val phoneNumber: TextView = view.findViewById(R.id.tVIRPhoneNumber)
        val note: TextView = view.findViewById(R.id.tVIRNote)
        val status: TextView = view.findViewById(R.id.tVIRStatus)
        val iVIRYes: ImageView = itemView.findViewById(R.id.iVIRYes)
        val iVIRNo: ImageView = itemView.findViewById(R.id.iVIRNo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentAdminViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_item_review, parent,false)
        return AppointmentAdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentAdminViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.email.text = "Email: ${appointment.email}"
        holder.id_app.text = "Appointment ID: ${appointment.id_app}"
        holder.id_user.text = "User ID: ${appointment.id_user}"
        holder.username.text = "Họ và tên: ${appointment.username}"
        holder.service.text = "Dịch vụ: ${appointment.service}"
        holder.date.text = "Ngày hẹn: ${appointment.date}"
        holder.hour.text = "Giờ hẹn: ${appointment.hour}"
        holder.note.text = "Ghi chú: ${appointment.note}"
        holder.phoneNumber.text = "Số điện thoại: ${appointment.phoneNumber}"
        holder.status.text = "Trạng thái: ${appointment.status}"

        holder.iVIRYes.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AdminConfAppointmentActivity::class.java)
            intent.putExtra("email", appointment.email) // Truyền ID của Appointment
            intent.putExtra("appointmentId", appointment.id_app)
            intent.putExtra("userId", appointment.id_user)
            intent.putExtra("username", appointment.username)
            intent.putExtra("service", appointment.service)
            intent.putExtra("date", appointment.date)
            intent.putExtra("hour", appointment.hour)
            intent.putExtra("note", appointment.note)
            intent.putExtra("status", appointment.status)
            intent.putExtra("phoneNumber", appointment.phoneNumber)
            context.startActivity(intent)
        }
        holder.iVIRNo.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AdminCancelAppointmentActivity::class.java)
            intent.putExtra("email", appointment.email) // Truyền ID của Appointment
            intent.putExtra("appointmentId", appointment.id_app)
            intent.putExtra("userId", appointment.id_user)
            intent.putExtra("username", appointment.username)
            intent.putExtra("service", appointment.service)
            intent.putExtra("date", appointment.date)
            intent.putExtra("hour", appointment.hour)
            intent.putExtra("note", appointment.note)
            intent.putExtra("status", appointment.status)
            intent.putExtra("phoneNumber", appointment.phoneNumber)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = appointments.size
    fun updateAppointments(appointmentList: List<Appointment>) {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        appointments = appointmentList.sortedByDescending {
            sdf.parse(it.date)

        }
        notifyDataSetChanged()
    }
}