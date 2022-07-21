package com.khtn.mangashare.adapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.activity.ViewBookListActivity
import com.khtn.mangashare.home.adapter.IOnRecyclerViewItemTouchListener
import com.khtn.mangashare.model.comicItem

class RankingComicAdapter(var context: Context?, var comics: ArrayList<comicItem>) :
    RecyclerView.Adapter<RankingComicAdapter.ViewHolder>() {
    var onItemClick: ((comicItem) -> Unit)? = null
    inner class ViewHolder(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        val icon = listItemView.findViewById<ImageView>(R.id.rankingIV)
        val comicImage = listItemView.findViewById<ImageView>(R.id.rankingComicIV)
        val comicName = listItemView.findViewById<TextView>(R.id.rankingComicNameTV)
        val status = listItemView.findViewById<ImageView>(R.id.rankingStatusIV)
        val viewNumber = listItemView.findViewById<TextView>(R.id.rankingViewTV)
        val likeNumber = listItemView.findViewById<TextView>(R.id.rankingLikeTV)
        val chapterNumber = listItemView.findViewById<TextView>(R.id.rankingChapterTV)
        val description = listItemView.findViewById<TextView>(R.id.rankingDescriptionTV)
        val rc = listItemView.findViewById<RecyclerView>(R.id.rankingCategoryRC)
        init {
            listItemView.setOnClickListener { onItemClick?.invoke(comics[adapterPosition])}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_ranking_comic, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp = comics[position]
        when (position) {
            0 -> holder.icon.setImageResource(R.drawable.ic_top1)
            1 -> holder.icon.setImageResource(R.drawable.ic_top2)
            2 -> holder.icon.setImageResource(R.drawable.ic_top3)
        }
        when (tmp.completeStatus) {
            true -> holder.status.setImageResource(R.drawable.complete_comic)
            false -> holder.status.setImageResource(R.drawable.uncomplete_comic)
        }
        holder.comicImage.setImageResource(tmp.cover)
        holder.comicName.setText(tmp.name)
        holder.chapterNumber.setText("${tmp.totalChapter} Chương")
        tmp.viewNumber?.let { holder.viewNumber.setText(it.toString()) }
        tmp.likeNumber?.let { holder.likeNumber.setText(it.toString()) }
        holder.description.setText(tmp.description)
        val adapter = SuggestCategoryAdapter(context,tmp.category)
        holder.rc.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.rc.adapter = adapter
        adapter.onButtonClick = { tmp ->
            val intent = Intent(context, ViewBookListActivity::class.java)
            intent.putExtra("title", tmp)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if (comics.size > 3) return 3 else return comics.size
    }

}