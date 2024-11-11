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

    private var selectedPosition = -1

    inner class DentistConfAppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dentistInfo: TextView = itemView.findViewById(R.id.tVDentistInfo)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxSelectDentist)

        fun bind(dentist: Dentist, position: Int) {
            dentistInfo.text = "Tên: ${dentist.username}\nChuyên khoa: ${dentist.specialty}\nSĐT: ${dentist.phoneNumber}\nEmail: ${dentist.email}"
            checkBox.isChecked = position == selectedPosition

            itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
                onDentistSelected(dentist)
            }

            checkBox.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
                onDentistSelected(dentist)
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
