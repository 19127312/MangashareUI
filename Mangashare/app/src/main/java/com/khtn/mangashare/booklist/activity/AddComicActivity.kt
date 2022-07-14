package com.khtn.mangashare.booklist.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.SuggestCategoryAdapter
import com.khtn.mangashare.booklist.adapter.chapterEditAdapter
import com.khtn.mangashare.model.chapterItem
import kotlinx.android.synthetic.main.activity_add_comic.*
import kotlinx.android.synthetic.main.activity_add_comic.backPressUserReport
import kotlinx.android.synthetic.main.activity_add_comic.titleModeComic
import kotlinx.android.synthetic.main.activity_user_report.*

class AddComicActivity : AppCompatActivity() {
    private val IMAGE_PICK_GALLARY_CODE=100
    var thumbnail: Uri? =null
    var selectedItems: ArrayList<Int> = ArrayList<Int>()
    lateinit var chapterList : ArrayList<chapterItem>

    var categoryArray: Array<String> = arrayOf("Hành động", "Hài hước", "Tình cảm","Trinh thám","Võ thuật","Kinh dị")
    var chooseArray:ArrayList<String> = ArrayList()
    val initSelectedItems = booleanArrayOf(false, false, false,false, false, false)
    lateinit var adapter: SuggestCategoryAdapter
    lateinit var chapterAdapter: chapterEditAdapter
    lateinit var mode: String
    lateinit var position: String
    lateinit var layout: LinearLayout
    var isReverse=false
    var isSetCover=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comic)
        var intent: Intent = getIntent()
        mode = intent.getStringExtra("mode").toString()
        position= intent.getStringExtra("position").toString()
        layout=findViewById<LinearLayout>(R.id.rootLinear)
        backPressUserReport.setOnClickListener {

            finish()
        }

        addCover()
        dropdownTV.setOnClickListener {
            categoryClick()
        }

        addChapter()
        confirmAddComicBtn.setOnClickListener {
            var isOK=true
            if(editTextTextPersonName.text?.isEmpty() == true){
                textInputLayoutName.helperText="Tên truyện không được bỏ trống"
                isOK=false
            }

            if(editTextTextMultiLine.text?.isEmpty() == true){
                textInputLayoutDes.helperText="Mô tả không được bỏ trống"
                isOK=false
            }

            if(!isSetCover){
                addCoverText.text="Cần thêm bìa truyện"
                addCoverText.setTextColor(getResources().getColor(R.color.skip))
                isOK=false
            }
            if(isOK ){
                finish()
            }

            //
        }
        initCatRV()
        setupView(mode)
        editTextTextPersonName.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editTextTextPersonName.clearFocus()

            }
            false
        })

        editTextTextMultiLine.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editTextTextMultiLine.clearFocus()
            }
            false
        })

        icon_trash_comic_btn.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("position", position)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }
    override fun onBackPressed() {
        if( editTextTextPersonName.isFocused ||editTextTextMultiLine.isFocused){
            editTextTextPersonName.clearFocus()
            editTextTextMultiLine.clearFocus()

        }else {
            super.onBackPressed();
        }
    }
    private fun setupView(mode: String) {


        when(mode){
            "add"-> {

                layout.removeView(chapterTableLayout)
                layout.removeView(chapterRV)
                icon_trash_comic_btn.visibility=View.INVISIBLE
                chapterList= ArrayList<chapterItem>()
                chapterAdapter = chapterEditAdapter(chapterList)


            }
            "edit"-> {
                isSetCover=true
                titleModeComic.text="Sửa thông tin truyện"
                addCoverIcon.visibility= View.INVISIBLE
                addCoverText.text="Sửa bìa truyện"
                reportCover.setImageResource(R.drawable.cover_manga)
                editTextTextPersonName.setText("Plapla pla")
                editTextTextMultiLine.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tincidunt tellus sed nulla auctor egestas. Quisque consectetur eros at vehicula malesuada ")
                setupRV()
                setupFilter()
            }
        }
    }
    fun setupFilter(){
        filterChapterMode.setOnClickListener {
            chapterList.reverse()
            chapterAdapter.notifyDataSetChanged()
            isReverse=!isReverse

            if(isReverse){
                filterChapterMode.text="Mới nhất"
            }else{
                filterChapterMode.text="Cũ nhất"

            }
        }
    }
    fun setupRVadd(){
        chapterRV.adapter=chapterAdapter
        chapterRV.layoutManager= LinearLayoutManager(this)

        chapterAdapter.setOnItemClickListener(object: chapterEditAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var intent: Intent=Intent(this@AddComicActivity,PickChapterActivity::class.java)
                intent.putExtra("mode","edit")
                intent.putExtra("positionChapter",position.toString())

                startActivityForResult(intent,5555)
            }

        })
    }
    private fun setupRV() {
        chapterList= ArrayList<chapterItem>()
        chapterList.add(chapterItem(1,"10/01/22"))
        chapterList.add(chapterItem(2,"12/01/22"))
        chapterList.add(chapterItem(3,"13/01/22"))
        chapterList.add(chapterItem(4,"14/01/22"))
        chapterList.add(chapterItem(5,"15/01/22"))

        chapterAdapter = chapterEditAdapter(chapterList)
        chapterRV.adapter=chapterAdapter
        chapterRV.layoutManager= LinearLayoutManager(this)

        chapterAdapter.setOnItemClickListener(object: chapterEditAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var intent: Intent=Intent(this@AddComicActivity,PickChapterActivity::class.java)
                intent.putExtra("mode","edit")
                intent.putExtra("positionChapter",position.toString())

                startActivityForResult(intent,5555)
            }

        })
    }

    private fun initCatRV() {
        if(mode=="add"){
            rootLinear.removeView(categoryRV)
        }else{
            selectedItems.add(0)
            selectedItems.add(1)
            chooseArray.add("Hành động")
            chooseArray.add("Hài hước")
            initSelectedItems[0]=true
            initSelectedItems[1]=true
            initAdapterRV()
            Log.d("MyScreen", rootLinear.childCount.toString())
        }

    }
    private fun initAdapterRV(){
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
        builder.setTitle("Chọn thể loại")
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
                    if(selectedItems.size==0){
                        rootLinear.removeView(categoryRV)
                    }else{
                        val x: Int = rootLinear.childCount
                        if(x==7 || x ==5){
                            Log.d("MyScreen", "addRV")
                            rootLinear.addView(categoryRV,4)
                        }
                        initAdapterRV()

                        adapter.notifyDataSetChanged()

                    }
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
                    adapter.notifyDataSetChanged()
                    rootLinear.removeView(categoryRV)
                })
            .setOnCancelListener {
                chooseArray.clear()
                for(item in selectedItems){
                    if(!chooseArray.contains(categoryArray[item])){
                        chooseArray.add(categoryArray[item])
                    }
                }
                if(selectedItems.size==0){
                    rootLinear.removeView(categoryRV)
                }else{
                    val x: Int = rootLinear.childCount
                    if(x==7 || x ==5){
                        rootLinear.addView(categoryRV,4)
                    }
                    initAdapterRV()
                    adapter.notifyDataSetChanged()

                }

            }
        builder.create()
        builder.show()


    }

    private fun addCover() {
        reportBackground.setOnClickListener {
            openGallery()
        }

        addCoverIcon.setOnClickListener {
            openGallery()
        }

        reportCover.setOnClickListener {
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
            reportCover.setImageURI(thumbnail)
            addCoverIcon.visibility= View.INVISIBLE
            isSetCover=true
            addCoverText.text="Sửa bìa truyện"
            addCoverText.setTextColor(getResources().getColor(androidx.media.R.color.secondary_text_default_material_light))

        }
        if(requestCode===5555 &&resultCode== Activity.RESULT_OK){
            val position = data!!.getStringExtra("positionChapter")?.toInt()
            val status = data!!.getStringExtra("status")
            if(status=="add"){
                if(position==null){
                    var table= findViewById<RecyclerView>(R.id.chapterRV)
                    var bar= findViewById<LinearLayout>(R.id.chapterTableLayout)
                    if(table==null && bar ==null){
                        layout.addView(chapterTableLayout,5)
                        layout.addView(chapterRV,6)
                        setupRVadd()
                        setupFilter()
                    }

                    if(isReverse){
                        chapterList.add(0,chapterItem(chapterList.size+1,"10/01/22"))
                        chapterAdapter.notifyItemInserted(0)

                    }
                    else{
                        chapterList.add(chapterItem(chapterList.size+1,"10/01/22"))
                        chapterAdapter.notifyItemInserted(chapterList.size)

                    }

                }
            }else if(status=="delete"){
                var snackbar = Snackbar.make(rootAddComic , "Đã xóa truyện ${position?.plus(1)}", Snackbar.LENGTH_LONG)
                var item= chapterItem(chapterList[position!!])
                chapterList.removeAt(position)
                chapterAdapter.notifyItemRemoved(position)
                chapterAdapter.notifyItemRangeChanged(position,chapterList.size)
                snackbar.show()
                snackbar.setAction("Undo delete", View.OnClickListener() {
                    chapterList.add(position,item)
                    chapterAdapter.notifyItemInserted(position)
                    chapterAdapter.notifyItemRangeChanged(position, chapterList.size)
                })
            }else if(status=="edit"){
            }


        }


    }



}

