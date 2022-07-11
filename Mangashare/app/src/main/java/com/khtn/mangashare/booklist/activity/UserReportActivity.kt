package com.khtn.mangashare.booklist.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.adapter.PickedChapterAdapter
import com.khtn.mangashare.model.picItem
import kotlinx.android.synthetic.main.activity_add_comic.*
import kotlinx.android.synthetic.main.activity_pick_chapter.*
import kotlinx.android.synthetic.main.activity_user_report.*
import kotlinx.android.synthetic.main.activity_user_report.backPressUserReport
import kotlin.properties.Delegates

class UserReportActivity : AppCompatActivity() {
    lateinit var imgsList : ArrayList<picItem>
    lateinit var adapter: PickedChapterAdapter
    lateinit var deleteList : ArrayList<Int>
    var positionClick by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_report)

        setupRecycleView()
        setupView()
        itemChapterClick()

        backPressUserReport.setOnClickListener {
            finish()
        }
        userReportInput.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    userReportInput.clearFocus()
                }
                false
        })
        sendReportButton.setOnClickListener {
            finish()
        }
    }

    fun setupListener(){
        deleteAllReportBtn.setOnClickListener {
            imgsList.clear()
            imgsList.add(picItem(R.drawable.cover_manga))
            adapter.notifyDataSetChanged()
            setupView()
        }
        deleteCheckReport.setOnClickListener {
            for (i in imgsList.indices.reversed())
            {
                if (imgsList[i].check) {
                    imgsList.removeAt(i)
                }
            }
            deleteList.clear()
            adapter.notifyDataSetChanged()
            setupView()
        }
        deletePickReportButton.setOnClickListener {
            for( item in deleteList ){
                imgsList[item].check=false;
            }
            deleteList.clear()
            adapter.notifyDataSetChanged()
            setupView()
        }
    }
    private fun setupView() {


        if(deleteList.size==0){
            deletePickReportButton.visibility= View.INVISIBLE
            pickNumberReportText.visibility= View.INVISIBLE
            deleteCheckReport.visibility= View.INVISIBLE
        }else{

            deletePickReportButton.visibility= View.VISIBLE
            pickNumberReportText.visibility= View.VISIBLE
            deleteCheckReport.visibility= View.VISIBLE

        }
        if(imgsList.size==1){
            sendReportButton.visibility= View.INVISIBLE
            rootUserReport.removeView(deleteSectionRoot)

        }else{
            sendReportButton.visibility= View.VISIBLE
            val x: Int = rootUserReport.childCount
            Log.d("MyScreen", x.toString());
            if(x==5){
                rootUserReport.addView(deleteSectionRoot,2)
                setupListener()
            }
        }

        if(  imgsList.size>1 && deleteList.size==0 ){
            deleteAllReportBtn?.visibility= View.VISIBLE

        }else if( deleteList.size!=0){
            deleteAllReportBtn?.visibility= View.INVISIBLE

        }else if(imgsList.size==1 && deleteList.size==0){
            deleteAllReportBtn?.visibility= View.INVISIBLE

        }

        pickNumberReportText.text="Đã chọn ${deleteList.size}"


    }
    private fun setupRecycleView() {
        imgsList= ArrayList<picItem>()
        imgsList.add(picItem(R.drawable.cover_manga))
        adapter = PickedChapterAdapter(this,imgsList,"report")
        deleteList=ArrayList<Int>()
        ReportRV.adapter=adapter
        ReportRV.layoutManager= GridLayoutManager(this,3)
    }
    private fun itemChapterClick() {
        adapter.setOnItemClickListener(object: PickedChapterAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                if(position==0){
                    startFileChooser(1111)
                }else{
                    positionClick=position
                    startFileChooser(2222)
                }
            }

            override fun onLongItemClick(position: Int) {
                if(position!=0){
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
                    pickNumberReportText.text="Đã chọn ${deleteList.size}"

                    setupView()
                }
            }
        })
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
    override fun onBackPressed() {
        if( userReportInput.isFocused){
            userReportInput.clearFocus()
        }else {
            super.onBackPressed();
        }
    }
}