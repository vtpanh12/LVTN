package com.example.vtpa_b2013518_lvtn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtpa_b2013518_lvtn.R


class FeedBackAdapter (private var feedbacks: List<FeedBack>):
    RecyclerView.Adapter<FeedBackAdapter.FeedBackViewHolder>(){
    class FeedBackViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val feedB: TextView = view.findViewById(R.id.tVFBack)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedBackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feedback, parent, false)
        return FeedBackViewHolder(view)
    }


    override fun onBindViewHolder(holder: FeedBackViewHolder, position: Int) {
        val feedback = feedbacks[position]
        holder.feedB.text = "Nội dung: ${feedback.feedBack}"
    }
    override fun getItemCount() = feedbacks.size
}