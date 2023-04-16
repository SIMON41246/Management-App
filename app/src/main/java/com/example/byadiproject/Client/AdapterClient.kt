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
import com.example.byadiproject.databinding.ItemBinding

class AdapterClient(
    private val context: Context,
    private var list: List<Client>,
    private val command: Command,
    private val paymentInterface: payment_interface,
    val delete: delete_interface
) : RecyclerView.Adapter<AdapterClient.BaseViewHolder>() {
    private var filteredList: List<Client> = list

    class MyviewHolder(view: View) : BaseViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name_view)
        val price = view.findViewById<TextView>(R.id.price_view)
        val tele = view.findViewById<TextView>(R.id.tele_view)
        val commander = view.findViewById<TextView>(R.id.commander)
        val payé = view.findViewById<TextView>(R.id.Payé)
        val total = view.findViewById<TextView>(R.id.total)
        val btndelete = view.findViewById<ImageView>(R.id.deletebutton)

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return VIEW_TYPE_HEADER
        } else {
            return VIEW_TYPE_CLIENT
        }
    }

    companion object {
        const val VIEW_TYPE_HEADER = 11
        const val VIEW_TYPE_CLIENT = 12
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class newViewHolder(view: View) : BaseViewHolder(view) {
        val binding = ItemBinding.bind(view)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
                return newViewHolder(view)
            }
            VIEW_TYPE_CLIENT -> {
                val view = LayoutInflater.from(context).inflate(R.layout.view_client, parent, false)
                return MyviewHolder(view)

            }
        }
        return super.createViewHolder(parent, viewType)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is MyviewHolder -> {
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
            is newViewHolder -> {

            }
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


