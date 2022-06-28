package com.khtn.mangashare.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.SuggestCategoryAdapter
import com.khtn.mangashare.adapter.SuggestComicAdapter
import com.khtn.mangashare.model.comicItem

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
        return view
    }

    private fun initRecyclerView(view: View) {
        val cate = arrayListOf<String>()
        cate.add("Hành động")
        cate.add("Trinh thám")
        cate.add("Phiêu lưu")
        cate.add("Hài hước")
        cate.add("Phiêu lưu")
        cate.add("Hài hước")

        val recyclerViewCategory = view.findViewById<RecyclerView>(R.id.suggestCategoryRC)
        recyclerViewCategory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.adapter = SuggestCategoryAdapter(context, cate)

        val comic = arrayListOf<comicItem>()
        comic.add(comicItem("Naruto", R.drawable.cover_manga, 100))
        comic.add(comicItem("One piece", R.drawable.cover_manga, 501))
        comic.add(comicItem("Hunter x hunter", R.drawable.cover_manga, 208))
        comic.add(comicItem("Bleach", R.drawable.cover_manga, 130))
        comic.add(comicItem("Doraemon", R.drawable.cover_manga, 208))
        comic.add(comicItem("Dragon ball", R.drawable.cover_manga, 130))

        val recyclerViewComic = view.findViewById<RecyclerView>(R.id.suggestComicRC)
        recyclerViewComic.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val adapter = SuggestComicAdapter(context, comic)
        adapter.onItemClick = { tmp ->
            Log.i("test", tmp.toString())
        }

        recyclerViewComic.adapter = adapter
        recyclerViewComic.scrollToPosition(Int.MAX_VALUE / 2)

    }

    private fun initViewPager(view: View) {
        val description =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tincidunt tellus sed nulla auctor egestas. "
        val category = arrayListOf<String>()
        category.add("Phiêu lưu")
        category.add("Hành động")
        category.add("Hài hước")
        category.add("Phiêu lưu")
        category.add(description)

        comicDayRankingItem.add(
            comicItem(
                "Naruto",
                R.drawable.manga_cover,
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
                R.drawable.manga_cover,
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
                "Onepiece",
                R.drawable.manga_cover,
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
                "Dragon ball",
                R.drawable.manga_cover,
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
                R.drawable.manga_cover,
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
                R.drawable.manga_cover,
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
                R.drawable.manga_cover,
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
                R.drawable.manga_cover,
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
                R.drawable.manga_cover,
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
                0 -> tab.text = "Hot trong ngày"
                1 -> tab.text = "Hot trong tháng"
                2 -> tab.text = "Hot trong năm"
            }
        }.attach()
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