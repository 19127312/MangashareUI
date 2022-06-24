package com.khtn.mangashare.booklist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.comicItem

class historyBookListAdapter( var comics:ArrayList<comicItem>?) :
    RecyclerView.Adapter<historyBookListAdapter.HolderVideo>(){

    lateinit var ViewGroup: ViewGroup


    class HolderVideo(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){


        var img: ImageView =itemView.findViewById(R.id.coverHistory)
        var name: TextView =itemView.findViewById(R.id.nameMangaHistory)
        var chapter: TextView =itemView.findViewById(R.id.chapterHistory)
        var date: TextView =itemView.findViewById(R.id.dateHistory)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
    private lateinit var mListenr: onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int){

        }
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListenr=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): historyBookListAdapter.HolderVideo {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_history_book,parent,false)
        ViewGroup=parent
        return historyBookListAdapter.HolderVideo(view,mListenr)
    }


    override fun getItemCount(): Int {
        return comics!!.size
    }

    override fun onBindViewHolder(holder: HolderVideo, position: Int) {
        val comic=comics!![position]
        val Name:String?=comic.name

        holder.name.text=Name
        holder.chapter.text="Chapter ${comic.lastSeenChapter}/${comic.totalChapter}"
        holder.date.text=comic.lastDateSeen
        holder.img.setImageResource(comic.cover)
    }
}