package com.example.wavesoffood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adapter.MenuAdapter
import com.example.wavesoffood.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        //set on back button
        binding.menuBackBtn.setOnClickListener {
            dismiss()
        }

        val menuItemName = listOf(
            "Burger",
            "Samosa",
            "Kachori",
            "Poha",
            "pancake",
            "crunchy",
            "scab",
            "Burger",
            "Samosa",
            "Kachori",
            "Poha",
            "pancake",
            "crunchy",
            "scab"
        )
        val menuItemPrice = listOf(
            "$5",
            "$7",
            "$10",
            "$10",
            "$7",
            "$7",
            "$7",
            "$5",
            "$7",
            "$10",
            "$10",
            "$7",
            "$7",
            "$7"
        )
        val menuItemImage = listOf(
            R.drawable.spicy_fresh_crab1,
            R.drawable.spicy_fresh_crab2,
            R.drawable.spicy_fresh_crab3,
            R.drawable.green_noddle,
            R.drawable.herbal_panckake,
            R.drawable.spicy_fresh_crab3,
            R.drawable.spicy_fresh_crab2,
            R.drawable.spicy_fresh_crab1,
            R.drawable.spicy_fresh_crab2,
            R.drawable.spicy_fresh_crab3,
            R.drawable.green_noddle,
            R.drawable.herbal_panckake,
            R.drawable.spicy_fresh_crab3,
            R.drawable.spicy_fresh_crab2,
        )

        val adapter =
            MenuAdapter(ArrayList(menuItemName), ArrayList(menuItemPrice), ArrayList(menuItemImage),requireContext())

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {

    }
}