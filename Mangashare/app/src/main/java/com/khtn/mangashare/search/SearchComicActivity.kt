package com.khtn.mangashare.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.adapter.BookListAdapter
import com.khtn.mangashare.comicDetail.ViewComicDetailActivity
import com.khtn.mangashare.model.comicItem


class SearchComicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_comic)

        init()
        initRecyclerView()
    }

    var comicList = arrayListOf<comicItem>()
    lateinit var inputSearchText : EditText
    private fun init() {
        inputSearchText = findViewById(R.id.inputSearchTV)
        val searchIM = findViewById<ImageView>(R.id.searchComicIM)
        val clearIM = findViewById<ImageView>(R.id.clearSeachComicIM)
        val length = findViewById<TextView>(R.id.lengthSearchTV)
        val lastUpdate = findViewById<TextView>(R.id.lastUpdateTV)
        val content = findViewById<TextView>(R.id.contentSearchTV)
        val tb = findViewById<Toolbar>(R.id.comicSearchTB)

        if (inputSearchText.text.length == 0) {
            clearIM.setVisibility(View.INVISIBLE)
        }

        inputSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (inputSearchText.text.length == 0) {
                    clearIM.setVisibility(View.INVISIBLE)
                } else {
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

        length.setOnClickListener {
            showLengthBottomSheetDialog()
        }
        lastUpdate.setOnClickListener {
            showTimeBottomSheetDialog()
        }
        content.setOnClickListener {
            showContentBottomSheetDialog()
        }
        tb.setNavigationOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {
        comicList = initComicItem()
        val rc = findViewById<RecyclerView>(R.id.searchComicRC)
        var adapter = BookListAdapter(this, comicList)
        rc.setHasFixedSize(true);
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adapter
        adapter.onItemClick = { item ->
            val intent = Intent(this, ViewComicDetailActivity::class.java)
            startActivity(intent)
        }

        inputSearchText.setOnEditorActionListener { v, actionId, event ->
            Log.i("testEnter","0")
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                Log.i("testEnter","1")
                if(inputSearchText.text.toString().length != 0){
                    Log.i("testEnter","2")
                    comicList.forEach {
                        var temp = inputSearchText.text.toString().lowercase()
                        if(!it.name!!.lowercase().contains(temp)){
                            comicList.remove(it)
                        }
                    }
                    Log.i("testEnter",comicList.size.toString())
                    adapter.notifyDataSetChanged()
                }
                true
            } else {
                false
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun showLengthBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.fragment_bottom_sheet_length_search)

        val cancelLengthBottomSheetTV =
            bottomSheetDialog.findViewById<TextView>(R.id.cancelLengthBottomSheetTV)
        val confirmLengthBottomSheetBTN =
            bottomSheetDialog.findViewById<Button>(R.id.confirmLengthBottomSheetBTN)
        val allLengthCL = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.allLengthCL)
        val oneLengthCL = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.oneLengthCL)
        val twoLengthCL = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.twoLengthCL)
        val threeLengthCL = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.threeLengthCL)
        val fourLengthCL = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.fourLengthCL)
        val allLengthCheckedIM =
            bottomSheetDialog.findViewById<ImageView>(R.id.allLengthCheckedIM)
        val oneLengthCheckedIM =
            bottomSheetDialog.findViewById<ImageView>(R.id.oneLengthCheckedIM)
        val twoLengthCheckedIM =
            bottomSheetDialog.findViewById<ImageView>(R.id.twoLengthCheckedIM)
        val threeLengthCheckedIM =
            bottomSheetDialog.findViewById<ImageView>(R.id.threeLengthCheckedIM)
        val fourLengthCheckedIM =
            bottomSheetDialog.findViewById<ImageView>(R.id.fourLengthCheckedIM)

        allLengthCheckedIM!!.setVisibility(View.VISIBLE)
        oneLengthCheckedIM!!.setVisibility(View.INVISIBLE)
        twoLengthCheckedIM!!.setVisibility(View.INVISIBLE)
        threeLengthCheckedIM!!.setVisibility(View.INVISIBLE)
        fourLengthCheckedIM!!.setVisibility(View.INVISIBLE)

        allLengthCL!!.setOnClickListener {
            allLengthCL.setBackgroundColor(R.color.green_5)
            oneLengthCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            twoLengthCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            threeLengthCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            fourLengthCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))

            allLengthCheckedIM.setVisibility(View.VISIBLE)
            oneLengthCheckedIM.setVisibility(View.INVISIBLE)
            twoLengthCheckedIM.setVisibility(View.INVISIBLE)
            threeLengthCheckedIM.setVisibility(View.INVISIBLE)
            fourLengthCheckedIM.setVisibility(View.INVISIBLE)
        }

        oneLengthCL!!.setOnClickListener {
            allLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            oneLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.green_5)))
            twoLengthCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            threeLengthCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            fourLengthCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))

            allLengthCheckedIM.setVisibility(View.INVISIBLE)
            oneLengthCheckedIM.setVisibility(View.VISIBLE)
            twoLengthCheckedIM.setVisibility(View.INVISIBLE)
            threeLengthCheckedIM.setVisibility(View.INVISIBLE)
            fourLengthCheckedIM.setVisibility(View.INVISIBLE)
        }

        twoLengthCL!!.setOnClickListener {
            allLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            oneLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            twoLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.green_5)))
            threeLengthCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            fourLengthCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))

            allLengthCheckedIM.setVisibility(View.INVISIBLE)
            oneLengthCheckedIM.setVisibility(View.INVISIBLE)
            twoLengthCheckedIM.setVisibility(View.VISIBLE)
            threeLengthCheckedIM.setVisibility(View.INVISIBLE)
            fourLengthCheckedIM.setVisibility(View.INVISIBLE)
        }

        threeLengthCL!!.setOnClickListener {
            allLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            oneLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            twoLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            threeLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.green_5)))
            fourLengthCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))

            allLengthCheckedIM.setVisibility(View.INVISIBLE)
            oneLengthCheckedIM.setVisibility(View.INVISIBLE)
            twoLengthCheckedIM.setVisibility(View.INVISIBLE)
            threeLengthCheckedIM.setVisibility(View.VISIBLE)
            fourLengthCheckedIM.setVisibility(View.INVISIBLE)
        }

        fourLengthCL!!.setOnClickListener {
            allLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            oneLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            twoLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            threeLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            fourLengthCL.setBackground((ContextCompat.getDrawable(this, R.color.green_5)))

            allLengthCheckedIM.setVisibility(View.INVISIBLE)
            oneLengthCheckedIM.setVisibility(View.INVISIBLE)
            twoLengthCheckedIM.setVisibility(View.INVISIBLE)
            threeLengthCheckedIM.setVisibility(View.INVISIBLE)
            fourLengthCheckedIM.setVisibility(View.VISIBLE)
        }

        cancelLengthBottomSheetTV!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        confirmLengthBottomSheetBTN!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    @SuppressLint("ResourceAsColor")
    private fun showTimeBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.fragment_bottom_sheet_time_search)

        val cancelTimeBottomSheetTV =
            bottomSheetDialog.findViewById<TextView>(R.id.cancelTimeBottomSheetTV)
        val comfirmTimeBottomSheetBTN =
            bottomSheetDialog.findViewById<Button>(R.id.confirmTimeBottomSheetBTN)
        val allTimeCL = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.allTimeCL)
        val oneTimeCL = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.oneTimeCL)
        val twoTimeCL = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.twoTimeCL)
        val threeTimeCL = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.threeTimeCL)
        val fourTimeCL = bottomSheetDialog.findViewById<ConstraintLayout>(R.id.fourTimeCL)
        val allTimeCheckedIM =
            bottomSheetDialog.findViewById<ImageView>(R.id.allTimeCheckedIM)
        val oneTimeCheckedIM =
            bottomSheetDialog.findViewById<ImageView>(R.id.oneTimeCheckedIM)
        val twoTimeCheckedIM =
            bottomSheetDialog.findViewById<ImageView>(R.id.twoTimeCheckedIM)
        val threeTimeCheckedIM =
            bottomSheetDialog.findViewById<ImageView>(R.id.threeTimeCheckedIM)
        val fourTimeCheckedIM =
            bottomSheetDialog.findViewById<ImageView>(R.id.fourTimeCheckedIM)

        allTimeCheckedIM!!.setVisibility(View.VISIBLE)
        oneTimeCheckedIM!!.setVisibility(View.INVISIBLE)
        twoTimeCheckedIM!!.setVisibility(View.INVISIBLE)
        threeTimeCheckedIM!!.setVisibility(View.INVISIBLE)
        fourTimeCheckedIM!!.setVisibility(View.INVISIBLE)

        allTimeCL!!.setOnClickListener {
            allTimeCL.setBackgroundColor(R.color.green_5)
            oneTimeCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            twoTimeCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            threeTimeCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            fourTimeCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))

            allTimeCheckedIM.setVisibility(View.VISIBLE)
            oneTimeCheckedIM.setVisibility(View.INVISIBLE)
            twoTimeCheckedIM.setVisibility(View.INVISIBLE)
            threeTimeCheckedIM.setVisibility(View.INVISIBLE)
            fourTimeCheckedIM.setVisibility(View.INVISIBLE)
        }

        oneTimeCL!!.setOnClickListener {
            allTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            oneTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.green_5)))
            twoTimeCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            threeTimeCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            fourTimeCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))

            allTimeCheckedIM.setVisibility(View.INVISIBLE)
            oneTimeCheckedIM.setVisibility(View.VISIBLE)
            twoTimeCheckedIM.setVisibility(View.INVISIBLE)
            threeTimeCheckedIM.setVisibility(View.INVISIBLE)
            fourTimeCheckedIM.setVisibility(View.INVISIBLE)
        }

        twoTimeCL!!.setOnClickListener {
            allTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            oneTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            twoTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.green_5)))
            threeTimeCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            fourTimeCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))

            allTimeCheckedIM.setVisibility(View.INVISIBLE)
            oneTimeCheckedIM.setVisibility(View.INVISIBLE)
            twoTimeCheckedIM.setVisibility(View.VISIBLE)
            threeTimeCheckedIM.setVisibility(View.INVISIBLE)
            fourTimeCheckedIM.setVisibility(View.INVISIBLE)
        }

        threeTimeCL!!.setOnClickListener {
            allTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            oneTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            twoTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            threeTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.green_5)))
            fourTimeCL!!.setBackground((ContextCompat.getDrawable(this, R.color.white)))

            allTimeCheckedIM.setVisibility(View.INVISIBLE)
            oneTimeCheckedIM.setVisibility(View.INVISIBLE)
            twoTimeCheckedIM.setVisibility(View.INVISIBLE)
            threeTimeCheckedIM.setVisibility(View.VISIBLE)
            fourTimeCheckedIM.setVisibility(View.INVISIBLE)
        }

        fourTimeCL!!.setOnClickListener {
            allTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            oneTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            twoTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            threeTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.white)))
            fourTimeCL.setBackground((ContextCompat.getDrawable(this, R.color.green_5)))

            allTimeCheckedIM.setVisibility(View.INVISIBLE)
            oneTimeCheckedIM.setVisibility(View.INVISIBLE)
            twoTimeCheckedIM.setVisibility(View.INVISIBLE)
            threeTimeCheckedIM.setVisibility(View.INVISIBLE)
            fourTimeCheckedIM.setVisibility(View.VISIBLE)
        }

        cancelTimeBottomSheetTV!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        comfirmTimeBottomSheetBTN!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun showContentBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.fragment_bottom_sheet_content_search)

        val cancelTimeBottomSheetTV =
            bottomSheetDialog.findViewById<TextView>(R.id.cancelContentBottomSheetTV)
        val comfirmTimeBottomSheetBTN =
            bottomSheetDialog.findViewById<Button>(R.id.confirmContentBottomSheetBTN)

        cancelTimeBottomSheetTV!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        comfirmTimeBottomSheetBTN!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun initComicItem(): ArrayList<comicItem> {
        val rs = arrayListOf<comicItem>()
        val description =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tincidunt tellus sed nulla auctor egestas. "
        val category = arrayListOf<String>()
        category.add("Phiêu lưu")
        category.add("Hành động")
        category.add("Hài hước")
        category.add("Phiêu lưu")
        category.add(description)

        rs.add(
            comicItem(
                "Naruto",
                R.drawable.manga_naruto,
                100,
                201,
                501,
                description,
                true,
                category
            )
        )
        rs.add(
            comicItem(
                "Conan",
                R.drawable.manga_conan,
                100,
                201,
                501,
                description,
                false,
                category
            )
        )
        rs.add(
            comicItem(
                "One piece",
                R.drawable.manga_onepiece,
                100,
                201,
                501,
                description,
                true,
                category
            )
        )
        rs.add(
            comicItem(
                "Bleach",
                R.drawable.manga_bleach,
                100,
                201,
                501,
                description,
                true,
                category
            )
        )
        rs.add(
            comicItem(
                "Doraemon",
                R.drawable.manga_doraemon,
                100,
                201,
                501,
                description,
                true,
                category
            )
        )
        rs.add(
            comicItem(
                "Hunter x Hunter",
                R.drawable.manga_hunter,
                100,
                201,
                501,
                description,
                true,
                category
            )
        )
        rs.add(
            comicItem(
                "Dragon Ball",
                R.drawable.manga_dragonball,
                100,
                201,
                501,
                description,
                true,
                category
            )
        )
        return rs
    }
}