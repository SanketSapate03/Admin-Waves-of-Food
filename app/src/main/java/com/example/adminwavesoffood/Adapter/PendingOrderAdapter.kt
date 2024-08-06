package com.example.adminwavesoffood.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwavesoffood.databinding.PendingItemBinding

class PendingOrderAdapter(
    private val context: Context,
    private val customerNames: MutableList<String>,
    private val foodQuantity: MutableList<String>,
    private val customerFoodImages: MutableList<String>,
    private val itemClicked: OnItemClicked
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    interface OnItemClicked {
        fun onItemClickListner(position: Int)
        fun onItemAcceptClickListner(position: Int)
        fun onItemDispatchClickListner(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding = PendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }

    override fun getItemCount(): Int = customerNames.size

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class PendingOrderViewHolder(private val binding: PendingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false

        fun bind(position: Int) {
            binding.apply {
                customerName.text = customerNames[position]
                quantity.text = foodQuantity[position]

                val uriString = customerFoodImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(foodImage)

                statusBtn.apply {
                    text = if (!isAccepted) "Accept" else "Dispatch"

                    setOnClickListener {
                        if (!isAccepted) {
                            isAccepted = true
                            text = "Dispatch"
                            Toast.makeText(context, "Order Accepted", Toast.LENGTH_SHORT).show()
                            itemClicked.onItemAcceptClickListner(position)  // Trigger Accept action
                        } else {
                            itemClicked.onItemDispatchClickListner(position)  // Trigger Dispatch action
                            removeItem(position)
                            Toast.makeText(context, "Order Dispatched", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                itemView.setOnClickListener {
                    itemClicked.onItemClickListner(position)
                }
            }
        }

        private fun removeItem(position: Int) {
            customerNames.removeAt(position)
            foodQuantity.removeAt(position)
            customerFoodImages.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
