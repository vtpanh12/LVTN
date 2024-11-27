package com.example.vtpa_b2013518_lvtn.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.appointment.AppointmentEditActivity

class CombinedAppAdapter(private val data: List<CombinedUser>) :
    RecyclerView.Adapter<CombinedAppAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id_dentist: TextView = view.findViewById(R.id.tVIFDentistId)
        val email: TextView = view.findViewById(R.id.tVIFEmail)
        val username: TextView = view.findViewById(R.id.tVIFUser)
        val service: TextView = view.findViewById(R.id.tVIFService)
        val date: TextView = view.findViewById(R.id.tVIFDate)
        val hour: TextView = view.findViewById(R.id.tVIFHour)
        val phoneNumber: TextView = view.findViewById(R.id.tVIFPhoneNumber)
        val note: TextView = view.findViewById(R.id.tVIFNote)
        val status: TextView = view.findViewById(R.id.tVIFStatus)
        val iVIFEdit: ImageView = itemView.findViewById(R.id.iVIFEdit)
//        val tVIFDentistUsername: TextView = view.findViewById(R.id.tVIFDentistUsername)
//        val tVIFDentistInfo: TextView = view.findViewById(R.id.tVIFDentistInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.email.text = "Email: ${item.appointment.email}"
        holder.username.text = "Họ và tên: ${item.appointment.username}"
        holder.email.text = "Email: ${item.appointment.email}"
        holder.service.text = "Dịch vụ: ${item.appointment.service}"
        holder.date.text = "Ngày hẹn: ${item.appointment.date}"
        holder.hour.text = "Giờ hẹn: ${item.appointment.hour}"
        holder.note.text = "Ghi chú: ${item.appointment.note}"
        holder.phoneNumber.text="Số điện thoại: ${item.appointment.phoneNumber}"
        holder.status.text = "Trạng thái: ${item.appointment.status}"
//        holder.tVIFDentistUsername.text = "Nha sĩ chủ trị: ${item.dentist?.username}"
//        holder.tVIFDentistInfo.text = "Thông tin nha sĩ: ${item.dentist?.email}" +
//                " | ${item.dentist?.phoneNumber}"

        holder.iVIFEdit.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AppointmentEditActivity::class.java)
            intent.putExtra("email", item.appointment.email) // Truyền ID của Appointment
            intent.putExtra("dentistId", item.appointment.id_dentist)
            intent.putExtra("username", item.appointment.username)
            intent.putExtra("date", item.appointment.date)
            intent.putExtra("hour", item.appointment.hour)
            intent.putExtra("phoneNumber", item.appointment.phoneNumber)
            intent.putExtra("note", item.appointment.note)
            intent.putExtra("status", item.appointment.status)
            intent.putExtra("id_dentist", item.appointment.id_dentist)
            intent.putExtra("dentistUser", item.dentist?.username)
            intent.putExtra("dentistEmail", item.dentist?.email)
            intent.putExtra("dentistPhoneNumber", item.dentist?.phoneNumber)
            context.startActivity(intent)
        }
    }
    override fun getItemCount() = data.size
}