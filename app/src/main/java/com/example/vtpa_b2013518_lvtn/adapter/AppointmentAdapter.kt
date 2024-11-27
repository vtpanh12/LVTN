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
import com.example.vtpa_b2013518_lvtn.admin.AdminDentistAssignmentEditActivity
import com.example.vtpa_b2013518_lvtn.appointment.AppointmentEditActivity

class AppointmentAdapter(private val appointments: List<Appointment>) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.id_dentist.text = "Appointment ID: ${appointment.id_dentist}"
        holder.username.text = "Họ và tên: ${appointment.username}"
        holder.email.text = "Email: ${appointment.email}"
        holder.service.text = "Dịch vụ: ${appointment.service}"
        holder.date.text = "Ngày hẹn: ${appointment.date}"
        holder.hour.text = "Giờ hẹn: ${appointment.hour}"
        holder.note.text = "Ghi chú: ${appointment.note}"
        holder.phoneNumber.text = "Số điện thoại: ${appointment.phoneNumber}"
        holder.status.text = "Trạng thái: ${appointment.status}"

        holder.iVIFEdit.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AppointmentEditActivity::class.java)
            intent.putExtra("email", appointment.email) // Truyền ID của Appointment
            intent.putExtra("dentistId", appointment.id_dentist)
            intent.putExtra("username", appointment.username)
            intent.putExtra("date", appointment.date)
            intent.putExtra("hour", appointment.hour)
            intent.putExtra("phoneNumber", appointment.phoneNumber)
            intent.putExtra("note", appointment.note)
            intent.putExtra("status", appointment.status)
            intent.putExtra("id_dentist", appointment.id_dentist)
            context.startActivity(intent)
        }
    }
    override fun getItemCount() = appointments.size
}
