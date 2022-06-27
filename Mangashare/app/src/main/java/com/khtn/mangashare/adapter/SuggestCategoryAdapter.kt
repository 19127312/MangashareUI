package com.khtn.mangashare.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import java.util.*
import kotlin.collections.ArrayList

class SuggestCategoryAdapter(var context: Context?, var categories: ArrayList<String>) :
    RecyclerView.Adapter<SuggestCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        val CategoryBtn = listItemView.findViewById<Button>(R.id.categoryBTN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_category_suggest_home, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cate = categories[position]
        holder.CategoryBtn.setText(cate)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

}