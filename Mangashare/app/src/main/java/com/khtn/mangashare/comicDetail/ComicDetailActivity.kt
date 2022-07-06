package com.khtn.mangashare.comicDetail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.khtn.mangashare.R
import com.khtn.mangashare.chapterDetail.ChapterDetailActivity
import com.khtn.mangashare.home.fragment.ViewPagerRankingAdapter
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem

class ComicDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_detail)

        comic = initItem()
        initViewPager(comic)
        init(comic)

    }

    lateinit var adapter: ViewPagerComicDetailAdapter
    lateinit var comic : comicItem

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            var tmp = data?.getSerializableExtra("comic") as comicItem
            comic = tmp
            initViewPager(comic)
            adapter.notifyDataSetChanged()
        }
    }

    private fun init(comic: comicItem) {
        val tb = findViewById<Toolbar>(R.id.comicDetailTB)
        val author = findViewById<TextView>(R.id.authorDetailTV)
        val category = findViewById<TextView>(R.id.categoryDetailTV)
        val continuteRead = findViewById<LinearLayout>(R.id.continueReadComicDetailLL)
        val startRead = findViewById<Button>(R.id.startReadBTN)

        continuteRead.setOnClickListener {
            val intent = Intent(this, ChapterDetailActivity::class.java)
            intent.putExtra("comic", comic)
            var index = 0
            for (i in 0..comic.chapter.size) {
                if (comic.chapter[i].bookmark == true) {
                    index = i
                    break
                }
            }
            intent.putExtra("chapterNumber", index)
            startActivityForResult(intent, 111)
        }

        startRead.setOnClickListener {
            val intent = Intent(this, ChapterDetailActivity::class.java)
            intent.putExtra("comic", comic)
            intent.putExtra("chapterNumber", 0)
            startActivityForResult(intent, 112)
        }

        tb.title = comic.name
        author.setText(comic.author)
        if (comic.category.size > 0) {
            category.setText(comic.category[0])
        }
        tb.setNavigationOnClickListener { finish() }
    }

    private fun initViewPager(comic: comicItem) {

        val tabLayout = findViewById<TabLayout>(R.id.detailTL)
        val viewPager = findViewById<ViewPager2>(R.id.comicDetailVP)
        adapter = ViewPagerComicDetailAdapter(
            supportFragmentManager, lifecycle,
            comic
        )
        viewPager?.adapter = adapter

        viewPager?.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager!!) { tab, position ->
            when (position) {
                0 -> tab.text = "Mô tả truyện"
                1 -> tab.text = "Chương truyện"
            }
        }.attach()
    }

    private fun initItem(): comicItem {
        val imageList = arrayListOf<Int>()
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)

        val imageList2 = arrayListOf<Int>()
        imageList2.add(R.drawable.manga_cover)
        imageList2.add(R.drawable.manga_cover)
        imageList2.add(R.drawable.manga_cover)
        imageList2.add(R.drawable.manga_cover)
        imageList2.add(R.drawable.manga_cover)
        imageList2.add(R.drawable.manga_cover)

        val chapterList = arrayListOf<chapterItem>()
        chapterList.add(chapterItem(1, "20/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(2, "21/05/2022", 100, false, 200, imageList2))
        chapterList.add(chapterItem(3, "22/05/2022", 220, false, 200, imageList2))
        chapterList.add(chapterItem(4, "23/05/2022", 0, false, 200, imageList2))
        chapterList.add(chapterItem(5, "24/05/2022", 0, true, 200, imageList2))
        chapterList.add(chapterItem(6, "25/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(7, "26/05/2022", 100, false, 200, imageList))
        chapterList.add(chapterItem(8, "27/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(9, "21/05/2022", 100, false, 200, imageList2))
        chapterList.add(chapterItem(10, "22/05/2022", 220, false, 200, imageList))
        chapterList.add(chapterItem(11, "23/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(12, "24/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(13, "25/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(14, "26/05/2022", 100, false, 200, imageList))
        chapterList.add(chapterItem(15, "10/07/2022", 0, false, 200, imageList))

        val category = arrayListOf<String>()
        category.add("Phiêu lưu")
        category.add("Hành động")
        category.add("Hài hước")
        category.add("Phiêu lưu")
        val des =
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
        val comic =
            comicItem(
                "Naruto",
                R.drawable.cover_manga,
                "Aoyama Gosho",
                100,
                23,
                15,
                56,
                34,
                des,
                false,
                category, chapterList
            )
        return comic
    }
}