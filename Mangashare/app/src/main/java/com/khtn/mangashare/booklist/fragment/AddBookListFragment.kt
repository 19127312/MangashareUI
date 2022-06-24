package com.khtn.mangashare.booklist.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.activity.AddComicActivity
import com.khtn.mangashare.booklist.adapter.historyBookListAdapter
import com.khtn.mangashare.booklist.adapter.myBookListAdapter
import com.khtn.mangashare.model.comicItem
import kotlinx.android.synthetic.main.fragment_add_book_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddBookListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddBookListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var adapter: myBookListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_add_book_list, container, false)
        initRecyclerView(view)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addComic()
    }

    private fun addComic() {
        var intent: Intent
        intent= Intent(context,AddComicActivity::class.java)
        intent.putExtra("mode","add")
        addComicBtn.setOnClickListener {
            startActivity(intent)
        }
        addComicText.setOnClickListener {
            startActivity(intent)

        }
    }

    private fun initRecyclerView(view: View?) {
        val recyclerView= view?.findViewById<RecyclerView>(R.id.myBookListRV)
        if (recyclerView != null) {
            recyclerView.layoutManager= LinearLayoutManager(activity)
        }
        var itemList:ArrayList<comicItem>
        itemList= ArrayList()
        itemList.add(comicItem(R.drawable.cover_manga,"Conan",500,"Censored"))
        itemList.add(comicItem(R.drawable.cover_manga,"Conan",500,"Uncensored"))
        itemList.add(comicItem(R.drawable.cover_manga,"Conan",500,"Uncensored"))
        itemList.add(comicItem(R.drawable.cover_manga,"Conan",500,"Censored"))
        itemList.add(comicItem(R.drawable.cover_manga,"Conan",500,"Uncensored"))
        itemList.add(comicItem(R.drawable.cover_manga,"Conan",500,"Censored"))
        itemList.add(comicItem(R.drawable.cover_manga,"Conan",500,"Uncensored"))
        adapter= myBookListAdapter(itemList)
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }
        adapter.setOnItemClickListener(object: myBookListAdapter.onItemClickListener{
            lateinit var intent: Intent
            override fun onItemClick(position: Int) {
                Log.d("MyScreen",position.toString())

            }
        })
    }


}