package com.khtn.mangashare.categoryList

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.categoryItem


class CategoryListAdapter(var context: Context?, var categories: ArrayList<categoryItem>) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    var onButtonClick: ((categoryItem) -> Unit)? = null

    inner class ViewHolder(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        val btn = listItemView.findViewById<Button>(R.id.itemCategoryBTN)

        init {
            btn.setOnClickListener{
                onButtonClick?.invoke(categories[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_category, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp = categories[position]
        holder.btn.setText(tmp.name)
        context?.let { ContextCompat.getColor(it, tmp.color) }
            ?.let { holder.btn.background.setTint(it) }
        holder.btn.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,tmp.imgResource,0)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

}