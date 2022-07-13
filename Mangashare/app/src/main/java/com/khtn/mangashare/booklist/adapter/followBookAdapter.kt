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
import com.google.android.material.snackbar.Snackbar
import com.khtn.mangashare.R
import com.khtn.mangashare.chapterDetail.ViewChapterDetailActivity
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem
import kotlinx.android.synthetic.main.fragment_add_book_list.*

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
        val comic=initItem()
        comic.lastDateSeen="5:30pm 23/22/2022"
        comic.lastSeenChapter=4
        val Name:String?=comic.name
        holder.name.text=Name
        holder.chapter.text="Chapter ${comic.totalChapter}"
        holder.author.text=comic.author
        holder.img.setImageResource(comic.cover)
        holder.button.setOnClickListener {
            var snackbar = Snackbar.make(root, "Đã bỏ theo dõi ${comic.name}", Snackbar.LENGTH_LONG)


            comics?.removeAt(position)
            notifyItemRemoved(position)
            comics?.let { it1 -> notifyItemRangeChanged(position, it1.size) }

            snackbar.show()
            snackbar.setAction("Theo dõi lại", View.OnClickListener() {
                comics?.add(position,comic)
                notifyItemInserted(position)
                comics?.let { it1 -> notifyItemRangeChanged(position, it1.size) }

            })
        }
    }
    private fun initItem(): comicItem {
        val imageList = arrayListOf<Int>()
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)

        val imageList2 = arrayListOf<Int>()
        imageList2.add(R.drawable.manga_cover)
        imageList2.add(R.drawable.manga_cover)
        imageList2.add(R.drawable.manga_cover)
        imageList2.add(R.drawable.manga_cover)
        imageList2.add(R.drawable.manga_cover)
        imageList2.add(R.drawable.manga_cover)

        val chapterList = arrayListOf<chapterItem>()
        chapterList.add(chapterItem(1, "20/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(2, "21/05/2022", 100, false, 200, imageList2))
        chapterList.add(chapterItem(3, "22/05/2022", 220, false, 200, imageList2))
        chapterList.add(chapterItem(4, "23/05/2022", 0, false, 200, imageList2))
        chapterList.add(chapterItem(5, "24/05/2022", 0, true, 200, imageList2))
        chapterList.add(chapterItem(6, "25/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(7, "26/05/2022", 100, false, 200, imageList))
        chapterList.add(chapterItem(8, "27/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(9, "21/05/2022", 100, false, 200, imageList2))
        chapterList.add(chapterItem(10, "22/05/2022", 220, false, 200, imageList))
        chapterList.add(chapterItem(11, "23/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(12, "24/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(13, "25/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(14, "26/05/2022", 100, false, 200, imageList))
        chapterList.add(chapterItem(15, "10/07/2022", 0, false, 200, imageList))

        val category = arrayListOf<String>()
        category.add("Phiêu lưu")
        category.add("Hành động")
        category.add("Hài hước")
        category.add("Phiêu lưu")
        val des =
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
        val comic =
            comicItem(
                "Naruto",
                R.drawable.cover_manga,
                "Aoyama Gosho",
                100,
                23,
                15,
                56,
                34,
                des,
                false,
                category, chapterList
            )
        return comic
    }
}