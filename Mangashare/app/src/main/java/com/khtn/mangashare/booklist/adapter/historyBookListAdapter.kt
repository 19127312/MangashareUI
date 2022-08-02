package com.khtn.mangashare.booklist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.chapterDetail.ViewChapterDetailActivity
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem
import com.khtn.mangashare.model.commentItem

class historyBookListAdapter( var context: Context?, var comics:ArrayList<comicItem>?) :
    RecyclerView.Adapter<historyBookListAdapter.HolderVideo>(){

    lateinit var ViewGroup: ViewGroup


    class HolderVideo(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){


        var img: ImageView =itemView.findViewById(R.id.coverHistory)
        var name: TextView =itemView.findViewById(R.id.nameMangaHistory)
        var chapter: TextView =itemView.findViewById(R.id.chapterHistory)
        var date: TextView =itemView.findViewById(R.id.dateHistory)
        var button :ImageView= itemView.findViewById(R.id.buttonContinueReading)

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
        val comic= comics?.get(position)
        comic?.lastDateSeen="5:30pm 23/22/2022"
        comic?.lastSeenChapter=4
        val Name:String?=comic?.name
        holder.name.text=Name
        holder.chapter.text="Chapter ${comic?.lastSeenChapter?.plus(1)}/${comic?.totalChapter}"
        holder.date.text=comic?.lastDateSeen
        comic?.cover?.let { holder.img.setImageResource(it) }
        holder.button.setOnClickListener {
            val intent = Intent(context, ViewChapterDetailActivity::class.java)
            intent.putExtra("comic", comic)
            var index = comic?.lastSeenChapter
            intent.putExtra("chapterNumber", index)
            context?.startActivity(intent);
        }
    }

}