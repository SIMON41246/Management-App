package com.example.byadiproject.Payments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.byadiproject.R

class AdapterPayment(val context: Context, val list: ArrayList<Payment>) :
    RecyclerView.Adapter<AdapterPayment.MyviewHolder>() {
    class MyviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val payment = view.findViewById<TextView>(R.id.payment_view)
        val date = view.findViewById<TextView>(R.id.Date_payment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val viewcommande =
            LayoutInflater.from(context).inflate(R.layout.view_payment, parent, false)
        return MyviewHolder(viewcommande)
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.payment.text = "${list[holder.adapterPosition].payment.toString()} DH "
        holder.date.text = "Time Send: ${list[holder.adapterPosition].date}"
    }

    override fun getItemCount(): Int {
        return list.size
    }
}