package com.khtn.mangashare.booklist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khtn.mangashare.R
import com.khtn.mangashare.comicDetail.ComicDetailActivity
import com.khtn.mangashare.model.comicItem
import kotlinx.android.synthetic.main.activity_view_report.*

class ViewReportActivity : AppCompatActivity() {
    lateinit var comic:comicItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_report)
        initActivity()

        backPressViewReport.setOnClickListener {
            finish()
        }
        goToComicDetail()
    }

    private fun goToComicDetail() {
        rootLinearReport.setOnClickListener {
            var intent: Intent
            intent= Intent(this,ComicDetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initActivity() {
        val intent = intent
        comic = intent.getSerializableExtra("comic") as comicItem

        reportCover.setImageResource(comic.cover)
        nameReport.text=comic.name
        authorReport.text=comic.author
        reporterName.text= "Người báo cáo:"+comic.reporter
        contextReport.text=comic.contextReport
        if(comic.replyReport==""){
            rootLinearReport.removeView(headerReplyAuthor)
            rootLinearReport.removeView(authorReplyReport)
        }else{
            authorReplyReport.text=comic.replyReport
        }
    }
}