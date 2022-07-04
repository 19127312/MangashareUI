package com.khtn.mangashare.comicDetail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.SuggestComicAdapter
import com.khtn.mangashare.model.comicItem
import com.ms.square.android.expandabletextview.ExpandableTextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ViewPagerComicDetailAdapter : FragmentStateAdapter {
    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        comic: comicItem
    ) : super(fragmentManager, lifecycle) {
        this.comic = comic
    }

    constructor(
        fragment: Fragment,
        comic: comicItem
    ) : super(fragment) {
        this.comic = comic
    }


    private var comic: comicItem
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailComicFragment(comic)
            else -> ChapterListFragment(comic)
        }
    }
}

class ChapterListFragment(private var comic: comicItem) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_comic_chapter_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View?) {
        var latestChapter = view?.findViewById<TextView>(R.id.lastestChapterListTV)
        var price = view?.findViewById<TextView>(R.id.priceChapterListTV)
        var sort = view?.findViewById<TextView>(R.id.sortChapterTV)
        var numberChapter = view?.findViewById<TextView>(R.id.numberChapterChapterListTV)
        latestChapter?.setText("Mới nhất: Chương ${comic.chapter.size}")
        var priceTmp: Int = 0
        var numberChapterTmp: Int = 0
        comic.chapter.forEach { i ->
            if (i.price!! > 0) {
                numberChapterTmp++
                priceTmp += i.price!!
            }
        }
        price?.setText(priceTmp.toString())
        numberChapter?.setText("${numberChapterTmp} Chương")

        //RecycleView
        val items = arrayListOf<ChapterRecyclerViewItem>()
        comic.chapter.forEach { i ->
            items.add(ChapterRecyclerViewItem.parseData(i))
        }
        val recycleView = view?.findViewById<RecyclerView>(R.id.chapterListDetailRC)
        recycleView?.layoutManager = LinearLayoutManager(activity)
        val adapter = context?.let { ChapterRecyclerViewAdapter(it) }!!
        adapter.items = items
        recycleView?.adapter = adapter

        adapter.itemClickListener = { view, item, position ->
            val messenger = when (item) {
                is ChapterRecyclerViewItem.VipChapter -> "Vip ${item.chapter} click"
                is ChapterRecyclerViewItem.FreeChapter -> "Free ${item.chapter} click"
            }
            Toast.makeText(context, messenger, Toast.LENGTH_SHORT).show()
        }

        adapter.onButtonClick = { view, item, position ->
            when (item) {
                is ChapterRecyclerViewItem.VipChapter -> {
                    if (item.status == true) {
                        item.status = false
                    } else {
                        items.forEach { i ->
                            when (i) {
                                is ChapterRecyclerViewItem.VipChapter -> i.status = false
                                is ChapterRecyclerViewItem.FreeChapter -> i.status = false
                            }
                        }
                        item.status = true
                    }
                }
                is ChapterRecyclerViewItem.FreeChapter -> {
                    if (item.status == true) {
                        item.status = false
                    } else {
                        items.forEach { i ->
                            when (i) {
                                is ChapterRecyclerViewItem.VipChapter -> i.status = false
                                is ChapterRecyclerViewItem.FreeChapter -> i.status = false
                            }
                        }
                        item.status = true
                    }

                }
            }
            adapter.notifyDataSetChanged()
        }
        sort?.setOnClickListener {
            if (sort.text == "Cũ nhất") {
                sort.setText("Mới nhất")
            } else {
                sort.setText("Cũ nhất")
            }
            items.reverse()
            adapter.notifyDataSetChanged()
        }
    }
}

class DetailComicFragment(private var comic: comicItem) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_comic_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        init(view)
    }

    private var recyclerView: RecyclerView? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(view: View?) {
        var reviewNumber = view?.findViewById<TextView>(R.id.reviewNumberTV)
        var viewNumber = view?.findViewById<TextView>(R.id.viewNumberTV)
        var likeNumber = view?.findViewById<TextView>(R.id.likeNumberTV)
        var followNumber = view?.findViewById<TextView>(R.id.followNumberTV)
        var description = view?.findViewById<ExpandableTextView>(R.id.expandTV)
        var updateTime = view?.findViewById<TextView>(R.id.updateDetailComicTV)

        comic.reviewNumber?.toString().let { reviewNumber?.setText(it) }
        comic.viewNumber?.toString().let { viewNumber?.setText(it) }
        comic.likeNumber?.toString().let { likeNumber?.setText(it) }
        comic.followNumber?.toString().let { followNumber?.setText(it) }
        description?.setText(comic.description)
        if (comic.chapter.size > 0) {

            val new = LocalDate.parse(
                comic.chapter[0].datePost,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
            val old = LocalDate.parse(
                comic.chapter[comic.chapter.size - 1].datePost,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
            val tmp: LocalDate
            if (old > new) {
                tmp = old
            } else {
                tmp = new
            }
            val day = tmp.dayOfWeek.toString()
            var temp = ""
            when (day) {
                "MONDAY" -> temp = "thứ 2"
                "TUESDAY" -> temp = "thứ 3"
                "WEDNESDAY" -> temp = "thứ 4"
                "THURSDAY" -> temp = "thứ 5"
                "FRIDAY" -> temp = "thứ 6"
                "SATURDAY" -> temp = "thứ 7"
                "SUNDAY" -> temp = "chủ nhật"
            }
            updateTime?.setText("Cập nhật mới nhất ${temp} (${comic.chapter[comic.chapter.size - 1].datePost})")
        }

    }

    private fun initRecyclerView(view: View?) {

        recyclerView = view?.findViewById(R.id.recommendComicRC)!!
        recyclerView?.setHasFixedSize(true);

        val comicList = arrayListOf<comicItem>()
        comicList.add(comicItem("Naruto", R.drawable.cover_manga, 100))
        comicList.add(comicItem("One piece", R.drawable.cover_manga, 501))
        comicList.add(comicItem("Hunter x hunter", R.drawable.cover_manga, 208))
        comicList.add(comicItem("Bleach", R.drawable.cover_manga, 130))
        comicList.add(comicItem("Doraemon", R.drawable.cover_manga, 208))
        comicList.add(comicItem("Dragon ball", R.drawable.cover_manga, 130))

        recyclerView?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val adapter = context?.let { SuggestComicAdapter(it, comicList) }
        adapter?.onItemClick = { tmp ->
            Log.i("test", tmp.name.toString())
        }
        recyclerView?.adapter = adapter
    }
}