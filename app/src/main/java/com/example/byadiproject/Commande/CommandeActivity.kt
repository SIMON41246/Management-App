package com.example.byadiproject.Commande

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.byadiproject.databinding.ActivityCommandeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CommandeActivity : AppCompatActivity() {
    val list = ArrayList<Commande>()
    private lateinit var binding: ActivityCommandeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCommandeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id = intent.getStringExtra("id")
        val firebase = Firebase.database
        val myref = firebase.getReference("Command").child(id!!)


        myref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (item in snapshot.children) {
                    val value = item.getValue(Commande::class.java)
                    list.add(value!!)
                }
                val adapter = AdapterCommand(this@CommandeActivity, list)
                binding.recycleCommande.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }

    override fun onStart() {
        super.onStart()
        if (binding.searchviewCommande != null) {
            binding.searchviewCommande.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchCommande(newText!!)
                    return true
                }

            })

        }
    }

    private fun searchCommande(newText: String) {
        val Deal = ArrayList<Commande>()
        for (item in list) {
            if (item.total.toString().toLowerCase()
                    .contains(newText.toLowerCase()) || item.count.toString().toLowerCase()
                    .contains(newText.toLowerCase()) || item.date.toLowerCase()
                    .contains(newText.toLowerCase())
            ) {
                Deal.add(item)
            }
        }
        val adpter = AdapterCommand(this, Deal)
        binding.recycleCommande.adapter = adpter
    }


}