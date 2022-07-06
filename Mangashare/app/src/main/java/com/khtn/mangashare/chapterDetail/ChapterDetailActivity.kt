package com.khtn.mangashare.chapterDetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.khtn.mangashare.R
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
    lateinit var backBTN: ImageView
    lateinit var nextBTN: ImageView
    lateinit var viewPager: ViewPager2

    private fun init() {
        val intent = intent
        comic = intent.getSerializableExtra("comic") as comicItem
        val num: Int = intent.getIntExtra("chapterNumber", -1)
        markLL = findViewById(R.id.markChapterDetailLL)
        markIV = findViewById(R.id.markChapterIM)

        val tblayout = findViewById<ConstraintLayout>(R.id.chapterDetailToolbarCL)
        val nalayout = findViewById<ConstraintLayout>(R.id.bottomChapterDetailCL)
        var tb = findViewById<Toolbar>(R.id.chapterDetailTB)
        setSupportActionBar(tb)
        tb.setTitleTextAppearance(this, R.style.ToolbarFont)
        tb.title = "Chương ${num + 1}"

        tb.setNavigationOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("comic", comic)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
        adapter = ViewPagerChapterDetailAdapter(
            supportFragmentManager, lifecycle,
            comic, tblayout, nalayout
        )
        viewPager = findViewById(R.id.chapterDetailVP)
        if (num != -1) {
            viewPager?.adapter = adapter
            viewPager.setCurrentItem(num)
        }


        //back and previous chapter
        backBTN = findViewById(R.id.goPreviousChapterIM)
        nextBTN = findViewById(R.id.goNextChapterIM)
        goBackNextChapter(num)

        markComic(num)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tb.title = "Chương ${position + 1}"
                markComic(position)
                goBackNextChapter(position)
            }
        })


    }
    private fun goBackNextChapter(pos: Int){
        backBTN.setOnClickListener {
            viewPager.setCurrentItem(pos - 1)
        }
        nextBTN.setOnClickListener {
            viewPager.setCurrentItem(pos + 1)
        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chapter_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.getItemId()) {
            R.id.action_add -> {
                true
            }
            R.id.action_settings -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}