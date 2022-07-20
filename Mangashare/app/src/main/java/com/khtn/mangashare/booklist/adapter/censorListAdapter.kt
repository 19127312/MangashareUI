package com.khtn.mangashare.booklist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.khtn.mangashare.R
import com.khtn.mangashare.model.comicItem

class censorListAdapter(var context: Context?, var comics:ArrayList<comicItem>?) :
    RecyclerView.Adapter<censorListAdapter.HolderVideo>(){

    lateinit var ViewGroup: ViewGroup


    class HolderVideo(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){


        var img: ImageView =itemView.findViewById(R.id.coverCensor)
        var name: TextView =itemView.findViewById(R.id.nameMangaCensor)
        var date: TextView =itemView.findViewById(R.id.dateCensor)
        var author: TextView =itemView.findViewById(R.id.authorCensor)

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): censorListAdapter.HolderVideo {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_censor,parent,false)
        ViewGroup=parent
        return censorListAdapter.HolderVideo(view,mListenr)
    }


    override fun getItemCount(): Int {
        return comics!!.size
    }

    override fun onBindViewHolder(holder: HolderVideo, position: Int) {
        val comic= comics?.get(position)


        val Name:String?= comic?.name
        holder.name.text=Name
        if (comic != null) {
            holder.date.text=comic.lastDateSeen
        }
        if (comic != null) {
            holder.author.text=comic.author
        }
        if (comic != null) {
            holder.img.setImageResource(comic.cover)
        }

    }
    }