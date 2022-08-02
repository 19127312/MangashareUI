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
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem
import com.khtn.mangashare.model.commentItem

class followBookAdapter(var root: View,var context: Context?, var comics:ArrayList<comicItem>?) :
    RecyclerView.Adapter<followBookAdapter.HolderVideo>(){

    lateinit var ViewGroup: ViewGroup


    class HolderVideo(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){


        var img: ImageView =itemView.findViewById(R.id.coverFollow)
        var name: TextView =itemView.findViewById(R.id.nameMangaFollow)
        var chapter: TextView =itemView.findViewById(R.id.chapterFollow)
        var author: TextView =itemView.findViewById(R.id.authorFollow)
        var button : ImageView = itemView.findViewById(R.id.cancelFollowBtn)

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): followBookAdapter.HolderVideo {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_follow_comic,parent,false)
        ViewGroup=parent
        return followBookAdapter.HolderVideo(view,mListenr)
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
        holder.chapter.text="Chapter ${comic?.totalChapter}"
        holder.author.text=comic?.author
        comic?.let { holder.img.setImageResource(it?.cover) }
        holder.button.setOnClickListener {
            var snackbar = Snackbar.make(root, "Đã bỏ theo dõi ${comic?.name}", Snackbar.LENGTH_LONG)


            comics?.removeAt(position)
            notifyItemRemoved(position)
            comics?.let { it1 -> notifyItemRangeChanged(position, it1.size) }

            snackbar.show()
            snackbar.setAction("Theo dõi lại", View.OnClickListener() {
                if (comic != null) {
                    comics?.add(position,comic)
                }
                notifyItemInserted(position)
                comics?.let { it1 -> notifyItemRangeChanged(position, it1.size) }

            })
        }
    }
}