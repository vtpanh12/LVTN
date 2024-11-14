package com.example.vtpa_b2013518_lvtn.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.admin.AdminDentistEditActivity
import com.example.vtpa_b2013518_lvtn.dentist.DentistMedicalRecordEditActivity

class MedicalRecordAdapter(
    private val combinedList: List<CombinedMRAPP>,
    private val onEditClick: (CombinedMRAPP) -> Unit
) : RecyclerView.Adapter<MedicalRecordAdapter.MedicalRecordViewHolder>() {

    // ViewHolder cho mỗi item trong RecyclerView
    class MedicalRecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val email: TextView = view.findViewById(R.id.tVMREmail)
        val username: TextView = view.findViewById(R.id.tVMRUsername)
        val time: TextView = view.findViewById(R.id.tVIDDate)
        val phoneNumber: TextView = view.findViewById(R.id.tVMRPhoneNumber)
        val diagnosis: TextView = view.findViewById(R.id.tVMRDiagnosis)
        val treatment: TextView = view.findViewById(R.id.tVMRTreatment)
        val dates: TextView = view.findViewById(R.id.tVMRDates)
        val notes: TextView = view.findViewById(R.id.tVMRNotes)
        val iVMREdit: ImageView = itemView.findViewById(R.id.iVMREdit)


    }

    // Tạo mới ViewHolder khi cần
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalRecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medical_record, parent, false)
        return MedicalRecordViewHolder(view)
    }

    // Gắn dữ liệu cho ViewHolder
    override fun onBindViewHolder(holder: MedicalRecordViewHolder, position: Int) {
        val combinedRecord = combinedList[position]
        holder.email.text = "Email: ${combinedRecord.appointment.email}"
        holder.username.text = "Họ và tên: ${combinedRecord.appointment.username}"
        holder.time.text = "Thời gian: ${combinedRecord.appointment.date}"
        holder.phoneNumber.text="Số điện thoại: ${combinedRecord.appointment.phoneNumber}"
        holder.diagnosis.text = "Chẩn đoán: ${combinedRecord.medicalRecord.diagnosis}"
        holder.treatment.text="Phương pháp điều trị: ${combinedRecord.medicalRecord.treatment}"
        holder.dates.text="Ngày tạo: ${combinedRecord.medicalRecord.date}"
        holder.notes.text="Ghi chú (đơn thuốc, ghi chú, lịch hẹn): \\n " +
                    "${combinedRecord.medicalRecord.prescription} | ${combinedRecord.medicalRecord.notes} | " +
                    "${combinedRecord.medicalRecord.nextAppointment}"
        holder.iVMREdit.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DentistMedicalRecordEditActivity::class.java)
            intent.putExtra("email", combinedRecord.appointment.email) // Truyền
            intent.putExtra("username", combinedRecord.appointment.username)
            intent.putExtra("date", combinedRecord.appointment.date)
            intent.putExtra("phoneNumber", combinedRecord.appointment.phoneNumber)
            intent.putExtra("diagnosis", combinedRecord.medicalRecord.diagnosis)
            intent.putExtra("treatment", combinedRecord.medicalRecord.treatment)
            intent.putExtra("dates", combinedRecord.medicalRecord.date)
            intent.putExtra("time", combinedRecord.appointment.date)
            intent.putExtra("notes",combinedRecord.medicalRecord.prescription
                    + combinedRecord.medicalRecord.notes +combinedRecord.medicalRecord.nextAppointment )
            context.startActivity(intent)
        }
    }
    // Trả về số lượng item
    override fun getItemCount() = combinedList.size
}