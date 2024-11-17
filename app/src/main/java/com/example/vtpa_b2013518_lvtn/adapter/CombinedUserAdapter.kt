package com.example.vtpa_b2013518_lvtn.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.admin.AdminMedicalRecordEditActivity

class CombinedUserAdapter(private val data: List<CombinedData>) :
    RecyclerView.Adapter<CombinedUserAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val email: TextView = view.findViewById(R.id.tVAMREmail)
        val username: TextView = view.findViewById(R.id.tVAMRUsername)
        val phoneNumber: TextView = view.findViewById(R.id.tVAMRPhoneNumber)
        val time: TextView = view.findViewById(R.id.tVAMRTime)
        val diagnosis: TextView = view.findViewById(R.id.tVAMRDiagnosis)
        val treatment: TextView = view.findViewById(R.id.tVAMRTreatment)
        val dates: TextView = view.findViewById(R.id.tVAMRDates)
        val payment: TextView = view.findViewById(R.id.tVAMRPayment)
        val notes: TextView = view.findViewById(R.id.tVAMRNotes)
        val dentistName: TextView = view.findViewById(R.id.tVAMRDentist)
        val price: TextView = view.findViewById(R.id.tVAMRPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_combined_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.email.text = "Email: ${item.appointment.email}"
        holder.username.text = "Họ và tên khách hàng: ${item.appointment.username}"
        holder.time.text = "Thời gian: "+  "${item.appointment.date}  | ${item.appointment.hour}"
        holder.phoneNumber.text="Số điện thoại: ${item.appointment.phoneNumber}"
        holder.diagnosis.text = "Chẩn đoán: ${item.medicalrecord.diagnosis}"
        holder.dentistName.text = "Bác sĩ chủ trị: ${item.dentist.username}"
        holder.treatment.text="Phương pháp điều trị: ${item.medicalrecord.treatment}"
        holder.dates.text="Ngày tạo: ${item.medicalrecord.date}"
        holder.notes.text="Ghi chú (đơn thuốc, ghi chú, lịch hẹn): \n " +
                "${item.medicalrecord.prescription} | ${item.medicalrecord.notes} | " +
                "${item.medicalrecord.nextAppointment}"
        holder.price.text = "Tổng tiền: ${item.medicalrecord.price}"
    }
    override fun getItemCount() = data.size
}