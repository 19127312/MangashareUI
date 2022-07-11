package com.khtn.mangashare.categoryList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.activity.ViewBookListActivity
import com.khtn.mangashare.model.categoryItem
import com.khtn.mangashare.model.comicItem

class ViewCategoryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)
        init()
    }

    var categoryList = arrayListOf<categoryItem>()
    private fun init() {
        categoryList.add(categoryItem("Vui nhộn", R.color.blue_100, R.drawable.ic_reddit))
        categoryList.add(categoryItem("Phiêu lưu", R.color.green_100, R.drawable.ic_adventure))
        categoryList.add(categoryItem("Thể thao", R.color.yellow, R.drawable.ic_dribbble))
        categoryList.add(categoryItem("Tình cảm", R.color.pink_100, R.drawable.ic_heart))
        categoryList.add(categoryItem("Thiếu nhi", R.color.orange, R.drawable.ic_present))
        categoryList.add(categoryItem("Hành động", R.color.blue_2, R.drawable.ic_telegram))
        categoryList.add(categoryItem("Hiện đại", R.color.green_3, R.drawable.ic_camera))
        categoryList.add(categoryItem("Lịch sử", R.color.brown, R.drawable.ic_history))

        var recyclerView = findViewById<RecyclerView>(R.id.categoryListRC)!!
        var tb = findViewById<Toolbar>(R.id.categoryDetailTB)
        tb.setNavigationOnClickListener {
            finish()
        }
        recyclerView.setHasFixedSize(true);

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val adapter = CategoryListAdapter(this, categoryList)

        adapter?.onButtonClick = { tmp ->
            val intent = Intent(this, ViewBookListActivity::class.java)
            intent.putExtra("title", tmp.name)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }


}