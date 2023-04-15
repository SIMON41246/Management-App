package com.example.byadiproject.Client

import androidx.recyclerview.widget.DiffUtil

class ClientDiffUtil(var oldlist: List<Client>, var newlist: List<Client>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldlist.size

    override fun getNewListSize(): Int = newlist.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldlist[oldItemPosition].name == newlist[newItemPosition].name && oldlist[oldItemPosition].tele == newlist[newItemPosition].tele)

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}