package com.khtn.mangashare.booklist.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.khtn.mangashare.R
import kotlinx.android.synthetic.main.activity_add_comic.*

class AddComicActivity : AppCompatActivity() {
    private val IMAGE_PICK_GALLARY_CODE=100
    var thumbnail: Uri? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comic)

        backPressAddComic.setOnClickListener {

            finish()
        }

        addCover()
    }

    private fun addCover() {
        addCoverBackground.setOnClickListener {
            Log.d("MyScreen","background")
            openGallery()
        }

        addCoverIcon.setOnClickListener {
            Log.d("MyScreen","Icon")
            openGallery()
        }

        coverImage.setOnClickListener {
            Log.d("MyScreen","Cover")
            openGallery()
        }
        addCoverText.setOnClickListener {
            Log.d("MyScreen","text")
            openGallery()
        }
    }
    fun openGallery(){
        var i= Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(i,IMAGE_PICK_GALLARY_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==IMAGE_PICK_GALLARY_CODE &&resultCode== Activity.RESULT_OK&& data!=null){
            thumbnail=data.data!!
            coverImage.setImageURI(thumbnail)
            addCoverIcon.visibility= View.INVISIBLE
        }


    }
}