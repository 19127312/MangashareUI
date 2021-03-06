package com.khtn.mangashare.home.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.SuggestCategoryAdapter
import com.khtn.mangashare.adapter.SuggestComicAdapter
import com.khtn.mangashare.booklist.activity.ViewBookListActivity
import com.khtn.mangashare.categoryList.ViewCategoryListActivity
import com.khtn.mangashare.comicDetail.ViewComicDetailActivity
import com.khtn.mangashare.home.adapter.ViewPagerRankingAdapter
import com.khtn.mangashare.model.comicItem
import com.khtn.mangashare.search.SearchComicActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var comicDayRankingItem = arrayListOf<comicItem>()
    private var comicMonthRankingItem = arrayListOf<comicItem>()
    private var comicYearRankingItem = arrayListOf<comicItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initRecyclerView(view)
        initViewPager(view)
        init(view)
        return view
    }

    private fun init(view: View) {
        val moreCategory = view.findViewById<LinearLayout>(R.id.categoriesAddLL)
        val moreSuggestComic = view.findViewById<LinearLayout>(R.id.suggestAddLL)
        val funnyCategoryBTN = view.findViewById<Button>(R.id.funnyCategoryBTN)
        val adventureCategoryBTN = view.findViewById<Button>(R.id.adventureCategoryBTN)
        val sportCategoryBTN = view.findViewById<Button>(R.id.sportCategoryBTN)
        val loveCategoryBTN = view.findViewById<Button>(R.id.loveCategoryBTN)
        val searchBTN = view.findViewById<ImageView>(R.id.searchIM)

        moreCategory.setOnClickListener {
            val intent = Intent(context, ViewCategoryListActivity::class.java)
            startActivity(intent)
        }

        moreSuggestComic.setOnClickListener {
            val intent = Intent(context, ViewBookListActivity::class.java)
            intent.putExtra("title", "Gi???i thi???u cho b???n")
            startActivity(intent)
        }

        funnyCategoryBTN.setOnClickListener {
            goToBookListByCategory("Vui nh???n")
        }

        adventureCategoryBTN.setOnClickListener {
            goToBookListByCategory("Phi??u l??u")
        }

        sportCategoryBTN.setOnClickListener {
            goToBookListByCategory("Th??? thao")
        }

        loveCategoryBTN.setOnClickListener {
            goToBookListByCategory("T??nh c???m")
        }

        searchBTN.setOnClickListener{
            val intent = Intent(context, SearchComicActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(view: View) {
        val searchIm = view.findViewById<ImageView>(R.id.searchIM)
        searchIm?.setOnClickListener {
            val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.anim_chapter_detail)
            val ic = view.findViewById<ImageView>(R.id.iconapp)
            ic?.startAnimation(anim)
        }
        val cate = arrayListOf<String>()
        cate.add("H??nh ?????ng")
        cate.add("Phi??u l??u")
        cate.add("H??i h?????c")

        val recyclerViewCategory = view.findViewById<RecyclerView>(R.id.suggestCategoryRC)
        val categoryAdapter = SuggestCategoryAdapter(context, cate)
        recyclerViewCategory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.adapter = categoryAdapter
        categoryAdapter.onButtonClick = { item ->
            goToBookListByCategory(item)
        }
        val comic = arrayListOf<comicItem>()
        comic.add(comicItem("Naruto", R.drawable.manga_naruto, 100))
        comic.add(comicItem("One Piece", R.drawable.manga_onepiece, 501))
        comic.add(comicItem("Hunter x Hunter", R.drawable.manga_hunter, 208))
        comic.add(comicItem("Bleach", R.drawable.manga_bleach, 130))
        comic.add(comicItem("Doraemon", R.drawable.manga_doraemon, 208))
        comic.add(comicItem("Dragon Ball", R.drawable.manga_dragonball, 130))

        val recyclerViewComic = view.findViewById<RecyclerView>(R.id.suggestComicRC)
        recyclerViewComic.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val adapter = SuggestComicAdapter(context, comic)
        adapter.onItemClick = { tmp ->
            val intent = Intent(context, ViewComicDetailActivity::class.java)
            intent.putExtra("comicName",tmp.name)
            startActivity(intent)
        }

        recyclerViewComic.adapter = adapter
    }

    private fun initViewPager(view: View) {
        val description =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tincidunt tellus sed nulla auctor egestas. "
        val category = arrayListOf<String>()
        category.add("Phi??u l??u")
        category.add("H??nh ?????ng")
        category.add("H??i h?????c")

        comicDayRankingItem.add(
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
        comicDayRankingItem.add(
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
        comicDayRankingItem.add(
            comicItem(
                "One Piece",
                R.drawable.manga_onepiece,
                100,
                201,
                501,
                description,
                true,
                category
            )
        )

        comicMonthRankingItem.add(
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
        comicMonthRankingItem.add(
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
        comicMonthRankingItem.add(
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

        comicYearRankingItem.add(
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
        comicYearRankingItem.add(
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
        comicYearRankingItem.add(
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

        val tabLayout = view.findViewById<TabLayout>(R.id.rankingTL)
        val viewPager = view.findViewById<ViewPager2>(R.id.comicRankingVP)
        viewPager?.adapter =
            ViewPagerRankingAdapter(
                this,
                comicDayRankingItem,
                comicMonthRankingItem,
                comicYearRankingItem
            )
        viewPager?.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager!!) { tab, position ->
            when (position) {
                0 -> tab.text = "Hot trong ng??y"
                1 -> tab.text = "Hot trong th??ng"
                2 -> tab.text = "Hot trong n??m"
            }
        }.attach()
    }

    private fun goToBookListByCategory(title: String) {
        val intent = Intent(context, ViewBookListActivity::class.java)
        intent.putExtra("title", title)
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}