package com.khtn.mangashare.comment

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.model.comicItem
import com.khtn.mangashare.model.commentItem


class ViewCommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_comment)
        init()
    }

    private fun init() {
        val intent = intent
        var comic: comicItem = intent.getSerializableExtra("comic") as comicItem
        var chapter: Int = intent.getIntExtra("chapterNumber", -1)

        val sendBtn = findViewById<ImageView>(R.id.sendCommentIM)
        val content = findViewById<EditText>(R.id.inputCommentTV)
        val tb = findViewById<Toolbar>(R.id.viewCommentTB)
        val rc = findViewById<RecyclerView>(R.id.viewCommentRC)
        val adapter = ViewCommentAdapter(this, comic.comment)
        rc.setHasFixedSize(true);
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adapter
        rc.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        tb.title = comic.name
        tb.setNavigationOnClickListener {
            finish()
        }
        sendBtn.setOnClickListener {
            if (content.text.toString().length > 0) {
                comic.comment.add(
                    0,
                    commentItem("Đào Duy An", chapter, "20/07/2022", content.text.toString())
                )
                adapter.notifyDataSetChanged()
                content.setText("")
                val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                rc.smoothSnapToPosition(0)
            }
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