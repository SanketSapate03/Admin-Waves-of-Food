package com.example.adminwavesoffood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.adminwavesoffood.Adapter.DelieveryAdapter
import com.example.adminwavesoffood.databinding.ActivityOutForDelieveryBinding
import com.example.adminwavesoffood.model.OrderDetailsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OutForDelieveryActivity : AppCompatActivity() {
    private val binding:ActivityOutForDelieveryBinding by lazy{
        ActivityOutForDelieveryBinding.inflate(layoutInflater)
    }
    private lateinit var database:FirebaseDatabase
    private  var listOfCompleteOrderList: ArrayList<OrderDetailsModel> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        //retrieve and display completed order
        retrieveCompleteOrderDetails()
    }

    private fun retrieveCompleteOrderDetails() {
        //initialise firebase database
        database =FirebaseDatabase.getInstance()
        val completeOrderRef=database.reference.child("CompletedOrder")
            .orderByChild("currentTime")
        completeOrderRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear the list before populating with new data
                listOfCompleteOrderList.clear()
                for(orderSnapshot in snapshot.children){
                    val completeOrder=orderSnapshot.getValue(OrderDetailsModel::class.java )
                    completeOrder?.let {
                        listOfCompleteOrderList.add(it)
                    }
                }

                setDataIntoRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataIntoRecyclerView() {
        //initialisation
        val customerName= mutableListOf<String>()
        val moneyStatus= mutableListOf<Boolean>()

        for(order in listOfCompleteOrderList){
            order.userName?.let { customerName.add(it) }
            moneyStatus.add(order.paymentReceived!!)
        }




        val adapter=DelieveryAdapter(customerName,moneyStatus)
        binding.outForDelieveryRecyclerView.adapter=adapter
        binding.outForDelieveryRecyclerView.layoutManager=LinearLayoutManager(this)

    }
}