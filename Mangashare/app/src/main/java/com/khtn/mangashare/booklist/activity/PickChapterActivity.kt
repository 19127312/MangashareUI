package com.khtn.mangashare.booklist.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.adapter.PickedChapterAdapter
import com.khtn.mangashare.model.picItem
import kotlinx.android.synthetic.main.activity_pick_chapter.*
import kotlin.properties.Delegates


class PickChapterActivity : AppCompatActivity() {
    lateinit var imgsList : ArrayList<picItem>
    lateinit var adapter: PickedChapterAdapter
    var positionClick by Delegates.notNull<Int>()
    var isUpdate=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_chapter)


        isUpdate=intent.getBooleanExtra("isUpdate",false)
        positionClick=0
        //If update
        if(isUpdate){
            //TODO
        }
        setupRecycleView()
        setupView()
        pickChapterImage()

        itemChapterClick()
        backPressAddChapter.setOnClickListener {
            finish()

        }
        deleteAllBtn.setOnClickListener {
            imgsList.clear()
            adapter.notifyDataSetChanged()
            setupView()
        }

        priceInputText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                priceInputText.clearFocus()

            }
            false
        })

        radioCheck()

        confirmAddChapterBtn.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

    private fun radioCheck() {
        radioGroup.setOnCheckedChangeListener { group, i ->
            if(i== R.id.freeStatus){
                Log.d("MyScreen","FREECHECK")

                priceChapterText.visibility=View.INVISIBLE
                priceLinearText.visibility=View.INVISIBLE
            }
            if( i==R.id.vipStatus){
                Log.d("MyScreen","VIPCHECK")
                priceChapterText.visibility=View.VISIBLE
                priceLinearText.visibility=View.VISIBLE
            }

        }

    }

    private fun setupView() {
        if(imgsList.size==0){
            deleteAllBtn.visibility= View.INVISIBLE
            statusText.visibility=View.VISIBLE
            confirmAddChapterBtn.visibility=View.INVISIBLE
            radioGroup.visibility=View.INVISIBLE
            statusChapterText.visibility=View.INVISIBLE
            priceChapterText.visibility=View.INVISIBLE
            priceLinearText.visibility=View.INVISIBLE
        }else{
            deleteAllBtn.visibility= View.VISIBLE
            statusText.visibility=View.INVISIBLE
            confirmAddChapterBtn.visibility=View.VISIBLE
            radioGroup.visibility=View.VISIBLE
            statusChapterText.visibility=View.VISIBLE


        }
    }

    private fun pickChapterImage() {
        addChapterImage.setOnClickListener {
            startFileChooser(1111)
        }
        addChapterBackground.setOnClickListener {
            startFileChooser(1111)
        }
        addChapterText.setOnClickListener {
            startFileChooser(1111)
        }

    }

    fun startFileChooser( code:Int) {

        var i= Intent()
        i.setType("image/*")
        if(code==1111){
            Log.d("MYSCREEN","MUTI")
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        }
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(i,code)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Add function, if pic is successfully gotten from gallery
        if(requestCode==1111 &&resultCode== Activity.RESULT_OK&& data!=null){

            if(data.data!=null){
                Log.d("MYSCREEN",data.data.toString())
                imgsList.add(picItem(data.data!!))
                adapter.notifyItemInserted(imgsList.size)
            }
            if(data.clipData!=null){
                val count=  data.clipData?.itemCount
                Log.d("MYSCREEN",count.toString())

                for(i in 0 until count!!){
                    Log.d("MYSCREEN",data.clipData!!.getItemAt(i).uri.toString())

                    imgsList.add(picItem(data.clipData!!.getItemAt(i).uri))
                }
                adapter.notifyDataSetChanged()
                Log.d("MYSCREEN",count.toString())

            }
//            Log.d("MYSCREEN",data.data!!.toString())
//
//            var filepath=data.data!!
//            imgsList.add(picItem(filepath))
//            adapter.notifyItemInserted(imgsList.size)
            setupView()
        }

        //Change function, callback to adapter to change that item
        if(requestCode==2222 &&resultCode== Activity.RESULT_OK&& data!=null){
            adapter.OnActivityResult(data,positionClick)
            setupView()
        }
    }
    private fun setupRecycleView() {
        imgsList= ArrayList<picItem>()
        adapter = PickedChapterAdapter(this,imgsList)
        pickedChapterRV.adapter=adapter
        pickedChapterRV.layoutManager= GridLayoutManager(this,3)
    }
    private fun itemChapterClick() {
        var intent: Intent
        adapter.setOnItemClickListener(object: PickedChapterAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                positionClick=position
                startFileChooser(2222)
            }

            override fun onLongItemClick(position: Int) {
                if(imgsList[position].check){
                    imgsList[position].check=false
                    adapter.notifyItemChanged(position)
                }else{
                    imgsList[position].check=true
                    adapter.notifyItemChanged(position)
                }

            }
        })
    }

    override fun onBackPressed() {
        if( priceInputText.isFocused){
            priceInputText.clearFocus();
        }else {
            super.onBackPressed();
        }
    }

}