package com.khtn.mangashare.chapterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.khtn.mangashare.R
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem

class ViewPagerChapterDetailAdapter : FragmentStateAdapter {
    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        comic: comicItem,
        tbLayout: ConstraintLayout,
        naLayout: ConstraintLayout
    ) : super(fragmentManager, lifecycle) {
        this.comic = comic
        this.tbLayout = tbLayout
        this.naLayout = naLayout
    }

    constructor(
        fragment: Fragment,
        comic: comicItem,
        num: Int,
        layout: ConstraintLayout
    ) : super(fragment) {
        this.comic = comic
        this.tbLayout = tbLayout
        this.naLayout = naLayout
    }


    private var comic: comicItem
    private lateinit var tbLayout: ConstraintLayout
    private lateinit var naLayout: ConstraintLayout
    override fun getItemCount(): Int = comic.chapter.size

    override fun createFragment(position: Int): Fragment {
        return DetailChapterFragment(comic.chapter[position], tbLayout, naLayout)
    }
}

class DetailChapterFragment(
    private var chapter: chapterItem,
    private var tbLayout: ConstraintLayout,
    private var naLayout: ConstraintLayout
) : Fragment() {
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
        var check = false
        adapter?.onItemClick = { tmp ->
            var anim: Animation = AnimationUtils.loadAnimation(context, R.anim.anim_chapter_detail)
            if(check == false){
                anim =  AnimationUtils.loadAnimation(context, R.anim.anim_chapter_detail)
                check = true
            }
            else{
                anim =  AnimationUtils.loadAnimation(context, R.anim.anim_chapter_detail_fade_out)
                check = false
            }
            tbLayout.startAnimation(anim)
            naLayout.startAnimation(anim)

        }
    }
}
