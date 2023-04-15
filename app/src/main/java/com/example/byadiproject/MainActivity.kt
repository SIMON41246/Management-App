package com.example.byadiproject

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.byadiproject.Client.AdapterClient
import com.example.byadiproject.Client.AddClient
import com.example.byadiproject.Client.Client
import com.example.byadiproject.Commande.Commande
import com.example.byadiproject.Commande.CommandeActivity
import com.example.byadiproject.Payments.Payment
import com.example.byadiproject.Payments.PaymentActivity
import com.example.byadiproject.databinding.ActivityMainBinding
import com.example.byadiproject.databinding.AlertDialogCommanderBinding
import com.example.byadiproject.databinding.AlertDialogPaymentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), AdapterClient.Command, AdapterClient.payment_interface,
    AdapterClient.delete_interface {
    private var adapter: AdapterClient? = null

    private val fireBase = Firebase.database
    val list = ArrayList<Client>()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val myAdapter = AdapterClient(this, list, this, this, this)
        binding.searchb.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filteredList = search(newText)
                adapter?.setItems(filteredList as ArrayList<Client>)
                return true
            }
        })


        val database = Firebase.database
        val myRef = database.getReference("Client")
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddClient::class.java))
        }
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (item in snapshot.children) {
                    val value = item.getValue(Client::class.java)
                    list.add(value!!)

                }
                val adapter =
                    AdapterClient(
                        this@MainActivity,
                        list,
                        this@MainActivity,
                        this@MainActivity,
                        this@MainActivity
                    )
                binding.myrecycle.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }

    override fun like(client: Client) {
        val alertDialog = AlertDialog.Builder(this).create()
        val alertDialogbinding = AlertDialogCommanderBinding.inflate(LayoutInflater.from(this))
        alertDialogbinding.alertCommander.setOnClickListener {

            if (alertDialogbinding.count.text!!.isNotBlank()) {
                val currentDate: String =
                    SimpleDateFormat(
                        ("dd.MM.yyyy. HH:mm:ss"),
                        Locale.getDefault()
                    ).format(Date())
                val myTotal = alertDialogbinding.count.text.toString().toInt() * client.price
                val pushCommande = fireBase
                val commande =
                    Commande(
                        alertDialogbinding.count.text.toString().toInt(),
                        myTotal,
                        currentDate
                    )
                pushCommande.getReference("Command").child(client.id).push().setValue(commande)
                    .addOnSuccessListener {
                        val newTotal = client.total + myTotal
                        val updateClient =
                            Client(client.id, client.name, client.price, newTotal, client.tele)
                        fireBase.getReference("Client").child(client.id).setValue(updateClient)
                            .addOnSuccessListener {
                                alertDialog.dismiss()
                                Toast.makeText(this, "Done !!", Toast.LENGTH_SHORT).show()
                            }
                    }
            }
        }



        alertDialogbinding.alertShowCommande.setOnClickListener {
            val intent = Intent(this, CommandeActivity::class.java)
            intent.putExtra("id", client.id)
            this.startActivity(intent)
            alertDialog.dismiss()
        }

        alertDialog.setView(alertDialogbinding.root)
        alertDialog.show()
    }

    override fun payment(client: Client) {
        val alertDialog = AlertDialog.Builder(this).create()
        val alertDialogPayment = AlertDialogPaymentBinding.inflate(LayoutInflater.from(this))

        alertDialogPayment.alertAddPayment.setOnClickListener {
            if (client.total > 0) {
                val pushpayment = fireBase
                val payment = alertDialogPayment.payment.text.toString().toInt()
                val currentDate: String =
                    SimpleDateFormat(("dd.MM.yyyy. HH:mm:ss"), Locale.getDefault()).format(Date())
                val payment_objet = Payment(client.id, payment.toDouble(), currentDate)
                pushpayment.getReference("payment").child(client.id).push().setValue(payment_objet)
                    .addOnSuccessListener {
                        val new = client.total - payment
                        val update = Client(client.id, client.name, client.price, new, client.tele)
                        pushpayment.getReference("Client").child(client.id).setValue(update)
                        Toast.makeText(this, "Done!!", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                    }
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }

        }




        alertDialogPayment.alertShowPayment.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("id", client.id)
            startActivity(intent)
            alertDialog.dismiss()
        }
        alertDialog.setView(alertDialogPayment.root)
        alertDialog.show()


    }

    private fun setUpRecyclerView() {

        adapter = AdapterClient(this@MainActivity, list, this@MainActivity, this@MainActivity, this)

    }

    override fun onStart() {
        super.onStart()
        if (binding.searchb != null) {
            binding.searchb.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    private fun search(string: String) {
        val Deal = ArrayList<Client>()
        for (item in list) {
            if (item.name.toLowerCase().contains(string.toLowerCase()) || item.tele.toLowerCase()
                    .contains(string.toLowerCase()) || item.total.toString().toLowerCase()
                    .contains(string.toLowerCase()) || item.price.toString().toLowerCase()
                    .contains(string.toLowerCase()) || item.total.toString().toLowerCase()
                    .contains(string.toLowerCase())

            ) {
                Deal.add(item)
            }
        }
        val adpter = AdapterClient(this, Deal, this, this, this)
        binding.myrecycle.adapter = adpter
    }

    override fun delete(client: Client) {
        fireBase.getReference("Client").child(client.id).removeValue()
        adapter?.setItems(list)

    }


}
