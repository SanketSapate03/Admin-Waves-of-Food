package com.example.adminwavesoffood.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwavesoffood.databinding.OrderDetailsItemBinding

class OrderDetailsAdapter(
    private var context: Context,
    private var foodNames: ArrayList<String>,
    private var foodImages: ArrayList<String>,
    private var foodQuantitys: ArrayList<Int>,
    private var foodPrices: ArrayList<String>,
) : RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding =
            OrderDetailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderDetailsViewHolder(binding)
    }

    override fun getItemCount(): Int = foodNames.size


    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class OrderDetailsViewHolder(private val binding: OrderDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodName.text=foodNames[position]
                foodPrice.text=foodPrices[position]
                foodQuantity.text= foodQuantitys[position].toString()

                val uriString=foodImages[position]
                val uri= Uri.parse(uriString)
                Glide.with(context).load(uri).into(foodImage)
            }
        }

    }
}