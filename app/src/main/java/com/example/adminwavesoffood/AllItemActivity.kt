package com.example.adminwavesoffood

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.Adapter.MenuItemAdapter
import com.example.adminwavesoffood.databinding.ActivityAllItemBinding
import com.example.adminwavesoffood.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    private lateinit var  databaseReference: DatabaseReference
    private lateinit var  database: FirebaseDatabase
    private  var menuItems:ArrayList<AllMenu> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initialisation
        databaseReference=FirebaseDatabase.getInstance().reference
        retrieveMenuItem()


        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun retrieveMenuItem() {
        database=FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")

        //fetch data from db
        foodRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear existing data before population
                menuItems.clear()

                //loop through each food item
                for(foodSnapshot in snapshot.children){
                    val menuItem=foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
               Log.d("message","${error.message}")
            }

        })
    }

    //set up adapter
    private fun setAdapter() {
        val adapter = MenuItemAdapter(this,menuItems,databaseReference){position->
            deleteMenuItem(position)
        }
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.menuRecyclerView.adapter = adapter
    }

    private fun deleteMenuItem(position: Int) {
val menuItemToDelete=menuItems[position]
        val menuItemPushKey=menuItemToDelete.key
        val foodMenuRef=database.reference.child("menu").child(menuItemPushKey!!)
        foodMenuRef.removeValue().addOnCompleteListener { task->
            if(task.isSuccessful){
                menuItems.removeAt(position)
                binding.menuRecyclerView.adapter?.notifyItemRemoved(position)
                Toast.makeText(this, "Item Deleted Successfully", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Item Deletion Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}