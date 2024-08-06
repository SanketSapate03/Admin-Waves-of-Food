package com.example.adminwavesoffood

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminwavesoffood.databinding.ActivityAdminProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminRef = database.reference.child("vendor")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.saveInfoBtn.setOnClickListener {
            updateUserData()
        }



        binding.nameTextView.isEnabled = false
        binding.addressTextView.isEnabled = false
        binding.emailTextView.isEnabled = false
        binding.phoneTextView.isEnabled = false
        binding.passwordTextView.isEnabled = false
        binding.saveInfoBtn.isEnabled = false


        var isEnable = false
        binding.editYourProfile.setOnClickListener {
            isEnable = !isEnable

            binding.nameTextView.isEnabled = isEnable
            binding.addressTextView.isEnabled = isEnable
            binding.emailTextView.isEnabled = isEnable
            binding.phoneTextView.isEnabled = isEnable
            binding.passwordTextView.isEnabled = isEnable




            if (isEnable) {
                binding.nameTextView.requestFocus()
                binding.saveInfoBtn.isEnabled = isEnable
            }
        }

        retrieveUserData()
    }


    private fun retrieveUserData() {
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null) {
            val userRef = adminRef.child(currentUserUid)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val ownerName = snapshot.child("name").getValue()
                        val email = snapshot.child("email").getValue()
                        val password = snapshot.child("password").getValue()
                        val address: Any? = snapshot.child("address").getValue()
                        val phone: Any? = snapshot.child("phone").getValue()

                        setDataToTextView(ownerName, email, password, address, phone)


                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?,
    ) {
        binding.nameTextView.setText(ownerName.toString())
        binding.emailTextView.setText(email.toString())
        binding.passwordTextView.setText(password.toString())
        binding.addressTextView.setText(address.toString())
        binding.phoneTextView.setText(phone.toString())

    }

    private fun updateUserData() {
        val updateName = binding.nameTextView.text.toString()
        val updateEmail = binding.emailTextView.text.toString()
        val updatePassword = binding.passwordTextView.text.toString()
        val updateAddress = binding.addressTextView.text.toString()
        val updatePhone = binding.phoneTextView.text.toString()

        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null) {
            val userRef = adminRef.child(currentUserUid)
            userRef.child("name").setValue(updateName)
            userRef.child("email").setValue(updateEmail)
            userRef.child("password").setValue(updatePassword)
            userRef.child("address").setValue(updateAddress)
            userRef.child("phone").setValue(updatePhone)

            Toast.makeText(this, "Profile Updated Successfully ❤️", Toast.LENGTH_SHORT).show()
            //update the email and firebase for firebase authentication
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        } else {
            Toast.makeText(this, "Profile Update Failed 😒", Toast.LENGTH_SHORT).show()
        }
    }

}