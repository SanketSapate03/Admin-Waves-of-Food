package com.example.wavesoffood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.CartItemBinding

class CartAdapter(
    private val cartItemsName: MutableList<String>,
    private val cartItemPrice: MutableList<String>,
    private val cartImage: MutableList<Int>,
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val itemQuantity = IntArray(cartItemsName.size)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cartItemsName.size
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantity[position]
                cartFoodName.text = cartItemsName[position]
                cartFoodPrice.text = cartItemPrice[position]
                cartFoodImage.setImageResource(cartImage[position])
                cartIFoodQuantity.text = quantity.toString()

                minusBtn.setOnClickListener {
                    decQuantity(position)
                }

                plusBtn.setOnClickListener {
                    incQuantity(position)
                }

                deleteBtn.setOnClickListener {
                    val itemPosition=adapterPosition
                    if(itemPosition!=RecyclerView.NO_POSITION){
                        deleteItem(itemPosition)
                    }

                }


            }
        }

        private fun decQuantity(position: Int) {
            if (itemQuantity[position] >1) {
                itemQuantity[position]--
                binding.cartIFoodQuantity.text = itemQuantity[position].toString()
            }
        }

        private fun incQuantity(position: Int) {
            if (itemQuantity[position] < 10) {
                itemQuantity[position]++
                binding.cartIFoodQuantity.text = itemQuantity[position].toString()
            }
        }

        private fun deleteItem(position: Int) {
            cartItemsName.removeAt(position)
            cartItemPrice.removeAt(position)
            cartImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItemsName.size)
        }

    }
}


