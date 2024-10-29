package com.example.vtpa_b2013518_lvtn.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.admin.AdminConfAppointmentActivity
import com.example.vtpa_b2013518_lvtn.admin.AdminDentistEditActivity


class DentistAdapter (private val dentists: List<Dentist>):
        RecyclerView.Adapter<DentistAdapter.DentistAdapterViewHolder>(){
    class DentistAdapterViewHolder (view: View): RecyclerView.ViewHolder(view){
        val email: TextView = view.findViewById(R.id.tVIDEmail)
        val id_dentist: TextView = view.findViewById(R.id.tVIDDentistId)
        val username: TextView = view.findViewById(R.id.tVIDDentist)
        val date: TextView = view.findViewById(R.id.tVIDDate)
        val phoneNumber: TextView = view.findViewById(R.id.tVIDPhoneNumber)
        val gender: TextView = view.findViewById(R.id.tVIDGender)
        val address: TextView = view.findViewById(R.id.tVIDAddress)
        val specialty: TextView = view.findViewById(R.id.tVIDSpecialty)
        val role: TextView = view.findViewById(R.id.tVIDRole)
        val iVIDEdit: ImageView = itemView.findViewById(R.id.iVIDEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DentistAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_item_dentist, parent,false)
        return DentistAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: DentistAdapterViewHolder, position: Int) {
        val dentist = dentists[position]
        holder.email.text = "Email: ${dentist.email}"
        holder.id_dentist.text = "Appointment ID: ${dentist.id_dentist}"
        holder.username.text = "Họ và tên: ${dentist.username}"
        holder.phoneNumber.text = "Dịch vụ: ${dentist.phoneNumber}"
        holder.date.text = "Ngày sinh: ${dentist.date}"
        holder.specialty.text = "Chuyên khoa: ${dentist.specialty}"
        holder.gender.text = "Giới tính: ${dentist.gender}"
        holder.address.text = "Địa chỉ: ${dentist.address}"
        holder.role.text = "Chức vụ: ${dentist.role}"
        holder.iVIDEdit.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AdminDentistEditActivity::class.java)
            intent.putExtra("email", dentist.email) // Truyền ID của Appointment
            intent.putExtra("dentistId", dentist.id_dentist)
            intent.putExtra("username", dentist.username)
            intent.putExtra("date", dentist.date)
            intent.putExtra("phoneNumber", dentist.phoneNumber)
            intent.putExtra("specialty", dentist.specialty)
            intent.putExtra("gender", dentist.gender)
            intent.putExtra("address", dentist.address)
            intent.putExtra("role", dentist.role)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() =dentists.size

}