package com.khtn.mangashare.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import com.khtn.mangashare.R

class SearchComicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_comic)
        init()
    }
    private fun init(){
        val inputSearchText = findViewById<EditText>(R.id.inputSearchTV)
        val searchIM = findViewById<ImageView>(R.id.searchComicIM)
        val clearIM = findViewById<ImageView>(R.id.clearSeachComicIM)

        if(inputSearchText.text.length == 0){
            clearIM.setVisibility(View.INVISIBLE)
        }

        inputSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(inputSearchText.text.length == 0){
                    clearIM.setVisibility(View.INVISIBLE)
                }
                else{
                    clearIM.setVisibility(View.VISIBLE)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearIM.setVisibility(View.VISIBLE)
            }
        })

        clearIM.setOnClickListener {
            inputSearchText.setText("")
        }
    }
}