package com.khtn.mangashare.booklist.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.picItem

class ReportImageAdapter (var imgs: ArrayList<picItem>) :
    RecyclerView.Adapter<ReportImageAdapter.ViewHolder>() {


    inner class ViewHolder(listItemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(listItemView) {
        val image=  listItemView.findViewById<ImageView>(R.id.chosenPicReport)
        val number = listItemView.findViewById<TextView>(R.id.numberChapterPicReport)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int) {

        }

    }
    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_image_report, parent, false)
        return ViewHolder(contactView, mListener)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectImage = imgs[position]


        holder.image.setImageResource(selectImage.imgResource)

        holder.number.text="Ảnh ${position+1}"

    }
    override fun getItemCount(): Int {
        return imgs.size
    }



}