package com.example.vtpa_b2013518_lvtn.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R
import com.example.vtpa_b2013518_lvtn.admin.AdminUserEditActivity
import com.example.vtpa_b2013518_lvtn.dentist.DentistAppointmentDetailActivity

class SlotAdapter(private var slotList: List<Pair<String, Slot>>) : RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {

    class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSlotTime: TextView = itemView.findViewById(R.id.tvSlotTime)
        val tvSlotStatus: TextView = itemView.findViewById(R.id.tvSlotStatus)
        val iVDetail: ImageView = itemView.findViewById(R.id.iVdetail)

        fun bind(time: String, slot: Slot) {
            tvSlotTime.text = time
            tvSlotStatus.text = if (slot.isBooked) {
                // Nếu slot đã được đặt, hiện iVDetail và cập nhật trạng thái
                iVDetail.visibility = View.VISIBLE // Hiện hình ảnh chi tiết
                "Đã đặt"
            } else {
                // Nếu slot chưa được đặt, ẩn iVDetail và cập nhật trạng thái
                iVDetail.visibility = View.GONE // Ẩn hình ảnh chi tiết
                "Chưa đặt"
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slot_item, parent, false)
        return SlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val (time, slot) = slotList[position]
        holder.bind(time, slot)
        holder.iVDetail.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DentistAppointmentDetailActivity::class.java)
            intent.putExtra("appointmentId", slot.id_app) // Truyền id_app sang activity
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = slotList.size

    fun updateData(newSlotList: List<Pair<String, Slot>>) {
        slotList = newSlotList
        notifyDataSetChanged()  // Thông báo RecyclerView cập nhật lại dữ liệu
    }

}

