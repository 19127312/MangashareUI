package com.khtn.mangashare.booklist.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.adapter.historyBookListAdapter
import com.khtn.mangashare.chapterDetail.ViewChapterDetailActivity
import com.khtn.mangashare.comicDetail.ViewComicDetailActivity
import com.khtn.mangashare.model.comicItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryBookListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryBookListFragment : Fragment() {
    // TODO: Rename and change types of parameters


    private lateinit var adapter: historyBookListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_history_book_list, container, false)
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View?) {
        val recyclerView= view?.findViewById<RecyclerView>(R.id.historyRV)
        if (recyclerView != null) {
            recyclerView.layoutManager= LinearLayoutManager(activity)
        }
        var itemList:ArrayList<comicItem>
        itemList= ArrayList()

        itemList.add(comicItem(R.drawable.cover_manga,"Konan",500,80,"5:30pm 23/22/2022"))
        itemList.add(comicItem(R.drawable.cover_manga,"Whole Piece",400,80,"6:30pm 23/22/2022"))
        itemList.add(comicItem(R.drawable.cover_manga,"Doremi",300,80,"7:30pm 23/22/2022"))
        itemList.add(comicItem(R.drawable.cover_manga,"My Ball",200,80,"8:30pm 23/22/2022"))
        itemList.add(comicItem(R.drawable.cover_manga,"Demon Killer",100,80,"9:30pm 23/22/2022"))
        itemList.add(comicItem(R.drawable.cover_manga,"Kintama",90,80,"10:30pm 23/22/2022"))



        adapter= historyBookListAdapter(context,itemList)
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }
        adapter.setOnItemClickListener(object: historyBookListAdapter.onItemClickListener{
            lateinit var intent: Intent
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ViewComicDetailActivity::class.java)
                startActivity(intent)
            }
        })

    }


}