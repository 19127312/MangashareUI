package com.khtn.mangashare.booklist.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.SuggestCategoryAdapter
import com.khtn.mangashare.booklist.adapter.PickedChapterAdapter
import kotlinx.android.synthetic.main.activity_add_comic.*
import kotlinx.android.synthetic.main.fragment_add_book_list.*

class AddComicActivity : AppCompatActivity() {
    private val IMAGE_PICK_GALLARY_CODE=100
    var thumbnail: Uri? =null
    var selectedItems: ArrayList<Int> = ArrayList<Int>()

    var categoryArray: Array<String> = arrayOf("Hành động", "Hài hước", "Tình cảm","Trinh thám","Võ thuật","Kinh dị")
    var chooseArray:ArrayList<String> = ArrayList()
    val initSelectedItems = booleanArrayOf(false, false, false,false, false, false)
    lateinit var adapter: SuggestCategoryAdapter

    lateinit var mode: String
    lateinit var layout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comic)
        var intent: Intent = getIntent()
        mode = intent.getStringExtra("mode").toString()
        layout=findViewById<LinearLayout>(R.id.rootLinear)
        backPressAddComic.setOnClickListener {

            finish()
        }

        addCover()
        dropdownTV.setOnClickListener {
            categoryClick()
        }

        addChapter()
        confirmAddComicBtn.setOnClickListener {
            finish()
        }
        initCatRV()
        setupView(mode)
    }

    private fun setupView(mode: String) {


        when(mode){
            "add"-> {
                layout.removeView(headerChaptername)
                layout.removeView(headerDescription)
                layout.removeView(chapterTableLayout)

            }
            "edit"-> {
                titleModeComic.text="Sửa thông tin truyện"
                addCoverIcon.visibility= View.INVISIBLE
                addCoverText.text="Sửa bìa truyện"
                coverImage.setImageResource(R.drawable.cover_manga)
                editTextTextPersonName.setText("Plapla pla")
                editTextTextMultiLine.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tincidunt tellus sed nulla auctor egestas. Quisque consectetur eros at vehicula malesuada ")
            }
        }
    }

    private fun initCatRV() {
        if(mode=="add"){
            chooseArray.add("Thể loại")
        }else{
            chooseArray.add("Hành động")
            chooseArray.add("Hài hước")
            initSelectedItems[0]=true
            initSelectedItems[1]=true

        }
        adapter=SuggestCategoryAdapter(this, chooseArray)
        categoryRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryRV.adapter = adapter
    }

    private fun addChapter() {
        var intent: Intent
        intent= Intent(this,PickChapterActivity::class.java)
        intent.putExtra("mode","add")
        backgroundAddChapter.setOnClickListener {
            startActivityForResult(intent,5555)
        }
        iconAddChapter.setOnClickListener {
            startActivityForResult(intent,5555)
        }
        textAddChapter.setOnClickListener {
            startActivityForResult(intent,5555)

        }
    }


    private fun categoryClick() {
        val builder = AlertDialog.Builder(this)
        Log.d("HeLLO","He")
        builder.setTitle("Dialog with checkboxes")
            .setMultiChoiceItems(
                categoryArray, initSelectedItems,
                DialogInterface.OnMultiChoiceClickListener {
                        dialog, which, isChecked ->
                    if (isChecked) {
                        selectedItems.add(which)
                    } else if (selectedItems.contains(which)) {
                        selectedItems.remove(Integer.valueOf(which))
                    }
                })
            .setPositiveButton("OK",
                DialogInterface.OnClickListener{dialogInterface, i ->
                    chooseArray.clear()
                    for(item in selectedItems){
                            chooseArray.add(categoryArray[item])

                    }
                    adapter.notifyDataSetChanged()
                })
            .setNegativeButton("Thoát",
                DialogInterface.OnClickListener{dialogInterface, i ->
                    dialogInterface.dismiss()
                })
            .setNeutralButton("Xóa tất cả",
                DialogInterface.OnClickListener{dialogInterface, i ->
                    selectedItems.clear()
                    for (i in initSelectedItems.indices) {
                        initSelectedItems[i]=false
                    }
                    chooseArray.clear()
                    chooseArray.add("Thể loại")
                    adapter.notifyDataSetChanged()

                })
            .setOnCancelListener {
                chooseArray.clear()
                for(item in selectedItems){
                    if(!chooseArray.contains(categoryArray[item])){
                        chooseArray.add(categoryArray[item])
                    }
                }
                adapter.notifyDataSetChanged()

            }
        builder.create()
        builder.show()

//        for (i in initSelectedItems.indices) {
//            if(initSelectedItems[i]){
//                Log.d("MyScreen",categoryArray[i].toString())
//            }
//        }
    }

    private fun addCover() {
        addCoverBackground.setOnClickListener {
            openGallery()
        }

        addCoverIcon.setOnClickListener {
            openGallery()
        }

        coverImage.setOnClickListener {
            openGallery()
        }
        addCoverText.setOnClickListener {
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
        if(requestCode===5555 &&resultCode== Activity.RESULT_OK){
            finish()
        }


    }



}

