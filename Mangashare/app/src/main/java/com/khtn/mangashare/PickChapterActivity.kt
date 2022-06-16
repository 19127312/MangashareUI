package com.khtn.mangashare

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.khtn.mangashare.adapter.PickedChapterAdapter
import com.khtn.mangashare.model.picItem
import kotlinx.android.synthetic.main.activity_pick_chapter.*
import kotlin.Int
import kotlin.properties.Delegates
import kotlin.toString

class PickChapterActivity : AppCompatActivity() {
    lateinit var imgsList : ArrayList<picItem>
    lateinit var adapter: PickedChapterAdapter
    var positionClick by Delegates.notNull<Int>()
    var isUpdate=false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_chapter)
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->

            isReadPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted

        }

        requestPermission()
        isUpdate=intent.getBooleanExtra("isUpdate",false)
        positionClick=0
        //If update
        if(isUpdate){
            //TODO
        }
        setupRecycleView()
        openGalleryBT.setOnClickListener {
            startFileChooser(1111)
        }
        itemChapterClick()

    }
    private fun requestPermission(){

        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED


        val permissionRequest : MutableList<String> = ArrayList()

        if (!isReadPermissionGranted){

            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)

        }

        if (permissionRequest.isNotEmpty()){

            permissionLauncher.launch(permissionRequest.toTypedArray())
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
        startActivityForResult(Intent.createChooser(i,"Chọn hình"),code)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Add function, if pic is successfully gotten from gallery
        if(requestCode==1111 &&resultCode== Activity.RESULT_OK&& data!=null){
            Log.d("MYSCREEN", data.clipData?.itemCount.toString())
//            var filepath=data.data!!
//            imgsList.add(picItem(filepath))
//            adapter.notifyItemInserted(imgsList.size)
        }

        //Change function, callback to adapter to change that item
        if(requestCode==2222 &&resultCode== Activity.RESULT_OK&& data!=null){
            adapter.OnActivityResult(data,positionClick)
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
        })
    }

}