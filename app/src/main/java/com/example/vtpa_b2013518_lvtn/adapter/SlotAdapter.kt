package com.example.vtpa_b2013518_lvtn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R

class SlotAdapter(private var slotList: List<Pair<String, Slot>>) : RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {

    class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSlotTime: TextView = itemView.findViewById(R.id.tvSlotTime)
        private val tvSlotStatus: TextView = itemView.findViewById(R.id.tvSlotStatus)
        private val iVDetail: ImageView = itemView.findViewById(R.id.iVdetail)

        fun bind(time: String, slot: Slot) {
            tvSlotTime.text = time
            tvSlotStatus.text = if (slot.isBooked) "Đã đặt" else "Chưa đặt"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slot_item, parent, false)
        return SlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val (time, slot) = slotList[position]
        holder.bind(time, slot)
    }

    override fun getItemCount(): Int = slotList.size

    fun updateData(newSlotList: List<Pair<String, Slot>>) {
        slotList = newSlotList
        notifyDataSetChanged()  // Thông báo RecyclerView cập nhật lại dữ liệu
    }

}

