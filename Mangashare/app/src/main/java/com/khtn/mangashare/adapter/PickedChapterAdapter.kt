package com.khtn.mangashare.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.picItem


class PickedChapterAdapter(var context: Activity, var imgs : ArrayList<picItem>) :
    RecyclerView.Adapter<PickedChapterAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(listItemView) {
        val image=  listItemView.findViewById<ImageView>(R.id.chosenPic)
        val number = listItemView.findViewById<TextView>(R.id.numberChapterPic)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
    private lateinit var mListener: onItemClickListener

    interface  onItemClickListener{
        fun onItemClick(position: Int){

        }
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_chapter_pic, parent, false)
        return ViewHolder(contactView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectImage  = imgs[position]
        holder.image.setImageURI(selectImage.imgURI)
        holder.number.text="Ảnh ${position+1}"

    }

    override fun getItemCount(): Int {
        return imgs.size
    }
    fun OnActivityResult(data: Intent?,posChange:Int){
        if (data != null) {
            imgs[posChange].imgURI=data.data!!
            notifyItemChanged(posChange)
        }
    }

}