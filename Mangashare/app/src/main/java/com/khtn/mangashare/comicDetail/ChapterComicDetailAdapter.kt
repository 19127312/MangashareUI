package com.khtn.mangashare.comicDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.khtn.mangashare.databinding.ItemFreeChapterListBinding
import com.khtn.mangashare.databinding.ItemVipChapterListBinding
import com.khtn.mangashare.R
import com.khtn.mangashare.model.chapterItem

sealed class ChapterRecyclerViewItem {

    class FreeChapter(
        var chapter: Int?,
        var datePost: String?,
        var viewNumber: Int?,
        var status: Boolean
    ) : ChapterRecyclerViewItem()

    class VipChapter(
        var chapter: Int?,
        var datePost: String?,
        var price: Int?,
        var viewNumber: Int?,
        var status: Boolean
    ) : ChapterRecyclerViewItem()

    companion object {
        fun parseData(input: chapterItem): ChapterRecyclerViewItem {
            if (input.price == 0)
                return FreeChapter(
                    chapter = input.number,
                    datePost = input.datePost,
                    viewNumber = input.viewNumber,
                    status = input.bookmark
                )
            return VipChapter(
                chapter = input.number,
                datePost = input.datePost,
                price = input.price,
                viewNumber = input.viewNumber,
                status = input.bookmark
            )
        }
    }
}

sealed class ChapterRecyclerViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var itemClickListener: ((view: View, item: ChapterRecyclerViewItem, position: Int) -> Unit)? =
        null
    var onButtonClick: ((view: View, item: ChapterRecyclerViewItem, position: Int) -> Unit)? = null

    class VipViewHolder(
        private val binding: ItemVipChapterListBinding,
        val context: Context
    ) :
        ChapterRecyclerViewHolder(binding) {
        fun bind(vip: ChapterRecyclerViewItem.VipChapter) {
            binding.dateUpdateVipChapter.setText(vip.datePost)
            binding.numberVipChapterTV.setText("Chương ${vip.chapter}")
            vip.price?.let { binding.priceVipChapterTV.setText(it.toString()) }
            vip.viewNumber?.let { binding.viewNumberVipChapter.setText(it.toString()) }
            when (vip.status) {
                true -> binding.markVipChapterIM.setImageResource(R.drawable.ic_unbook_mark)
                false -> binding.markVipChapterIM.setImageResource(R.drawable.ic_book_mark)
            }
            binding.markVipChapterIM.setOnClickListener {
                onButtonClick?.invoke(it, vip, adapterPosition)
            }
            binding.root.setOnClickListener {
                itemClickListener?.invoke(it, vip, adapterPosition)
            }
        }
    }

    class FreeViewHolder(
        private val binding: ItemFreeChapterListBinding,
        val context: Context
    ) :
        ChapterRecyclerViewHolder(binding) {
        fun bind(free: ChapterRecyclerViewItem.FreeChapter) {
            binding.dateUpdateFreeChapter.setText(free.datePost)
            binding.numberFreeChapterTV.setText("Chương ${free.chapter}")
            free.viewNumber?.let { binding.viewNumberFreeChapter.setText(it.toString()) }
            when (free.status) {
                true -> binding.markFreeChapterIM.setImageResource(R.drawable.ic_unbook_mark)
                false -> binding.markFreeChapterIM.setImageResource(R.drawable.ic_book_mark)
            }

            binding.markFreeChapterIM.setOnClickListener {
                onButtonClick?.invoke(it, free, adapterPosition)
            }
            binding.root.setOnClickListener {
                itemClickListener?.invoke(it, free, adapterPosition)
            }
        }
    }
}

class ChapterRecyclerViewAdapter(var context: Context) :
    RecyclerView.Adapter<ChapterRecyclerViewHolder>() {

    var items = mutableListOf<ChapterRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: ((view: View, item: ChapterRecyclerViewItem, position: Int) -> Unit)? =
        null
    var onButtonClick: ((view: View, item: ChapterRecyclerViewItem, position: Int) -> Unit)? =
        null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChapterRecyclerViewHolder {
        return when (viewType) {
            R.layout.item_vip_chapter_list -> ChapterRecyclerViewHolder.VipViewHolder(
                ItemVipChapterListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), context
            )
            R.layout.item_free_chapter_list -> ChapterRecyclerViewHolder.FreeViewHolder(
                ItemFreeChapterListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), context
            )

            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: ChapterRecyclerViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener

        when (holder) {
            is ChapterRecyclerViewHolder.VipViewHolder -> {
                holder.bind(items[position] as ChapterRecyclerViewItem.VipChapter)
                holder.onButtonClick = onButtonClick
            }
            is ChapterRecyclerViewHolder.FreeViewHolder -> {
                holder.bind(items[position] as ChapterRecyclerViewItem.FreeChapter)
                holder.onButtonClick = onButtonClick
            }
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ChapterRecyclerViewItem.VipChapter -> R.layout.item_vip_chapter_list
            is ChapterRecyclerViewItem.FreeChapter -> R.layout.item_free_chapter_list
        }
    }

    fun updateList(inputList: List<chapterItem>) {
        items.clear()
        for (input in inputList) {
            items.add(ChapterRecyclerViewItem.parseData(input))
        }
        notifyDataSetChanged()
    }
}