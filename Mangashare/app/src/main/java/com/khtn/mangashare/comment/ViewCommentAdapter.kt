package com.khtn.mangashare.comment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.commentItem

class ViewCommentAdapter(var context: Context?, var comments: ArrayList<commentItem>) :
    RecyclerView.Adapter<ViewCommentAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        val name = listItemView.findViewById<TextView>(R.id.nameCommentTV)
        val chapter = listItemView.findViewById<TextView>(R.id.chapterCommentTV)
        val content = listItemView.findViewById<TextView>(R.id.contentCommentTV)
        val time = listItemView.findViewById<TextView>(R.id.timeCommentTV)
        val text = listItemView.findViewById<TextView>(R.id.commentTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_view_comment, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp = comments[position]
        holder.name.setText(tmp.name)
        holder.content.setText(tmp.content)
        holder.time.setText(tmp.time)

        if(tmp.chapter == -1){
            holder.chapter.setText("")
            holder.text.setText("")
        }
        else{
            holder.text.setText("-")
            holder.chapter.setText("Chương ${tmp.chapter}")
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}