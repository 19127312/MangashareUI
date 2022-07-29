package com.khtn.mangashare.comicDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.ratingItem

class RatingListAdapter(var context: Context?, var comments: ArrayList<ratingItem>) :
    RecyclerView.Adapter<RatingListAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        val name = listItemView.findViewById<TextView>(R.id.nameRatingTV)
        val content = listItemView.findViewById<TextView>(R.id.contentRatingTV)
        val time = listItemView.findViewById<TextView>(R.id.timeRatingTV)
        val starOne = listItemView.findViewById<ImageView>(R.id.starRatingOneItemIM)
        val starTwo = listItemView.findViewById<ImageView>(R.id.starRatingTwoItemIM)
        val starThree = listItemView.findViewById<ImageView>(R.id.starRatingThreeItemIM)
        val starFour = listItemView.findViewById<ImageView>(R.id.starRatingFourItemIM)
        val starFive = listItemView.findViewById<ImageView>(R.id.starRatingFiveItemIM)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_rating, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tmp = comments[position]
        holder.name.setText(tmp.name + " - ")
        holder.content.setText(tmp.content)
        holder.time.setText(tmp.time)
        when (tmp.star) {
            0 -> {
                holder.starOne.setImageResource(R.drawable.ic_star_gray)
                holder.starTwo.setImageResource(R.drawable.ic_star_gray)
                holder.starThree.setImageResource(R.drawable.ic_star_gray)
                holder.starFour.setImageResource(R.drawable.ic_star_gray)
                holder.starFive.setImageResource(R.drawable.ic_star_gray)
            }
            1 -> {
                holder.starOne.setImageResource(R.drawable.ic_star)
                holder.starTwo.setImageResource(R.drawable.ic_star_gray)
                holder.starThree.setImageResource(R.drawable.ic_star_gray)
                holder.starFour.setImageResource(R.drawable.ic_star_gray)
                holder.starFive.setImageResource(R.drawable.ic_star_gray)
            }
            2 -> {
                holder.starOne.setImageResource(R.drawable.ic_star)
                holder.starTwo.setImageResource(R.drawable.ic_star)
                holder.starThree.setImageResource(R.drawable.ic_star_gray)
                holder.starFour.setImageResource(R.drawable.ic_star_gray)
                holder.starFive.setImageResource(R.drawable.ic_star_gray)
            }
            3 -> {
                holder.starOne.setImageResource(R.drawable.ic_star)
                holder.starTwo.setImageResource(R.drawable.ic_star)
                holder.starThree.setImageResource(R.drawable.ic_star)
                holder.starFour.setImageResource(R.drawable.ic_star_gray)
                holder.starFive.setImageResource(R.drawable.ic_star_gray)
            }
            4 -> {
                holder.starOne.setImageResource(R.drawable.ic_star)
                holder.starTwo.setImageResource(R.drawable.ic_star)
                holder.starThree.setImageResource(R.drawable.ic_star)
                holder.starFour.setImageResource(R.drawable.ic_star)
                holder.starFive.setImageResource(R.drawable.ic_star_gray)
            }
            5 -> {
                holder.starOne.setImageResource(R.drawable.ic_star)
                holder.starTwo.setImageResource(R.drawable.ic_star)
                holder.starThree.setImageResource(R.drawable.ic_star)
                holder.starFour.setImageResource(R.drawable.ic_star)
                holder.starFive.setImageResource(R.drawable.ic_star)
            }
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}