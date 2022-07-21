package com.khtn.mangashare.booklist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.SuggestCategoryAdapter
import com.khtn.mangashare.booklist.activity.ViewBookListActivity
import com.khtn.mangashare.model.comicItem


class BookListAdapter(var context: Context?, var comics: ArrayList<comicItem>) :
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
    var onItemClick: ((comicItem) -> Unit)? = null

    inner class ViewHolder(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        val comicImage = listItemView.findViewById<ImageView>(R.id.itemComicIV)
        val comicName = listItemView.findViewById<TextView>(R.id.itemComicNameTV)
        val status = listItemView.findViewById<ImageView>(R.id.itemComicStatusIV)
        val viewNumber = listItemView.findViewById<TextView>(R.id.itemComicViewTV)
        val likeNumber = listItemView.findViewById<TextView>(R.id.itemComicLikeTV)
        val chapterNumber = listItemView.findViewById<TextView>(R.id.itemComicChapterTV)
        val description = listItemView.findViewById<TextView>(R.id.itemComicDescriptionTV)
        val rc = listItemView.findViewById<RecyclerView>(R.id.itemComicCategoryRC)

        init {
            listItemView.setOnClickListener { onItemClick?.invoke(comics[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_comic_list, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp = comics[position]

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
        val adapter = SuggestCategoryAdapter(context, tmp.category)
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
        return comics.size
    }

}