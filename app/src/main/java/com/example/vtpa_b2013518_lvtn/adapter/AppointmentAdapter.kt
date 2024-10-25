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

class AppointmentAdapter(private val appointments: List<Appointment>) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id_app: TextView = view.findViewById(R.id.tVIFAppId)
        val email: TextView = view.findViewById(R.id.tVIFEmail)
        val username: TextView = view.findViewById(R.id.tVIFUser)
        val service: TextView = view.findViewById(R.id.tVIFService)
        val date: TextView = view.findViewById(R.id.tVIFDate)
        val hour: TextView = view.findViewById(R.id.tVIFHour)
        val phoneNumber: TextView = view.findViewById(R.id.tVIFPhoneNumber)
        val note: TextView = view.findViewById(R.id.tVIFNote)
        val status: TextView = view.findViewById(R.id.tVIFStatus)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.id_app.text = "Appointment ID: ${appointment.id_app}"
        holder.username.text = "Họ và tên: ${appointment.username}"
        holder.email.text = "Email: ${appointment.email}"
        holder.service.text = "Dịch vụ: ${appointment.service}"
        holder.date.text = "Ngày hẹn: ${appointment.date}"
        holder.hour.text = "Giờ hẹn: ${appointment.hour}"
        holder.note.text = "Ghi chú: ${appointment.note}"
        holder.phoneNumber.text = "Số điện thoại: ${appointment.phoneNumber}"
        holder.status.text = "Trạng thái: ${appointment.status}"


    }

    override fun getItemCount() = appointments.size
}
