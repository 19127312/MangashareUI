package com.khtn.mangashare.chapterDetail

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
import com.khtn.mangashare.comicDetail.ChapterRecyclerViewAdapter
import com.khtn.mangashare.comicDetail.ChapterRecyclerViewItem
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem
import com.ms.square.android.expandabletextview.ExpandableTextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ViewPagerChapterDetailAdapter : FragmentStateAdapter {
    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        comic: comicItem
    ) : super(fragmentManager, lifecycle) {
        this.comic = comic
    }

    constructor(
        fragment: Fragment,
        comic: comicItem,
        num: Int
    ) : super(fragment) {
        this.comic = comic
    }


    private var comic: comicItem
    override fun getItemCount(): Int = comic.chapter.size

    override fun createFragment(position: Int): Fragment {
        return DetailChapterFragment(comic.chapter[position])
    }
}

class DetailChapterFragment(private var chapter: chapterItem) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_chapter_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View?) {
        val rc = view?.findViewById<RecyclerView>(R.id.chapterDetailRC)
        rc?.setHasFixedSize(true);

        rc?.layoutManager = LinearLayoutManager(activity)
        val adapter = context?.let { ImageChapterDetailAdapter(it, chapter.imageList) }
        rc?.adapter = adapter
    }
}
