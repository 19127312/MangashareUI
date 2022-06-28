package com.khtn.mangashare.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.khtn.mangashare.R
import com.khtn.mangashare.adapter.RankingComicAdapter
import com.khtn.mangashare.model.comicItem

interface IOnRecyclerViewItemTouchListener {
    fun onItemClick(position: Int)
}

class ViewPagerRankingAdapter : FragmentStateAdapter {
    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        dayRanking: ArrayList<comicItem>,
        monthRanking: ArrayList<comicItem>,
        yearRanking: ArrayList<comicItem>
    ) : super(fragmentManager, lifecycle) {
        this.dayRanking = dayRanking
        this.monthRanking = monthRanking
        this.yearRanking = yearRanking
    }

    constructor(
        fragment: Fragment,
        dayRanking: ArrayList<comicItem>,
        monthRanking: ArrayList<comicItem>,
        yearRanking: ArrayList<comicItem>
    ) : super(fragment) {
        this.dayRanking = dayRanking
        this.monthRanking = monthRanking
        this.yearRanking = yearRanking
    }


    private var dayRanking: ArrayList<comicItem>
    private var monthRanking: ArrayList<comicItem>
    private var yearRanking: ArrayList<comicItem>
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RankingComicFragment(dayRanking)
            1 -> RankingComicFragment(monthRanking)
            else -> RankingComicFragment(yearRanking)
        }
    }
}

class RankingComicFragment(private var comicList: ArrayList<comicItem>) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.component_ranking, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }

    private var recyclerView: RecyclerView? = null
    private fun initRecyclerView(view: View?) {

        recyclerView = view?.findViewById(R.id.rankingRC)!!
        recyclerView?.setHasFixedSize(true);

        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val adapter = context?.let { RankingComicAdapter(it, comicList) }
        adapter?.onItemClick = { tmp ->
            Log.i("test", tmp.name.toString())
        }
        recyclerView?.adapter = adapter

    }

    fun updateData(data: List<comicItem>) {
        comicList = ArrayList(data)
        recyclerView?.adapter = RankingComicAdapter(requireContext(), comicList)
    }
}