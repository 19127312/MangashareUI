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
import kotlinx.android.synthetic.main.activity_zoom_image.*
import kotlinx.android.synthetic.main.fragment_chapter_detail.*

class ViewPagerChapterDetailAdapter : FragmentStateAdapter {
    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        comic: comicItem,
        num: Int,
        naLayout: ConstraintLayout,
        tblayout: ConstraintLayout
    ) : super(fragmentManager, lifecycle) {
        this.comic = comic
        this.num = num
        this.naLayout = naLayout
        this.tbLayout = tblayout
    }

    private var comic: comicItem
    private var num: Int
    private var naLayout: ConstraintLayout
    private var tbLayout: ConstraintLayout

    override fun getItemCount(): Int = comic.chapter[num].imageList.size

    override fun createFragment(position: Int): Fragment {
        return DetailChapterFragment(comic.chapter[num].imageList[position], tbLayout, naLayout)
    }
}

class DetailChapterFragment(
    private var image: Int,
    private var tblayout: ConstraintLayout,
    private var naLayout: ConstraintLayout
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_chapter_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        imageChapterDetail.setImageResource(image)
        var check = false
        imageChapterDetail?.setOnClickListener {
            var anim: Animation
            if (check == false) {
                anim = AnimationUtils.loadAnimation(context, R.anim.anim_chapter_detail)
                check = true
            } else {
                anim = AnimationUtils.loadAnimation(context, R.anim.anim_chapter_detail_fade_out)
                check = false
            }
            naLayout.startAnimation(anim)
            tblayout.startAnimation(anim)
        }
    }
}
