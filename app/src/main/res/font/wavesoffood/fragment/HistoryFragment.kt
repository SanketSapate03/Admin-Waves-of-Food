package com.example.wavesoffood.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.R
import com.example.wavesoffood.adapter.BuyAgainAdapter
import com.example.wavesoffood.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val buyAgainFoodName = arrayListOf("food1", "food2", "food3", "food4", "food5")
        val buyAgainFoodPrice = arrayListOf("$1", "$1", "$1", "$1", "$1")
        val buyAgainFoodImage = arrayListOf(
            R.drawable.spicy_fresh_crab1,
            R.drawable.spicy_fresh_crab2,
            R.drawable.spicy_fresh_crab3,
            R.drawable.herbal_panckake,
            R.drawable.green_noddle
        )

        buyAgainAdapter= BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrice,buyAgainFoodImage)
        binding.historyRecyclerView.adapter=buyAgainAdapter
        binding.historyRecyclerView.layoutManager=LinearLayoutManager(requireContext())


    }

    companion object {

    }
}