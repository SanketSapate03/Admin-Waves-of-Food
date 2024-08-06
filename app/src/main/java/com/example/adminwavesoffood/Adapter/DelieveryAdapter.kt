package com.example.adminwavesoffood.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.databinding.DelieveryItemBinding

class DelieveryAdapter(
    private val customerNames: MutableList<String>,
    private val paymentStatuses: MutableList<Boolean>,

    ) : RecyclerView.Adapter<DelieveryAdapter.DelieveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DelieveryViewHolder {
        val binding =
            DelieveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DelieveryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DelieveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size

    inner class DelieveryViewHolder(private val binding: DelieveryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                customerName.text = customerNames[position]
                if (paymentStatuses[position] ==true ){
                    paymentStatus.text="Received"
                }else{
                    paymentStatus.text="notReceived"
                }

                val colorMap = mapOf(
                    true to Color.GREEN,
                    false to Color.RED,
                )

                paymentStatus.setTextColor(colorMap[paymentStatuses[position]] ?: Color.BLACK)
                statusColor.backgroundTintList =
                    ColorStateList.valueOf(colorMap[paymentStatuses[position]] ?: Color.BLACK)
            }
        }

    }
}