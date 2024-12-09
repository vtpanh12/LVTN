package com.example.vtpa_b2013518_lvtn.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.dentist.DentistMedicalRecordEditActivity

class CombinedAdapter(private val combines: List<Combined>):
    RecyclerView.Adapter<CombinedAdapter.CombinedAdapterViewHolder>(){
    class CombinedAdapterViewHolder (view: View): RecyclerView.ViewHolder(view){
        val email: TextView = view.findViewById(R.id.tVMREmail)
        val username: TextView = view.findViewById(R.id.tVMRUsername)
        val time: TextView = view.findViewById(R.id.tVMRTime)
        val phoneNumber: TextView = view.findViewById(R.id.tVMRPhoneNumber)
        val diagnosis: TextView = view.findViewById(R.id.tVMRDiagnosis)
        val treatment: TextView = view.findViewById(R.id.tVMRTreatment)
        val dates: TextView = view.findViewById(R.id.tVMRDates)
        val notes: TextView = view.findViewById(R.id.tVMRNotes)
        //val iVMREdit: ImageView = itemView.findViewById(R.id.iVMREdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CombinedAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medical_record, parent,false)
        return CombinedAdapterViewHolder(view)
    }

    override fun getItemCount() = combines.size

    override fun onBindViewHolder(holder: CombinedAdapterViewHolder, position: Int) {
        val combined = combines[position]
        Log.d("Adapter", "Binding data for position")
        holder.email.text = "Email: ${combined.appointmentEmail}"
        holder.username.text = "Họ và tên: ${combined.appointmentUsername}"
        holder.time.text = "Thời gian: "+  "${combined.appointmentDate}  | ${combined.appointmentHour}"
        holder.phoneNumber.text="Số điện thoại: ${combined.appointmentPhoneNumber}"
        holder.diagnosis.text = "Chẩn đoán: ${combined.medicalRecordDiagnosis}"
        holder.treatment.text="Phương pháp điều trị: ${combined.medicalRecordTreatment}"
        holder.dates.text="Ngày tạo: ${combined.medicalRecordDates}"
        holder.notes.text="Ghi chú (đơn thuốc, ghi chú, lịch hẹn): \n " +
                "${combined.medicalRecordPrescription} | ${combined.medicalRecordNotes} | " +
                "${combined.medicalRecordNextAppointment}"
//        holder.iVMREdit.setOnClickListener {
//            val context = holder.itemView.context
//            val intent = Intent(context, DentistMedicalRecordEditActivity::class.java)
//            intent.putExtra("email", combined.appointmentEmail) // Truyền
//            intent.putExtra("username", combined.appointmentUsername)
//            intent.putExtra("time", combined.appointmentDate)
//            intent.putExtra("phoneNumber", combined.appointmentPhoneNumber)
//            intent.putExtra("diagnosis", combined.medicalRecordDiagnosis)
//            intent.putExtra("treatment", combined.medicalRecordTreatment)
//            intent.putExtra("dates", combined.medicalRecordDates)
//            intent.putExtra("notes",combined.medicalRecordPrescription
//                    + combined.medicalRecordNotes +combined.medicalRecordNextAppointment )
//            context.startActivity(intent)
//        }
    }
}