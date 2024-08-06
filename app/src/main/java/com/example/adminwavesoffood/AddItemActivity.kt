package com.example.adminwavesoffood

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminwavesoffood.databinding.ActivityAddItemBinding
import com.example.adminwavesoffood.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {
    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    //food item details
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private var foodImageUri: Uri? = null
    private lateinit var foodDescription: String
    private lateinit var foodIngredients: String

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initialise firebase and database instance
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        //click listner on add item btn
        binding.AddItemButton.setOnClickListener {
            //get data from all fields
            foodName = binding.foodNameEditText.text.toString().trim()
            foodPrice = binding.foodPriceEditText.text.toString().trim()
            foodDescription = binding.descriptionEdittext.text.toString().trim()
            foodIngredients = binding.ingredientsEditText.text.toString().trim()

            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredients.isBlank())) {
                uploadData()
                Toast.makeText(this, "Item added successfully 👌", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Fill all the details 😒", Toast.LENGTH_SHORT).show()
            }
        }


        //click listner on image selector
        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        //click listner on card view to pick image
        binding.cardView2.setOnClickListener {
            pickImage.launch("image/*")
        }

        //click listner on back btn
        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    //uploading data to database and storage
    private fun uploadData() {
        //get reference to the menu , node in the database
        val menuRef = database.getReference("menu")

        //generate unique id for new menu item
        val newItemKey = menuRef.push().key

        if (foodImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            //upload
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    //create new menu item
                    val newItem = AllMenu(
                        newItemKey,
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodImage = downloadUrl.toString(),
                        foodDescription = foodDescription,
                        foodIngredients = foodIngredients
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Data upload successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Data upload Failed", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show()
        }

    }

    //picker launcher to select an image
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                binding.selectedImage.setImageURI(uri)
                foodImageUri=uri
            }
        }
}