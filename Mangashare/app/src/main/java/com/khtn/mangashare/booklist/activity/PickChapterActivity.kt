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
import com.google.android.material.snackbar.Snackbar
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.adapter.PickedChapterAdapter
import com.khtn.mangashare.model.picItem
import kotlinx.android.synthetic.main.activity_pick_chapter.*
import kotlin.properties.Delegates


class PickChapterActivity : AppCompatActivity() {
    lateinit var imgsList : ArrayList<picItem>

    lateinit var deleteList : ArrayList<Int>
    var tempList:ArrayList<picItem> = ArrayList<picItem>()
    lateinit var adapter: PickedChapterAdapter
    var positionClick by Delegates.notNull<Int>()
    lateinit var mode: String
    lateinit var view :View
    lateinit var positionChapter:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_chapter)
        view=findViewById<View>(R.id.rootChapterAdd)

        mode= intent.getStringExtra("mode").toString()
        positionChapter=intent.getStringExtra("positionChapter").toString()
        positionClick=0
        //If update

        setupRecycleView()
        if(mode=="add"){
            icon_deleteChapter_btn.visibility=View.INVISIBLE
        }else{
            titlePickChapterText.text="Sửa chương truyện"
            imgsList.add(picItem(R.drawable.cover_manga))
            imgsList.add(picItem(R.drawable.cover_manga))
            imgsList.add(picItem(R.drawable.cover_manga))
            imgsList.add(picItem(R.drawable.cover_manga))
            priceChapterText.visibility=View.INVISIBLE
            priceLinearText.visibility=View.INVISIBLE
        }

        setupView()
        pickChapterImage()

        itemChapterClick()
        backPressAddChapter.setOnClickListener {
            finish()
        }
        icon_deleteChapter_btn.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("status", "delete")
            replyIntent.putExtra("positionChapter", positionChapter)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

        deleteAllBtn.setOnClickListener {
            tempList= ArrayList(imgsList)
            imgsList.clear()
            var snackbar = Snackbar.make(view, "Đã xóa tất cả hình", Snackbar.LENGTH_LONG)
            snackbar.show()
            snackbar.setAction("Undo delete", View.OnClickListener() {
                imgsList.clear()
                imgsList.addAll(tempList)
                //imgsList.addAll(tempList)
                Log.d("MyScreen",imgsList.size.toString())
                tempList.clear()
                adapter.SetChange()
                setupView()

            })
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
            replyIntent.putExtra("status", mode)
            if(mode!="add") {
                replyIntent.putExtra("positionChapter", positionChapter)
            }
            setResult(Activity.RESULT_OK, replyIntent)
            finish()

        }

        deleteCheck.setOnClickListener {

            tempList= ArrayList(imgsList)
            for (i in imgsList.indices.reversed())
            {
                if (imgsList[i].check) {
                    imgsList.removeAt(i)
                }
            }


            var snackbar = Snackbar.make(view, "Đã xóa ${deleteList.size} hình", Snackbar.LENGTH_LONG)
            snackbar.show()
            snackbar.setAction("Undo delete", View.OnClickListener() {
                imgsList.clear()
                for(item in tempList){
                    item.check=false
                    imgsList.add(item)
                }
                //imgsList.addAll(tempList)
                Log.d("MyScreen",imgsList.size.toString())
                tempList.clear()
                adapter.SetChange()
                setupView()

            })
            deleteList.clear()
            pickNumberText.text="Đã chọn ${deleteList.size}"

            adapter.notifyDataSetChanged()
            setupView()

        }

        deletePickButton.setOnClickListener {

            for( item in deleteList ){
                imgsList[item].check=false;
            }
            deleteList.clear()
            adapter.notifyDataSetChanged()

            pickNumberText.text="Đã chọn ${deleteList.size}"
            setupView()
        }
    }

    private fun radioCheck() {
        radioGroup.setOnCheckedChangeListener { group, i ->
            if(i== R.id.freeStatus){

                priceChapterText.visibility=View.INVISIBLE
                priceLinearText.visibility=View.INVISIBLE
            }
            if( i==R.id.vipStatus){
                priceChapterText.visibility=View.VISIBLE
                priceLinearText.visibility=View.VISIBLE
            }

        }

    }

    private fun setupView() {


        if(deleteList.size==0){
            deletePickButton.visibility= View.INVISIBLE
            pickNumberText.visibility= View.INVISIBLE
            deleteCheck.visibility= View.INVISIBLE
        }else{
            deletePickButton.visibility= View.VISIBLE
            pickNumberText.visibility= View.VISIBLE
            deleteCheck.visibility= View.VISIBLE
        }
        if(imgsList.size==0){
            statusText.visibility=View.VISIBLE
            confirmAddChapterBtn.visibility=View.INVISIBLE
            radioGroup.visibility=View.INVISIBLE
            statusChapterText.visibility=View.INVISIBLE
            priceChapterText.visibility=View.INVISIBLE
            priceLinearText.visibility=View.INVISIBLE
        }else{
            statusText.visibility=View.INVISIBLE
            confirmAddChapterBtn.visibility=View.VISIBLE
            radioGroup.visibility=View.VISIBLE
            statusChapterText.visibility=View.VISIBLE
        }

        if(  imgsList.size!=0 && deleteList.size==0 ){
            deleteAllBtn.visibility= View.VISIBLE

        }else if( deleteList.size!=0){
            deleteAllBtn.visibility= View.INVISIBLE

        }else if(imgsList.size==0 && deleteList.size==0){
            deleteAllBtn.visibility= View.INVISIBLE

        }

        pickNumberText.text="Đã chọn ${deleteList.size}"


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
                imgsList.add(picItem(data.data!!))
                adapter.notifyItemInserted(imgsList.size)
            }
            if(data.clipData!=null){
                val count=  data.clipData?.itemCount

                for(i in 0 until count!!){

                    imgsList.add(picItem(data.clipData!!.getItemAt(i).uri))
                }
                adapter.notifyDataSetChanged()

            }
            setupView()
        }

        //Change function, callback to adapter to change that item
        if(requestCode==2222 &&resultCode== Activity.RESULT_OK&& data!=null){
            adapter.OnActivityResult(data,positionClick)
            if(deleteList.size>1){
                deleteList.removeAt(0)
            }
            setupView()
        }
    }
    private fun setupRecycleView() {
        imgsList= ArrayList<picItem>()
        deleteList=ArrayList<Int>()
        adapter = PickedChapterAdapter(this,imgsList,"chapter")
        pickedChapterRV.adapter=adapter
        pickedChapterRV.layoutManager= GridLayoutManager(this,3)
    }
    private fun itemChapterClick() {
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

                if(deleteList.contains(position)){
                    deleteList.remove(position)
                }else{
                    deleteList.add(position)
                }
                pickNumberText.text="Đã chọn ${deleteList.size}"

                setupView()
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