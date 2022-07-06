package com.khtn.mangashare.chapterDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.SuggestCategoryAdapter
import com.khtn.mangashare.model.comicItem


class ImageChapterDetailAdapter(var context: Context?, var image: ArrayList<Int>) :
    RecyclerView.Adapter<ImageChapterDetailAdapter.ViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null
    inner class ViewHolder(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        val img = listItemView.findViewById<ImageView>(R.id.itemChapterDetailIM)
        init {
            listItemView.setOnClickListener { onItemClick?.invoke(image[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_chapter_detail, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp = image[position]
        holder.img.setImageResource(tmp)
    }

    override fun getItemCount(): Int {
        return image.size
    }

}