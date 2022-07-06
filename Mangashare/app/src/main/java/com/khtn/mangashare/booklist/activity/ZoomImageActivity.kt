package com.khtn.mangashare.booklist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khtn.mangashare.R
import com.khtn.mangashare.model.comicItem
import com.khtn.mangashare.model.picItem
import kotlinx.android.synthetic.main.activity_zoom_image.*

class ZoomImageActivity : AppCompatActivity() {
    lateinit var comic: picItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_image)
        val intent = intent
        comic = intent.getSerializableExtra("comic") as picItem
        chosenPicZoom.setImageResource(comic.imgResource)
    }
}