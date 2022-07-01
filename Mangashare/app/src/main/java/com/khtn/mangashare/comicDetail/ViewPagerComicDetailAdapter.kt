package com.khtn.mangashare.comicDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.RankingComicAdapter
import com.khtn.mangashare.adapter.SuggestComicAdapter
import com.khtn.mangashare.model.comicItem
import com.ms.square.android.expandabletextview.ExpandableTextView

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
        initRecyclerView(view)
        init(view)
    }



    private fun init(view: View?) {
        var reviewNumber = view?.findViewById<TextView>(R.id.reviewNumberTV)
        var viewNumber = view?.findViewById<TextView>(R.id.viewNumberTV)
        var likeNumber = view?.findViewById<TextView>(R.id.likeNumberTV)
        var followNumber = view?.findViewById<TextView>(R.id.followNumberTV)
        var description = view?.findViewById<ExpandableTextView>(R.id.expandTV)
        description?.setText(comic.description)

        comic.reviewNumber?.toString().let { reviewNumber?.setText(it) }
        comic.viewNumber?.toString().let { viewNumber?.setText(it) }
        comic.likeNumber?.toString().let { likeNumber?.setText(it) }
        comic.followNumber?.toString().let { followNumber?.setText(it) }
    }

    private fun initRecyclerView(view: View?) {
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
                    if(item.status == true){
                        item.status = false
                    }
                    else{
                        items.forEach { i ->
                            when(i){
                                is ChapterRecyclerViewItem.VipChapter -> i.status=false
                                is ChapterRecyclerViewItem.FreeChapter -> i.status=false
                            }
                        }
                        item.status= true
                    }
                }
                is ChapterRecyclerViewItem.FreeChapter -> {
                    if(item.status == true){
                        item.status = false
                    }
                    else{
                        items.forEach { i ->
                            when(i){
                                is ChapterRecyclerViewItem.VipChapter -> i.status=false
                                is ChapterRecyclerViewItem.FreeChapter -> i.status=false
                            }
                        }
                        item.status= true
                    }

                }
            }
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

    private fun init(view: View?) {
        var reviewNumber = view?.findViewById<TextView>(R.id.reviewNumberTV)
        var viewNumber = view?.findViewById<TextView>(R.id.viewNumberTV)
        var likeNumber = view?.findViewById<TextView>(R.id.likeNumberTV)
        var followNumber = view?.findViewById<TextView>(R.id.followNumberTV)
        var description = view?.findViewById<ExpandableTextView>(R.id.expandTV)
        description?.setText(comic.description)

        comic.reviewNumber?.toString().let { reviewNumber?.setText(it) }
        comic.viewNumber?.toString().let { viewNumber?.setText(it) }
        comic.likeNumber?.toString().let { likeNumber?.setText(it) }
        comic.followNumber?.toString().let { followNumber?.setText(it) }
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