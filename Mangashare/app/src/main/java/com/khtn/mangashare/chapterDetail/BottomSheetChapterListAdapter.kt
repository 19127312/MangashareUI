package com.khtn.mangashare.chapterDetail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.chapterItem

class BottomSheetChapterListAdapter(
    var context: Context?,
    var chapterList: ArrayList<chapterItem>,
    var number: Int
) :
    RecyclerView.Adapter<BottomSheetChapterListAdapter.ViewHolder>() {
    var onItemClick: ((chapterItem) -> Unit)? = null

    inner class ViewHolder(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        val img = listItemView.findViewById<ImageView>(R.id.bottomSheetIConIM)
        val numberChapter = listItemView.findViewById<TextView>(R.id.bottomSheetChapterNumberTV)
        val price = listItemView.findViewById<TextView>(R.id.bottomSheetPriceTV)

        init {
            listItemView.setOnClickListener { onItemClick?.invoke(chapterList[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_bottom_sheet_chapter_list, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp = chapterList[position]
        holder.numberChapter.setText("Chương ${tmp.number}")

        if (tmp.number!! <= number + 1) {
            holder.numberChapter.setTextColor(ContextCompat.getColor(context!!, R.color.transparent_black))
        }

        if (tmp.price!! > 0) {
            holder.price.setText(tmp.price.toString())
        } else {
            holder.price.setText("")
            holder.img.setVisibility(View.INVISIBLE)
        }
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}