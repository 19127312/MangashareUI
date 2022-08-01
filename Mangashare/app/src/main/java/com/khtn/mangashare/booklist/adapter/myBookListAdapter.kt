package com.khtn.mangashare.booklist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.comicItem

class myBookListAdapter( var comics:ArrayList<comicItem>?) :
    RecyclerView.Adapter<myBookListAdapter.HolderVideo>(){

    lateinit var ViewGroup: ViewGroup


    class HolderVideo(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){


        var img: ImageView =itemView.findViewById(R.id.myBookCoverImg)
        var name: TextView =itemView.findViewById(R.id.myBookNameTV)
        var chapter: TextView =itemView.findViewById(R.id.myBookChapterTV)
        var status: ImageView =itemView.findViewById(R.id.statusImage)

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myBookListAdapter.HolderVideo {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_my_book,parent,false)
        ViewGroup=parent
        return myBookListAdapter.HolderVideo(view,mListenr)
    }


    override fun getItemCount(): Int {
        return comics!!.size
    }

    override fun onBindViewHolder(holder: HolderVideo, position: Int) {
        val comic=comics!![position]
        val Name:String?=comic.name

        holder.name.text=Name
        if(comic.totalChapter!=0){
            holder.chapter.text="Chapter ${comic.totalChapter}"
        }else{
            holder.chapter.text="Người báo cáo:  ${comic.reporter}"
        }
        holder.img.setImageResource(comic.cover)
        if(comic.status=="Censored"){
            holder.status.setImageResource(R.drawable.censored)
        }else if(comic.status=="Uncensored"){
            holder.status.setImageResource(R.drawable.wait_to_censor)
        }else if(comic.status=="Đã"){
            holder.status.setImageResource(R.drawable.xuli)
        }else if(comic.status=="Chưa"){
            holder.status.setImageResource(R.drawable.chuaxuly)
        }else if(comic.status=="Temp"){
            holder.status.setImageResource(R.drawable.tempcomic)

        }
    }
}