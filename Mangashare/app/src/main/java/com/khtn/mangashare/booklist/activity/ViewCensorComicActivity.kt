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
import androidx.recyclerview.widget.LinearLayoutManager
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.SuggestCategoryAdapter
import com.khtn.mangashare.booklist.adapter.chapterEditAdapter
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem
import kotlinx.android.synthetic.main.activity_add_comic.*
import kotlinx.android.synthetic.main.activity_view_censor_comic.*
import kotlinx.android.synthetic.main.activity_view_report.*

class ViewCensorComicActivity : AppCompatActivity() {
    lateinit var chapterList : ArrayList<chapterItem>
    var chooseArray:ArrayList<String> = ArrayList()

    lateinit var categoryAdapter: SuggestCategoryAdapter
    lateinit var chapterAdapter: chapterEditAdapter
    var isReverse=false
    lateinit var comic: comicItem
    lateinit var position: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_censor_comic)
        comic= intent.getSerializableExtra("comic") as comicItem
        position= intent.getStringExtra("position").toString()

        nameCensorText.text=comic.name
        authorCensorText.text=comic.author
        censorCover.setImageResource(comic.cover)
        desEditText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tincidunt tellus sed nulla auctor egestas. Quisque consectetur eros at vehicula malesuada ")
        desEditText.isEnabled=false
        initCateRV()
        initChapterRV()
        setupFilter()
        backPressCensor.setOnClickListener {
            finish()
        }
        yesBtn.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("position", position)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
        openDialog()
    }
    val DELETE_CODE=666;

    private fun initChapterRV() {
        chapterList= ArrayList<chapterItem>()
        chapterList.add(chapterItem(1,"10/01/22"))
        chapterList.add(chapterItem(2,"12/01/22"))
        chapterList.add(chapterItem(3,"13/01/22"))
        chapterList.add(chapterItem(4,"14/01/22"))
        chapterList.add(chapterItem(5,"15/01/22"))

        chapterAdapter = chapterEditAdapter(chapterList)
        chapterCensorRV.adapter=chapterAdapter
        chapterCensorRV.layoutManager= LinearLayoutManager(this)

        chapterAdapter.setOnItemClickListener(object: chapterEditAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var intent: Intent
                intent= Intent(this@ViewCensorComicActivity,ViewCensorChapterActivity::class.java)
                intent.putExtra("number",position+1)
                startActivityForResult(intent,DELETE_CODE)
            }

        })
    }

    private fun initCateRV() {
        categoryAdapter=SuggestCategoryAdapter(this, chooseArray)
        chooseArray.add("Hành động")
        chooseArray.add("Hài hước")
        categoryCensorRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryCensorRV.adapter = categoryAdapter
    }
    fun setupFilter(){
        filterChapterCensor.setOnClickListener {
            chapterList.reverse()
            chapterAdapter.notifyDataSetChanged()
            isReverse=!isReverse

            if(isReverse){
                filterChapterCensor.text="Mới nhất"
            }else{
                filterChapterCensor.text="Cũ nhất"

            }
        }
    }
    private fun openDialog() {
        noBtn.setOnClickListener {
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
                replyIntent.putExtra("position", position)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
            dialog.show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==DELETE_CODE &&resultCode== Activity.RESULT_OK){
            val replyIntent = Intent()
            replyIntent.putExtra("position", position)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }
}