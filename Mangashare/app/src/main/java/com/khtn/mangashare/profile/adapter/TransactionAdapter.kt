package com.khtn.mangashare.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.picItem
import com.khtn.mangashare.model.transactionItem

class TransactionAdapter (var items: ArrayList<transactionItem>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {


    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val priceConvert=  listItemView.findViewById<Button>(R.id.convertPrice)
        val price = listItemView.findViewById<TextView>(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(contactView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.price.text= item.price.toString()
        holder.priceConvert.setText(item.priceConvert)
    }
    override fun getItemCount(): Int {
        return items.size
    }

}