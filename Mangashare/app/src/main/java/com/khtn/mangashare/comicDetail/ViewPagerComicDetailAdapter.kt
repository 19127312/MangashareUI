package com.khtn.mangashare.comicDetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.SuggestComicAdapter
import com.khtn.mangashare.chapterDetail.ViewChapterDetailActivity
import com.khtn.mangashare.model.comicItem
import com.khtn.mangashare.model.ratingItem
import com.ms.square.android.expandabletextview.ExpandableTextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ViewPagerComicDetailAdapter : FragmentStateAdapter {
    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        comic: comicItem
    ) : super(fragmentManager, lifecycle) {
        this.comic = comic
    }

    constructor(
        fragment: Fragment,
        comic: comicItem
    ) : super(fragment) {
        this.comic = comic
    }


    private var comic: comicItem
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailComicFragment(comic)
            else -> ChapterListFragment(comic)
        }
    }
}

class ChapterListFragment(private var comic: comicItem) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_comic_chapter_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    lateinit var adapter: ChapterRecyclerViewAdapter
    var items = arrayListOf<ChapterRecyclerViewItem>()
    var checkSort: Boolean = true
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            var tmp = data?.getSerializableExtra("comic") as comicItem
            var num = data?.getIntExtra("sort", -1)
            comic = tmp
            items.clear()
            tmp.chapter.forEach { i ->
                items.add(ChapterRecyclerViewItem.parseData(i))
            }
            if (num == 1) {
                checkSort = true
            } else {
                items.reverse()
                checkSort = false
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun init(view: View?) {
        var latestChapter = view?.findViewById<TextView>(R.id.lastestChapterListTV)
        var price = view?.findViewById<TextView>(R.id.priceChapterListTV)
        var sort = view?.findViewById<TextView>(R.id.sortChapterTV)
        var numberChapter = view?.findViewById<TextView>(R.id.numberChapterChapterListTV)
        latestChapter?.setText("Mới nhất: Chương ${comic.chapter.size}")
        var priceTmp: Int = 0
        var numberChapterTmp: Int = 0
        comic.chapter.forEach { i ->
            if (i.price!! > 0) {
                numberChapterTmp++
                priceTmp += i.price!!
            }
        }
        price?.setText(priceTmp.toString())
        numberChapter?.setText("${numberChapterTmp} Chương")

        //RecycleView
        comic.chapter.forEach { i ->
            items.add(ChapterRecyclerViewItem.parseData(i))
        }
        val recyclerView = view?.findViewById<RecyclerView>(R.id.chapterListDetailRC)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        adapter = context?.let { ChapterRecyclerViewAdapter(it) }!!
        adapter.items = items
        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        adapter.onButtonClick = { view, item, position ->
            when (item) {
                is ChapterRecyclerViewItem.VipChapter -> {
                    if (item.status == true) {
                        comic.chapter[position].bookmark = false
                        item.status = false
                    } else {
                        items.forEach { i ->
                            when (i) {
                                is ChapterRecyclerViewItem.VipChapter -> i.status = false
                                is ChapterRecyclerViewItem.FreeChapter -> i.status = false
                            }
                        }
                        item.status = true
                        comic.chapter[position].bookmark = true
                    }
                }
                is ChapterRecyclerViewItem.FreeChapter -> {
                    if (item.status == true) {
                        item.status = false
                        comic.chapter[position].bookmark = false
                    } else {
                        items.forEach { i ->
                            when (i) {
                                is ChapterRecyclerViewItem.VipChapter -> i.status = false
                                is ChapterRecyclerViewItem.FreeChapter -> i.status = false
                            }
                        }
                        item.status = true
                        comic.chapter[position].bookmark = true
                    }

                }
            }
            adapter.notifyDataSetChanged()
        }
        var checkSort = true
        sort?.setOnClickListener {
            if (sort.text == "Cũ nhất") {
                checkSort = false
                sort.setText("Mới nhất")
            } else {
                checkSort = true
                sort.setText("Cũ nhất")
            }
            items.reverse()
            adapter.notifyDataSetChanged()
        }

        adapter.itemClickListener = { view, item, position ->
            val intent = Intent(context, ViewChapterDetailActivity::class.java)
            intent.putExtra("comic", comic)
            if (checkSort) {
                intent.putExtra("chapterNumber", position)
                intent.putExtra("sort", 1)
            } else {
                intent.putExtra("chapterNumber", comic.chapter.size - position - 1)
                intent.putExtra("sort", 0)
            }

            startActivityForResult(intent, 111)
        }
    }
}

