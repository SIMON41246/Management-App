package com.example.byadiproject.Payments

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.byadiproject.databinding.ActivityPaymentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PaymentActivity : AppCompatActivity() {
    val list = ArrayList<Payment>()
    lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id = intent.getStringExtra("id")
        val firebase = Firebase.database
        val myref = firebase.getReference("payment").child(id!!)
        myref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    val value = item.getValue(Payment::class.java)
                    list.add(value!!)
                }
                val adapter = AdapterPayment(this@PaymentActivity, list)
                binding.recyclePayment.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onStart() {
        super.onStart()
        if (binding.searchPayment != null) {
            binding.searchPayment.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    search(newText!!)
                    return true
                }

            })
        }


    }

    private fun search(newText: String) {
        val Deal = ArrayList<Payment>()
        for (item in list) {
            if (item.payment.toString().toLowerCase()
                    .contains(newText.toLowerCase()) || item.date.toLowerCase()
                    .contains(newText.toLowerCase())
            ) {
                Deal.add(item)
            }
        }
        val adpter = AdapterPayment(this, Deal)
        binding.recyclePayment.adapter = adpter
    }
}