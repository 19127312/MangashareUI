package com.khtn.mangashare.chapterDetail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.activity.UserReportActivity
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem
import com.khtn.mangashare.navigation.MainActivity


class ViewChapterDetailActivity : AppCompatActivity() {
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
    lateinit var chapterListLL: LinearLayout

    private fun init() {
        val intent = intent
        comic = intent.getSerializableExtra("comic") as comicItem
        val num: Int = intent.getIntExtra("chapterNumber", -1)
        val numSort: Int = intent.getIntExtra("sort", -1)

        markLL = findViewById(R.id.markChapterDetailLL)
        markIV = findViewById(R.id.markChapterIM)
        chapterListLL = findViewById(R.id.chooseChapterDetailLL)
        val tblayout = findViewById<ConstraintLayout>(R.id.chapterDetailToolbarCL)
        val nalayout = findViewById<ConstraintLayout>(R.id.bottomChapterDetailCL)
        var tb = findViewById<Toolbar>(R.id.chapterDetailTB)

        setSupportActionBar(tb)
        tb.setTitleTextAppearance(this, R.style.ToolbarFont)
        tb.title = "Chương ${num + 1}"

        tb.setNavigationOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("comic", comic)
            replyIntent.putExtra("sort", numSort)
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
                showBottomSheet(position)
            }
        })
        showBottomSheet(num)
    }

    private fun showBottomSheet(pos: Int) {
        chapterListLL.setOnClickListener {
            showBottomSheetDialog(comic.chapter, pos)
        }
    }

    private fun goBackNextChapter(pos: Int) {
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

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        menuInflater.inflate(R.menu.menu_chapter_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.getItemId()) {
            R.id.action_follow -> {
                item.setIcon(R.drawable.ic_hotspot_checked)
                true
            }
            R.id.action_report -> {
                val intent = Intent(this, UserReportActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_share -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showBottomSheetDialog(chapterList: ArrayList<chapterItem>, number: Int) {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.fragment_bottom_sheet_chapter_list)
        val rc = bottomSheetDialog.findViewById<RecyclerView>(R.id.bottomSheetChapterListRC)
        var adapter = BottomSheetChapterListAdapter(this, chapterList, number)
        rc?.layoutManager = LinearLayoutManager(this)
        rc?.adapter = adapter
        rc?.smoothSnapToPosition(number)
        rc?.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        bottomSheetDialog.show()
        adapter?.onItemClick = { tmp ->
            val intent = Intent(this, ViewChapterDetailActivity::class.java)
            intent.putExtra("comic", comic)
            intent.putExtra("chapterNumber", tmp.number?.minus(1))
            startActivityForResult(intent, 111)
        }
    }

    private fun RecyclerView.smoothSnapToPosition(
        position: Int,
        snapMode: Int = LinearSmoothScroller.SNAP_TO_START
    ) {
        val smoothScroller = object : LinearSmoothScroller(this.context) {
            override fun getVerticalSnapPreference(): Int = snapMode
            override fun getHorizontalSnapPreference(): Int = snapMode
        }
        smoothScroller.targetPosition = position
        layoutManager?.startSmoothScroll(smoothScroller)
    }
}