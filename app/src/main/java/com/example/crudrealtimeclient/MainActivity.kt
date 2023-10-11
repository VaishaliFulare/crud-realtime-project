package com.example.crudrealtimeclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.crudrealtimeclient.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val searchVehicleNUmber: String =binding.searchVehicleNUmber.text.toString()
            if (searchVehicleNUmber.isNotEmpty()){
                readData(searchVehicleNUmber)
            }else{
                Toast.makeText(this, "Please Enter the Vehicle Number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(VehicleNumber:String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Vehicle Information")
        databaseReference.child(VehicleNumber).get().addOnSuccessListener {
            if (it.exists()){
                val ownerName=it.child("ownerName").value
                val vehicleBrand=it.child("vehicleBrand").value
                val vehicleRTO=it.child("vehicleRTO").value
                Toast.makeText(this, "Result Found", Toast.LENGTH_SHORT).show()
                binding.searchVehicleNUmber.text.clear()
                binding.readOwnerName.text = ownerName.toString()
                binding.readVehicleBrand.text = vehicleBrand.toString()
                binding.readVehicleRTO.text = vehicleRTO.toString()

            }else{
                Toast.makeText(this, "Vehicle Number is not exist ", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

}