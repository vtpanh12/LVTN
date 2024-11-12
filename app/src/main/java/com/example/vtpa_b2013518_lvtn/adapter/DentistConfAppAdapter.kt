package com.example.vtpa_b2013518_lvtn.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.admin.AdminDentistEditActivity

class DentistConfAppAdapter(
    private val dentists: List<Dentist>,
    private val onDentistSelected: (Dentist) -> Unit
) : RecyclerView.Adapter<DentistConfAppAdapter.DentistConfAppViewHolder>() {

    private var selectedPosition = -1  // Chỉ cho phép chọn một checkbox duy nhất

    inner class DentistConfAppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dentistInfo: TextView = itemView.findViewById(R.id.tVDentistInfo)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxSelectDentist)

        fun bind(dentist: Dentist, position: Int) {
            dentistInfo.text = "Tên: ${dentist.username}\nChuyên khoa: ${dentist.specialty}\nSĐT: ${dentist.phoneNumber}\nEmail: ${dentist.email}"

            // Cập nhật trạng thái checkbox
            checkBox.isChecked = position == selectedPosition

            // Click vào item để chọn hoặc bỏ chọn checkbox
            itemView.setOnClickListener {
                selectedPosition = position // Cập nhật vị trí được chọn
                notifyDataSetChanged() // Cập nhật toàn bộ danh sách
                onDentistSelected(dentist) // Gọi callback khi có bác sĩ được chọn
            }

            // Click vào checkbox để chọn hoặc bỏ chọn
            checkBox.setOnClickListener {
                selectedPosition = if (checkBox.isChecked) position else -1
                notifyDataSetChanged() // Cập nhật toàn bộ danh sách
                onDentistSelected(dentist) // Gọi callback khi có bác sĩ được chọn
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DentistConfAppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dentist_confapp, parent, false)
        return DentistConfAppViewHolder(view)
    }

    override fun onBindViewHolder(holder: DentistConfAppViewHolder, position: Int) {
        holder.bind(dentists[position], position)
    }

    override fun getItemCount() = dentists.size
}

