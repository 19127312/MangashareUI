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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.activity.UserReportActivity
import com.khtn.mangashare.comment.ViewCommentActivity
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem


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
    lateinit var commentLL: LinearLayout

    private fun init() {
        val intent = intent
        comic = intent.getSerializableExtra("comic") as comicItem
        var num: Int = intent.getIntExtra("chapterNumber", -1)
        var numSort: Int = intent.getIntExtra("sort", -1)

        commentLL = findViewById(R.id.commentChapterDetailLL)
        markLL = findViewById(R.id.markChapterDetailLL)
        markIV = findViewById(R.id.markChapterIM)
        chapterListLL = findViewById(R.id.chooseChapterDetailLL)
        val tblayout = findViewById<ConstraintLayout>(R.id.chapterDetailToolbarCL)
        val nalayout = findViewById<ConstraintLayout>(R.id.bottomChapterDetailCL)
        var tb = findViewById<Toolbar>(R.id.chapterDetailTB)

        setSupportActionBar(tb)
        tb.setTitleTextAppearance(this, R.style.ToolbarFont)
        tb.title = "Chương ${num + 1}"
        val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_menu_white)
        tb.setOverflowIcon(drawable)
        tb.setNavigationOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("comic", comic)
            replyIntent.putExtra("sort", numSort)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

        adapter = ViewPagerChapterDetailAdapter(
            supportFragmentManager, lifecycle,
            comic, num, nalayout, tblayout
        )
        viewPager = findViewById(R.id.chapterDetailVP)
        if (num != -1) {
            viewPager?.adapter = adapter
        }
        //view comment
        commentLL.setOnClickListener {
            val intent = Intent(this, ViewCommentActivity::class.java)
            intent.putExtra("comic", comic)
            intent.putExtra("chapterNumber", num + 1)
            startActivity(intent)
        }
        //back and previous chapter
        backBTN = findViewById(R.id.goPreviousChapterIM)
        nextBTN = findViewById(R.id.goNextChapterIM)
        goBackNextChapter(num, numSort)
        markComic(num)
        showBottomSheet(num)
    }

    private fun showBottomSheet(pos: Int) {
        chapterListLL.setOnClickListener {
            showBottomSheetDialog(comic.chapter, pos)
        }
    }

    private fun goBackNextChapter(pos: Int, sort: Int) {
        if (pos == 0) {
            backBTN.setImageResource(R.drawable.ic_caret_left)
        } else if (pos == comic.chapter.size - 1) {
            nextBTN.setImageResource(R.drawable.ic_caret_right)
        }

        backBTN.setOnClickListener {
            if (pos > 0) {
                val intent = Intent(this, ViewChapterDetailActivity::class.java)
                intent.putExtra("comic", comic)
                intent.putExtra("sort", sort)
                intent.putExtra("chapterNumber", pos - 1)
                startActivityForResult(intent, 111)
                finish()
            }
        }
        nextBTN.setOnClickListener {
            if (pos < comic.chapter.size - 1){
                val intent = Intent(this, ViewChapterDetailActivity::class.java)
                intent.putExtra("comic", comic)
                intent.putExtra("sort", sort)
                intent.putExtra("chapterNumber", pos + 1)
                startActivityForResult(intent, 111)
                finish()
            }
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
            bottomSheetDialog.dismiss()
            finish()
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