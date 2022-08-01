package com.khtn.mangashare.booklist.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.picItem

class chapterEditAdapter(var chapters: ArrayList<chapterItem>) :
    RecyclerView.Adapter<chapterEditAdapter.ViewHolder>() {


    inner class ViewHolder(listItemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(listItemView) {
        val number = listItemView.findViewById<TextView>(R.id.numberChapterTV)
        val date = listItemView.findViewById<TextView>(R.id.datePostTV)
        val status =listItemView.findViewById<TextView>(R.id.statusChapter)

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

    fun setOnItemClickListener(listener: chapterEditAdapter.onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_chapter, parent, false)
        return ViewHolder(contactView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chapter = chapters[position]
        holder.number.text="Chương ${chapter.number}"
        holder.date.text="Ngày cập nhật: ${chapter.datePost}"
        holder.status.text=chapter.status

        if(chapter.status=="Duyệt"){
            holder.status.setTextColor(Color.parseColor("#21C878"))

        } else if( chapter.status=="Chưa duyệt"){
            holder.status.setTextColor(Color.parseColor("#EC0000"))

        } else if( chapter.status=="Nháp"){
            holder.status.setTextColor(Color.parseColor("#F49404"))

        }else {
            holder.status.text=""
        }
        //holder.date.text="Ngày cập nhật: ${}"
    }

    override fun getItemCount(): Int {
        return chapters.size
    }


}