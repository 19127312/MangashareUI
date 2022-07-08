package com.khtn.mangashare.booklist.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.adapter.ReportImageAdapter
import com.khtn.mangashare.comicDetail.ComicDetailActivity
import com.khtn.mangashare.model.comicItem
import com.khtn.mangashare.model.picItem
import kotlinx.android.synthetic.main.activity_view_report.*
import kotlinx.android.synthetic.main.activity_view_report.sentReplyBtn
import kotlinx.android.synthetic.main.custom_report_layout.*

class ViewReportActivity : AppCompatActivity() {
    lateinit var comic:comicItem
    var imgsList= ArrayList<picItem>()
    lateinit var adapter: ReportImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_report)
        initActivity()
        initRV()
        backPressViewReport.setOnClickListener {
            finish()
        }
        goToComicDetail()

    }

    private fun openDialog() {
        sentReplyBtn.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.custom_report_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            val body = dialog.findViewById(R.id.cancelBtn) as TextView

            body.setOnClickListener {
                dialog.dismiss()
            }
            val yesBtn = dialog.findViewById(R.id.sentReplyBtn) as Button

            yesBtn.setOnClickListener {
                dialog.dismiss()
                finish()
            }
            dialog.show()

        }
    }

    private fun initRV() {
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        adapter = ReportImageAdapter(imgsList)
        pickedChapterRV.adapter=adapter
        pickedChapterRV.layoutManager= GridLayoutManager(this,3)

        adapter.setOnItemClickListener(object: ReportImageAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var intent: Intent
                intent= Intent(this@ViewReportActivity,ZoomImageActivity::class.java)
                intent.putExtra("comic",imgsList[position])
                startActivity(intent)
            }
        })
    }

    private fun goToComicDetail() {
        var intent: Intent
        intent= Intent(this,ComicDetailActivity::class.java)
        backGroundCoverDetail.setOnClickListener {
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
        if(comic.status=="Đã"){
            rootReportComic.removeView(rootLinearButton)
        }

        if(comic.replyReport==""){
            rootLinearReport.removeView(headerReplyAuthor)
            rootLinearReport.removeView(authorReplyReport)
        }else{
            authorReplyReport.text=comic.replyReport
        }

        if(comic.status=="Chưa" && comic.replyReport==""){
            rootLinearButton.removeView(sentReplyBtn)
            rootLinearButton.removeView(confirmReportBtn)
        }else if(comic.status=="Chưa" && comic.replyReport!=""){
            rootLinearButton.removeView(waitToConfirm)
            openDialog()
            confirmReportBtn.setOnClickListener {
                finish()
            }
        }

    }
}