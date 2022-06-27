package com.khtn.mangashare.home.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        init(view)
        return view
    }

    fun init(view: View) {
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
            Log.i("test",tmp.toString())
        }

        recyclerViewComic.adapter = adapter
        recyclerViewComic.scrollToPosition(Int.MAX_VALUE/2)

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