package com.khtn.mangashare.booklist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.adapter.BookListAdapter
import com.khtn.mangashare.comicDetail.ViewComicDetailActivity
import com.khtn.mangashare.model.comicItem

class ViewBookListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_book_list)
        init()
    }

    private fun init() {
        val intent = intent
        val title = intent.getStringExtra("title")
        var comicList = initComicItem()

        val tb = findViewById<Toolbar>(R.id.bookListTB)
        tb.title = title
        tb.setNavigationOnClickListener {
            finish()
        }

        val rc = findViewById<RecyclerView>(R.id.bookListRC)
        val adapter = BookListAdapter(this, comicList)
        rc.setHasFixedSize(true);
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adapter
        adapter.onItemClick= {item ->
            val intent = Intent(this, ViewComicDetailActivity::class.java)
            startActivity(intent)
        }
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
                R.drawable.manga_naruto,
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
                "One piece",
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
                "One piece",
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
                "One piece",
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
                "One piece",
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
                "Doraemon",
                R.drawable.manga_naruto,
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