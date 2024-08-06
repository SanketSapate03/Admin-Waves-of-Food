package com.example.wavesoffood.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.CongratsBottomSheetFragment
import com.example.wavesoffood.PayoutActivity
import com.example.wavesoffood.R
import com.example.wavesoffood.adapter.CartAdapter
import com.example.wavesoffood.databinding.FragmentCartBinding


class CartFragment : Fragment() {
private lateinit var binding:FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCartBinding.inflate(inflater,container,false)

        val cartItemName= listOf("Burger","Samosa","Kachori","Poha")
        val cartItemPrice= listOf("$5","$7","$10","$10")
        val cartItemImage= listOf(
            R.drawable.spicy_fresh_crab1,
            R.drawable.spicy_fresh_crab2,
            R.drawable.spicy_fresh_crab3,
            R.drawable.green_noddle,
        )

        val adapter=CartAdapter(ArrayList(cartItemName),ArrayList(cartItemPrice),ArrayList(cartItemImage))
        binding.cartRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter=adapter


        //set on click listner
        binding.proceedBtn.setOnClickListener{
            val intent= Intent(requireContext(),PayoutActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }

    companion object {

    }
}