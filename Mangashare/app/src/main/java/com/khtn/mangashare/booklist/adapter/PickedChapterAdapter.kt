package com.khtn.mangashare.booklist.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.picItem


class PickedChapterAdapter(var context: Activity, var imgs: ArrayList<picItem>,var type:String) :
    RecyclerView.Adapter<PickedChapterAdapter.ViewHolder>() {


    inner class ViewHolder(listItemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(listItemView) {
        val image=  listItemView.findViewById<ImageView>(R.id.chosenPic)
        val number = listItemView.findViewById<TextView>(R.id.numberChapterPic)
        val checkStatus= listItemView.findViewById<ImageView>(R.id.statusCheckPic)

        init {
           itemView.setOnClickListener {
               listener.onItemClick(adapterPosition)
           }
           itemView.setOnLongClickListener {
               listener.onLongItemClick(adapterPosition)
               return@setOnLongClickListener true
           }

        }
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int) {

        }

        fun onLongItemClick(position: Int){

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_chapter_pic, parent, false)
        return ViewHolder(contactView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectImage = imgs[position]

        if(selectImage.imgURI==null){
            holder.image.setImageResource(selectImage.imgResource)
        }else{
            holder.image.setImageURI(selectImage.imgURI)
        }
        if(type=="chapter"){
            holder.number.text="???nh ${position+1}"
        }else{
            if(position==0){
                holder.number.text=""
            }else{
                holder.number.text="???nh ${position}"

            }
        }
        if(type=="chapter"){

        }
        if(selectImage.check){
            if(type!="report" || position!=0 ){
                holder.image.background= ContextCompat.getDrawable(context, R.drawable.outline_checked)
                holder.checkStatus.visibility=View.VISIBLE
            }

        }else{
            holder.image.background= ContextCompat.getDrawable(context, R.drawable.outline_pic)
            holder.checkStatus.visibility=View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return imgs.size
    }

    fun OnActivityResult(data: Intent?, posChange: Int) {
        if (data != null) {
            imgs[posChange].imgURI = data.data!!
            notifyItemChanged(posChange)
        }
    }
    fun SetChange(){
        notifyDataSetChanged()
    }
}