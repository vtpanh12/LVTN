package com.example.vtpa_b2013518_lvtn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R

class AppointmentAdmin (private val appointments: List<Appointment>):
RecyclerView.Adapter<AppointmentAdmin.AppointmentAdminViewHolder>(){

    class AppointmentAdminViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id_app: TextView = view.findViewById(R.id.tVIRAppId)
        val username: TextView = view.findViewById(R.id.tVIRUserId)
        val service: TextView = view.findViewById(R.id.tVIRService)
        val date: TextView = view.findViewById(R.id.tVIRDate)
        val hour: TextView = view.findViewById(R.id.tVIRHour)
        val phoneNumber: TextView = view.findViewById(R.id.tVIRPhoneNumber)
        val note: TextView = view.findViewById(R.id.tVIRNote)
        val status: TextView = view.findViewById(R.id.tVIRStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentAdminViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_item_review, parent,false)
        return AppointmentAdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentAdminViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.id_app.text = "Appointment ID: ${appointment.id_app}"
        holder.username.text = "Họ và tên: ${appointment.username}"
        holder.service.text = "Dịch vụ: ${appointment.service}"
        holder.date.text = "Ngày hẹn: ${appointment.date}"
        holder.hour.text = "Giờ hẹn: ${appointment.hour}"
        holder.note.text = "Ghi chú: ${appointment.note}"
        holder.phoneNumber.text = "Số điện thoại: ${appointment.phoneNumber}"
        holder.status.text = "Trạng thái: ${appointment.status}"
    }

    override fun getItemCount() = appointments.size
}