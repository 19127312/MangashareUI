package com.khtn.mangashare.chapterDetail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.khtn.mangashare.R
import com.khtn.mangashare.comicDetail.ViewPagerComicDetailAdapter
import com.khtn.mangashare.model.comicItem

class ChapterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_detail)
        init()
    }

    lateinit var adapter: ViewPagerChapterDetailAdapter
    lateinit var markLL: LinearLayout
    lateinit var markIV: ImageView
    lateinit var comic: comicItem

    private fun init() {
        val intent = intent
        comic = intent.getSerializableExtra("comic") as comicItem
        val num: Int = intent.getIntExtra("chapterNumber", -1)
        markLL = findViewById(R.id.markChapterDetailLL)
        markIV = findViewById(R.id.markChapterIM)

        var tb = findViewById<Toolbar>(R.id.chapterDetailTB)

        tb.title = "Chương ${num + 1}"

        tb.setNavigationOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("comic", comic)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
        adapter = ViewPagerChapterDetailAdapter(
            supportFragmentManager, lifecycle,
            comic
        )
        val viewPager = findViewById<ViewPager2>(R.id.chapterDetailVP)
        if (num != -1) {
            viewPager?.adapter = adapter
            viewPager.setCurrentItem(num)

        }

        markComic(num)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tb.title = "Chương ${position + 1}"
                markComic(position)
            }
        })
    }

    private fun markComic(pos: Int) {
        if (comic.chapter[pos].bookmark == true) {
            markIV.setImageResource(R.drawable.ic_unbook_mark)
        } else {
            markIV.setImageResource(R.drawable.ic_book_mark)
        }
        markLL.setOnClickListener {
            if (comic.chapter[pos].bookmark == true) {
                comic.chapter[pos].bookmark = false

                markIV.setImageResource(R.drawable.ic_book_mark)
            } else {
                comic.chapter.forEach { i ->
                    i.bookmark = false
                }
                comic.chapter[pos].bookmark = true
                markIV.setImageResource(R.drawable.ic_unbook_mark)
            }
            adapter.notifyDataSetChanged()
        }
    }
}