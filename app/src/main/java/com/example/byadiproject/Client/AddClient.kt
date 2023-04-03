package com.example.byadiproject.Client

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.byadiproject.databinding.ActivityAddClientBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddClient : AppCompatActivity(){
    lateinit var binding: ActivityAddClientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddClientBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val database = Firebase.database
        val myRef = database.getReference("Client").push()
        binding.btnClient.setOnClickListener {
            if (binding.tele.text!!.isNotBlank() && binding.Name.text!!.isNotBlank() && binding.price.text!!.isNotBlank()) {
                val client = Client(
                    myRef.key.toString(),
                    binding.Name.text.toString(),
                    binding.price.text.toString().toDouble(),
                    0.0,
                    binding.tele.text.toString()

                )
                myRef.setValue(client)
            }
            binding.Name.text!!.clear()
            binding.price.text!!.clear()
            binding.tele.text!!.clear()
            finish()
        }


    }


}