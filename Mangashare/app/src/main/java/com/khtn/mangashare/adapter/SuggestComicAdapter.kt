package com.khtn.mangashare.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.comicItem


class SuggestComicAdapter(var context: Context?, var comics: ArrayList<comicItem>) :
    RecyclerView.Adapter<SuggestComicAdapter.ViewHolder>() {
    var onItemClick: ((comicItem) -> Unit)? = null

    inner class ViewHolder(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        val comicImage = listItemView.findViewById<ImageView>(R.id.suggestComicIM)
        val comicName = listItemView.findViewById<TextView>(R.id.suggestComicNameTV)
        val comicChapter = listItemView.findViewById<TextView>(R.id.suggestComicChapterTV)

        init {
            listItemView.setOnClickListener { onItemClick?.invoke(comics[adapterPosition % comics.size]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_suggest_comic, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = position % comics.size
        val comic = comics[pos]
        holder.comicImage.setImageResource(comic.cover)
        holder.comicName.setText(comic.name)
        holder.comicChapter.setText("Chap ${comic.totalChapter}")
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

}