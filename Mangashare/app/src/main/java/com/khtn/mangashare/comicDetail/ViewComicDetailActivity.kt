package com.khtn.mangashare.comicDetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ShareCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.khtn.mangashare.R
import com.khtn.mangashare.chapterDetail.ViewChapterDetailActivity
import com.khtn.mangashare.comment.ViewCommentActivity
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem
import com.khtn.mangashare.model.commentItem
import com.khtn.mangashare.model.ratingItem

class ViewComicDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_detail)
        comic = initItem()
        initViewPager(comic)
        init(comic)

    }

    lateinit var adapter: ViewPagerComicDetailAdapter
    lateinit var comic: comicItem
    lateinit var comicName: String
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
        val comment = findViewById<LinearLayout>(R.id.commentComicDetailLL)
        val share = findViewById<ImageView>(R.id.shareComicDetailIM)
        val imageComic = findViewById<ImageView>(R.id.imageComicIV)

        imageComic.setImageResource(comic.cover)
        continuteRead.setOnClickListener {
            val intent = Intent(this, ViewChapterDetailActivity::class.java)
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
            val intent = Intent(this, ViewChapterDetailActivity::class.java)
            intent.putExtra("comic", comic)
            intent.putExtra("chapterNumber", 0)
            startActivityForResult(intent, 112)
        }

        comment.setOnClickListener {
            val intent = Intent(this, ViewCommentActivity::class.java)
            intent.putExtra("comic", comic)
            intent.putExtra("chapterNumber", -1)
            startActivity(intent)
        }

        share.setOnClickListener {
            val txt: String? = comic.name
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Choose your apps")
                .setText(txt)
                .startChooser()
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
                0 -> tab.text = "M?? t??? truy???n"
                1 -> tab.text = "Ch????ng truy???n"
            }
        }.attach()
    }

    private fun initItem(): comicItem {
        val intent = intent
        comicName = intent.getStringExtra("comicName").toString()
        Log.d("MyScreen", comicName)
        val imageList = arrayListOf<Int>()
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)

        val imageList2 = arrayListOf<Int>()
        imageList2.add(R.drawable.manga_naruto)
        imageList2.add(R.drawable.manga_naruto)
        imageList2.add(R.drawable.manga_naruto)
        imageList2.add(R.drawable.manga_naruto)
        imageList2.add(R.drawable.manga_naruto)
        imageList2.add(R.drawable.manga_naruto)

        val chapterList = arrayListOf<chapterItem>()
        chapterList.add(chapterItem(1, "20/05/2022", 0, false, 200, imageList2))
        chapterList.add(chapterItem(2, "21/05/2022", 100, false, 200, imageList2))
        chapterList.add(chapterItem(3, "22/05/2022", 220, false, 200, imageList2))
        chapterList.add(chapterItem(4, "23/05/2022", 0, false, 200, imageList2))
        chapterList.add(chapterItem(5, "24/05/2022", 0, true, 200, imageList2))
        chapterList.add(chapterItem(6, "25/05/2022", 0, false, 200, imageList2))
        chapterList.add(chapterItem(7, "26/05/2022", 100, false, 200, imageList2))
        chapterList.add(chapterItem(8, "27/05/2022", 0, false, 200, imageList2))
        chapterList.add(chapterItem(9, "21/05/2022", 100, false, 200, imageList2))
        chapterList.add(chapterItem(10, "22/05/2022", 220, false, 200, imageList2))
        chapterList.add(chapterItem(11, "23/05/2022", 0, false, 200, imageList2))
        chapterList.add(chapterItem(12, "24/05/2022", 0, false, 200, imageList2))
        chapterList.add(chapterItem(13, "25/05/2022", 0, false, 200, imageList2))
        chapterList.add(chapterItem(14, "26/05/2022", 100, false, 200, imageList2))
        chapterList.add(chapterItem(15, "10/07/2022", 0, false, 200, imageList2))

        val commentList = arrayListOf<commentItem>()
        commentList.add(commentItem("Nguy???n V??n A", 1, "20/07/2022", "Truy???n hay qu??. Art ?????p"))
        commentList.add(commentItem("Nguy???n V??n B", 2, "20/07/2022", "Truy???n hay qu??. N??n ?????c"))
        commentList.add(commentItem("Nguy???n V??n C", -1, "20/07/2022", "Truy???n hay"))
        commentList.add(commentItem("Nguy???n ?????c ?????t", 11, "20/07/2022", "Truy???n hay qu??. Art ?????p"))
        commentList.add(
            commentItem(
                "Ng?? Nguy???n Ki???t T?????ng",
                12,
                "20/07/2022",
                "Truy???n hay qu??. Art ?????p"
            )
        )
        commentList.add(commentItem("????o Duy An", -1, "20/07/2022", "Truy???n hay qu??. Art ?????p"))
        commentList.add(commentItem("T??? C??ng ??i???n", 15, "20/07/2022", "Truy???n hay qu??. Art ?????p"))
        commentList.add(commentItem("Nguy???n V??n A", 9, "20/07/2022", "Truy???n hay qu??. Art ?????p"))
        commentList.add(commentItem("Nguy???n V??n A", 10, "20/07/2022", "..."))
        commentList.add(commentItem("Nguy???n V??n A", 8, "20/07/2022", "Truy???n hay qu??. Art ?????p"))
        commentList.add(commentItem("Nguy???n V??n A", 4, "20/07/2022", "Truy???n hay qu??. Art ?????p"))
        commentList.add(commentItem("Nguy???n V??n A", 5, "20/07/2022", "Truy???n hay qu??. Art ?????p"))
        commentList.add(commentItem("Nguy???n V??n D", 1, "20/07/2022", "Truy???n hay qu??. Art ?????p"))

        val ratingList = arrayListOf<ratingItem>()
        ratingList.add(ratingItem("????o Duy An", 3, "22/07/2022", "Truy???n hay"))
        ratingList.add(
            ratingItem(
                "Ng?? Nguy???n Ki???t T?????ng",
                0,
                "21/07/2022",
                "Truy???n kh??ng ????ng ?????c, t??nh ti???t d??? t???, art x???u"
            )
        )
        ratingList.add(ratingItem("Nguy???n ?????c ?????t", 2, "21/07/2022", "..."))
        ratingList.add(
            ratingItem(
                "T??? C??ng ??i???n",
                2,
                "21/07/2022",
                "Truy???n b??nh th?????ng kh??ng c?? g?? ?????c s???c"
            )
        )
        ratingList.add(ratingItem("Nguy???n V??n A", 4, "20/07/2022", "Truy???n hay"))
        ratingList.add(ratingItem("Nguy???n V??n B", 1, "19/07/2022", "Truy???n kh??ng hay"))
        ratingList.add(ratingItem("Nguy???n V??n C", 3, "10/07/2022", "Truy???n hay"))
        ratingList.add(ratingItem("Nguy???n V??n D", 5, "09/07/2022", "Truy???n hay"))
        ratingList.add(ratingItem("????o Duy Anh", 5, "02/07/2022", "Truy???n hay c??c b???n n??n ?????c"))

        var image: Int = R.drawable.cover_manga
        Log.i("testimage", comicName)

        var author = "Ausi"

        val category = arrayListOf<String>()
        category.add("Phi??u l??u")
        category.add("H??nh ?????ng")
        category.add("H??i h?????c")

        when (comicName) {
            "Naruto" -> {
                image = R.drawable.banner_naruto
                author = "Kishimoto Masashi"
            }
            "Conan" -> {
                image = R.drawable.banner_conan
                author = "Aoyama Gosho"
                category.add(0,"Trinh th??m")
            }
            "Bleach" -> {
                image = R.drawable.banner_bleach
                author = "Kubo Taito"
            }
            "One Piece" -> {
                image = R.drawable.banner_onepiece
                author = "Oda Eiichiro"
            }
            "Doraemon" -> {
                image = R.drawable.banner_doraemon
                author = "Fujiko Fujio"
                category.add(0,"Thi???u nhi")
            }
            "Dragon Ball" -> {
                image = R.drawable.banner_dragonball
                author = "Toriyama Akira"
            }
            "Hunter x Hunter" -> {
                image = R.drawable.banner_hunter
                author = "Togashi Yoshihiro"
            }
        }

        val des =
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"

        val comic =
            comicItem(
                comicName,
                image,
                author,
                100,
                23,
                15,
                56,
                34,
                des,
                false,
                category, chapterList, commentList, ratingList
            )
        return comic
    }
}