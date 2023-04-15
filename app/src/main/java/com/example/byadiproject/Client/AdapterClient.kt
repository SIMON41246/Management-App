package com.example.byadiproject.Client

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.byadiproject.R

class AdapterClient(
    private val context: Context,
    private var list: List<Client>,
    private val command: Command,
    private val paymentInterface: payment_interface,
    val delete: delete_interface
) : RecyclerView.Adapter<AdapterClient.MyviewHolder>() {
    private var filteredList: List<Client> = list

    class MyviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name_view)
        val price = view.findViewById<TextView>(R.id.price_view)
        val tele = view.findViewById<TextView>(R.id.tele_view)
        val commander = view.findViewById<TextView>(R.id.commander)
        val payé = view.findViewById<TextView>(R.id.Payé)
        val total = view.findViewById<TextView>(R.id.total)
        val btndelete = view.findViewById<ImageView>(R.id.deletebutton)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_client, parent, false)
        return MyviewHolder(view)
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.name.text = "Nom :\t${list[holder.adapterPosition].name}"
        holder.price.text = "Prix :\t${list[holder.adapterPosition].price} DH"
        holder.tele.text = "Tele :212\t${list[holder.adapterPosition].tele}"
        holder.total.text = "total :\t${list[holder.adapterPosition].total}DH"

        holder.commander.setOnClickListener {
            command.like(list[holder.adapterPosition])
        }
        holder.payé.setOnClickListener {
            paymentInterface.payment(list[holder.adapterPosition])
        }
        holder.btndelete.setOnClickListener {
            delete.delete(list[holder.adapterPosition])
        }


    }

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            list
        } else {
            list.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface Command {
        fun like(client: Client)
    }

    interface payment_interface {
        fun payment(client: Client)
    }

    interface delete_interface {
        fun delete(client: Client)
    }


    // slide the view from its current position to below itself


    fun setItems(newItems: List<Client>) {
        val diffresults = DiffUtil.calculateDiff(ClientDiffUtil(list, newItems))
        list = newItems
        diffresults.dispatchUpdatesTo(this)
    }


}


