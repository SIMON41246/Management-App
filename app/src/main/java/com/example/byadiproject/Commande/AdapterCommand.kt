package com.example.byadiproject.Commande

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.byadiproject.R

class AdapterCommand(private val context: Context, private val list: ArrayList<Commande>) :
    RecyclerView.Adapter<AdapterCommand.MyviewHolder>() {
    class MyviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val count = view.findViewById<TextView>(R.id.count_commande)
        val total = view.findViewById<TextView>(R.id.total_commande)
        val date=view.findViewById<TextView>(R.id.Date_commande)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_commande, parent, false)
        return MyviewHolder((view))
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.count.text = list[holder.adapterPosition].count.toString()
        holder.total.text = "Prix: ${list[holder.adapterPosition].total.toString()}DH"
        holder.date.text=list[holder.adapterPosition].date
    }

    override fun getItemCount(): Int {
        return list.size
    }
}