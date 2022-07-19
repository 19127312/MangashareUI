package com.khtn.mangashare.booklist.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.adapter.ReportImageAdapter
import com.khtn.mangashare.model.picItem
import kotlinx.android.synthetic.main.activity_view_censor_chapter.*
import kotlinx.android.synthetic.main.activity_view_censor_comic.*
import kotlinx.android.synthetic.main.activity_view_report.*

class ViewCensorChapterActivity : AppCompatActivity() {
    var imgsList= ArrayList<picItem>()
    lateinit var adapter: ReportImageAdapter
    var number=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_censor_chapter)
        number=intent.getIntExtra("number",0)
        censorChapter.text="Chương ${number}"
        backPressChapterCensor.setOnClickListener {
            finish()
        }
        initRV()
        yesChapterBtn.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
        openDialog()
    }
    private fun initRV() {
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))
        imgsList.add(picItem(R.drawable.cover_manga))

        adapter = ReportImageAdapter(imgsList)
        chapterDetailCensorRV.adapter=adapter
        chapterDetailCensorRV.layoutManager= GridLayoutManager(this,3)

        adapter.setOnItemClickListener(object: ReportImageAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var intent: Intent
                intent= Intent(this@ViewCensorChapterActivity,ZoomImageActivity::class.java)
                intent.putExtra("comic",imgsList[position])
                startActivity(intent)
            }
        })
    }
    private fun openDialog() {
        noChapterBtn.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.custom_report_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val body = dialog.findViewById(R.id.cancelBtn) as TextView
            body.setOnClickListener {
                dialog.dismiss()
            }
            val sentBtn = dialog.findViewById(R.id.sentReplyBtn) as Button

            sentBtn.setOnClickListener {
                dialog.dismiss()
                val replyIntent = Intent()
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
            dialog.show()

        }
    }
}