class DetailComicFragment(private var comic: comicItem) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_comic_detail, container, false)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        init(view)
    }

    private var recyclerView: RecyclerView? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(view: View?) {
        var reviewNumber = view?.findViewById<TextView>(R.id.reviewNumberTV)
        var viewNumber = view?.findViewById<TextView>(R.id.viewNumberTV)
        var likeNumber = view?.findViewById<TextView>(R.id.likeNumberTV)
        var followNumber = view?.findViewById<TextView>(R.id.followNumberTV)
        var description = view?.findViewById<ExpandableTextView>(R.id.expandTV)
        var updateTime = view?.findViewById<TextView>(R.id.updateDetailComicTV)
        var likeLL = view?.findViewById<LinearLayout>(R.id.likeComicDetailLL)
        var followLL = view?.findViewById<LinearLayout>(R.id.followComicDetailLL)
        var ratingLL = view?.findViewById<LinearLayout>(R.id.ratingComicDetailLL)

        var star = 0;
        comic.rating.forEach { it ->
            star += it.star
        }
        star /= comic.rating.size
        star.toString().let { reviewNumber?.setText(it) }
        comic.viewNumber?.toString().let { viewNumber?.setText(it) }
        comic.likeNumber?.toString().let { likeNumber?.setText(it) }
        comic.followNumber?.toString().let { followNumber?.setText(it) }
        description?.setText(comic.description)

        var isLike = true
        likeLL?.setOnClickListener {
            val image = view?.findViewById<ImageView>(R.id.likeComicIM)

            if (isLike) {
                comic.likeNumber = comic.likeNumber?.plus(1)
                comic.likeNumber?.toString().let { likeNumber?.setText(it) }
                image?.setImageResource(R.drawable.ic_like_checked)
                isLike=false;
            } else {
                comic.likeNumber = comic.likeNumber?.minus(1)
                comic.likeNumber?.toString().let { likeNumber?.setText(it) }
                image?.setImageResource(R.drawable.ic_hand_like)
                isLike=true;

            }

        }
        var isFollow = true

        followLL?.setOnClickListener {
            val image = view?.findViewById<ImageView>(R.id.followComicIM)
            if (isFollow) {
                comic.followNumber = comic.followNumber?.plus(1)
                comic.followNumber?.toString().let { followNumber?.setText(it) }
                image?.setImageResource(R.drawable.ic_hotspot_checked)
                isFollow = false
            } else {
                comic.followNumber = comic.followNumber?.minus(1)
                comic.followNumber?.toString().let { followNumber?.setText(it) }
                image?.setImageResource(R.drawable.ic_follow)
                isFollow = true
            }

        }

        ratingLL?.setOnClickListener {
            showBottomSheetDialog()
        }

        if (comic.chapter.size > 0) {
            val new = LocalDate.parse(
                comic.chapter[0].datePost,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
            val old = LocalDate.parse(
                comic.chapter[comic.chapter.size - 1].datePost,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
            val tmp: LocalDate
            if (old > new) {
                tmp = old
            } else {
                tmp = new
            }
            val day = tmp.dayOfWeek.toString()
            var temp = ""
            when (day) {
                "MONDAY" -> temp = "thứ 2"
                "TUESDAY" -> temp = "thứ 3"
                "WEDNESDAY" -> temp = "thứ 4"
                "THURSDAY" -> temp = "thứ 5"
                "FRIDAY" -> temp = "thứ 6"
                "SATURDAY" -> temp = "thứ 7"
                "SUNDAY" -> temp = "chủ nhật"
            }
            updateTime?.setText("Cập nhật mới nhất ${temp} (${comic.chapter[comic.chapter.size - 1].datePost})")
        }

    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = context?.let { BottomSheetDialog(it, R.style.DialogStyle) }
        bottomSheetDialog!!.setContentView(R.layout.fragment_bottom_sheet_rate_comic)

        val cancel = bottomSheetDialog.findViewById<TextView>(R.id.cancelRatingTV)
        val send = bottomSheetDialog.findViewById<TextView>(R.id.sendRatingTV)
        val content = bottomSheetDialog.findViewById<EditText>(R.id.ratingET)
        val rc = bottomSheetDialog.findViewById<RecyclerView>(R.id.ratingRC)
        val starOne = bottomSheetDialog.findViewById<ImageView>(R.id.starRatingOneIM)
        val starTwo = bottomSheetDialog.findViewById<ImageView>(R.id.starRatingTwoIM)
        val starThree = bottomSheetDialog.findViewById<ImageView>(R.id.starRatingThreeIM)
        val starFour = bottomSheetDialog.findViewById<ImageView>(R.id.starRatingFourIM)
        val starFive = bottomSheetDialog.findViewById<ImageView>(R.id.starRatingFiveIM)

        val starList = arrayListOf<Boolean>()
        starList.add(false)
        starList.add(false)
        starList.add(false)
        starList.add(false)
        starList.add(false)

        rc!!.setHasFixedSize(true)
        rc.layoutManager = LinearLayoutManager(activity)
        val adapter = RatingListAdapter(context, comic.rating)
        rc.adapter = adapter

        var star = 0
        send!!.setOnClickListener {
            if (content!!.text.toString().length > 0) {
                starList.forEach { it ->
                    if (it) {
                        star++
                    }
                }
                comic.rating.add(
                    0,
                    ratingItem("Đào Duy An", star, "20/07/2022", content.text.toString())
                )
                adapter.notifyDataSetChanged()
                content.setText("")
                val imm: InputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                rc.smoothSnapToPosition(0)
            }
        }

        cancel!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        starOne!!.setOnClickListener {
            if (starList[0]) {
                if (starList[1] == true) {
                    starOne.setImageResource(R.drawable.ic_star)
                    starTwo!!.setImageResource(R.drawable.ic_star_gray)
                    starThree!!.setImageResource(R.drawable.ic_star_gray)
                    starFour!!.setImageResource(R.drawable.ic_star_gray)
                    starFive!!.setImageResource(R.drawable.ic_star_gray)
                    starList[0] = true
                    starList[1] = false
                    starList[2] = false
                    starList[3] = false
                    starList[4] = false
                } else {
                    starOne.setImageResource(R.drawable.ic_star_gray)
                    starList[0] = false
                }

            } else {
                starOne.setImageResource(R.drawable.ic_star)
                starList[0] = true
            }
        }

        starTwo!!.setOnClickListener {
            if (starList[1]) {
                if (starList[2] == true) {
                    starOne.setImageResource(R.drawable.ic_star)
                    starTwo.setImageResource(R.drawable.ic_star)
                    starThree!!.setImageResource(R.drawable.ic_star_gray)
                    starFour!!.setImageResource(R.drawable.ic_star_gray)
                    starFive!!.setImageResource(R.drawable.ic_star_gray)
                    starList[0] = true
                    starList[1] = true
                    starList[2] = false
                    starList[3] = false
                    starList[4] = false
                } else {
                    starOne.setImageResource(R.drawable.ic_star)
                    starTwo.setImageResource(R.drawable.ic_star_gray)
                    starList[1] = false
                }
            } else {
                starOne.setImageResource(R.drawable.ic_star)
                starTwo.setImageResource(R.drawable.ic_star)
                starList[0] = true
                starList[1] = true
            }
        }
        starThree!!.setOnClickListener {
            if (starList[2]) {
                if (starList[3] == true) {
                    starOne.setImageResource(R.drawable.ic_star)
                    starTwo.setImageResource(R.drawable.ic_star)
                    starThree.setImageResource(R.drawable.ic_star)
                    starFour!!.setImageResource(R.drawable.ic_star_gray)
                    starFive!!.setImageResource(R.drawable.ic_star_gray)
                    starList[0] = true
                    starList[1] = true
                    starList[2] = true
                    starList[3] = false
                    starList[4] = false
                } else {
                    starOne.setImageResource(R.drawable.ic_star)
                    starTwo.setImageResource(R.drawable.ic_star)
                    starThree.setImageResource(R.drawable.ic_star_gray)
                    starList[2] = false
                }
            } else {
                starOne.setImageResource(R.drawable.ic_star)
                starTwo.setImageResource(R.drawable.ic_star)
                starThree.setImageResource(R.drawable.ic_star)
                starList[0] = true
                starList[1] = true
                starList[2] = true
            }
        }
        starFour!!.setOnClickListener {
            if (starList[3]) {
                if (starList[4] == true) {
                    starOne.setImageResource(R.drawable.ic_star)
                    starTwo.setImageResource(R.drawable.ic_star)
                    starThree.setImageResource(R.drawable.ic_star)
                    starFour.setImageResource(R.drawable.ic_star)
                    starFive!!.setImageResource(R.drawable.ic_star_gray)
                    starList[0] = true
                    starList[1] = true
                    starList[2] = true
                    starList[3] = true
                    starList[4] = false
                } else {
                    starOne.setImageResource(R.drawable.ic_star)
                    starTwo.setImageResource(R.drawable.ic_star)
                    starThree.setImageResource(R.drawable.ic_star)
                    starFour.setImageResource(R.drawable.ic_star_gray)
                    starList[3] = false
                }
            } else {
                starOne.setImageResource(R.drawable.ic_star)
                starTwo.setImageResource(R.drawable.ic_star)
                starThree.setImageResource(R.drawable.ic_star)
                starFour.setImageResource(R.drawable.ic_star)
                starList[0] = true
                starList[1] = true
                starList[2] = true
                starList[3] = true
            }
        }
        starFive!!.setOnClickListener {
            if (starList[4]) {
                starOne.setImageResource(R.drawable.ic_star)
                starTwo.setImageResource(R.drawable.ic_star)
                starThree.setImageResource(R.drawable.ic_star)
                starFour.setImageResource(R.drawable.ic_star)
                starFive.setImageResource(R.drawable.ic_star_gray)
                starList[4] = false
            } else {
                starOne.setImageResource(R.drawable.ic_star)
                starTwo.setImageResource(R.drawable.ic_star)
                starThree.setImageResource(R.drawable.ic_star)
                starFour.setImageResource(R.drawable.ic_star)
                starFive.setImageResource(R.drawable.ic_star)
                starList[0] = true
                starList[1] = true
                starList[2] = true
                starList[3] = true
                starList[4] = true
            }
        }

        bottomSheetDialog.show()
    }

    private fun initRecyclerView(view: View?) {

        recyclerView = view?.findViewById(R.id.recommendComicRC)!!
        recyclerView?.setHasFixedSize(true)

        val comicList = arrayListOf<comicItem>()
        comicList.add(comicItem("Naruto", R.drawable.manga_naruto, 100))
        comicList.add(comicItem("One Piece", R.drawable.manga_onepiece, 501))
        comicList.add(comicItem("Hunter x Hunter", R.drawable.manga_hunter, 208))
        comicList.add(comicItem("Bleach", R.drawable.manga_bleach, 130))
        comicList.add(comicItem("Doraemon", R.drawable.manga_doraemon, 208))
        comicList.add(comicItem("Dragon Ball", R.drawable.manga_dragonball, 130))

        recyclerView?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val adapter = context?.let { SuggestComicAdapter(it, comicList) }
        adapter?.onItemClick = { tmp ->
            val intent = Intent(context, ViewComicDetailActivity::class.java)
            intent.putExtra("comicName",tmp.name)
            startActivity(intent)
        }
        recyclerView?.adapter = adapter
    }

    private fun RecyclerView.smoothSnapToPosition(
        position: Int,
        snapMode: Int = LinearSmoothScroller.SNAP_TO_START
    ) {
        val smoothScroller = object : LinearSmoothScroller(this.context) {
            override fun getVerticalSnapPreference(): Int = snapMode
            override fun getHorizontalSnapPreference(): Int = snapMode
        }
        smoothScroller.targetPosition = position
        layoutManager?.startSmoothScroll(smoothScroller)
    }
}