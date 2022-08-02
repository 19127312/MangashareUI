package com.khtn.mangashare.booklist.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.activity.AddComicActivity
import com.khtn.mangashare.booklist.adapter.historyBookListAdapter
import com.khtn.mangashare.booklist.adapter.myBookListAdapter
import com.khtn.mangashare.model.comicItem
import kotlinx.android.synthetic.main.activity_add_comic.*
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
    val DELETE_CODE=666;
    lateinit var itemList:ArrayList<comicItem>

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
        itemList= ArrayList()
        itemList.add(comicItem(R.drawable.manga_conan,"Konan",500,"Censored"))
        itemList.add(comicItem(R.drawable.manga_onepiece,"Whole Piece",500,"Uncensored"))
        itemList.add(comicItem(R.drawable.manga_dragonball,"Doremi",500,"Temp"))
        itemList.add(comicItem(R.drawable.manga_naruto,"My Ball",500,"Censored"))
        itemList.add(comicItem(R.drawable.manga_bleach,"Demon Killer",500,"Uncensored"))
        itemList.add(comicItem(R.drawable.manga_conan,"Hiloios",500,"Temp"))
        itemList.add(comicItem(R.drawable.manga_onepiece,"Kintama",500,"Uncensored"))
        adapter= myBookListAdapter(itemList)
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }
        adapter.setOnItemClickListener(object: myBookListAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var intent: Intent
                intent= Intent(context,AddComicActivity::class.java)
                intent.putExtra("mode","edit")
                intent.putExtra("position",position.toString())
                intent.putExtra("hasComic", true)
                intent.putExtra("comic", itemList[position])
                startActivityForResult(intent,DELETE_CODE)

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==DELETE_CODE &&resultCode== Activity.RESULT_OK&& data!=null){
            val position = data!!.getStringExtra("position")?.toInt()
            var snackbar = Snackbar.make(rootAddBook, "Đã xóa truyện ${itemList[position!!.toInt()].name}", Snackbar.LENGTH_LONG)
            var item=comicItem(itemList[position])

            if (position != null) {
                itemList.removeAt(position)
            }
            if (position != null) {
                adapter.notifyItemRemoved(position)
            }
            if (position != null) {
                adapter.notifyItemRangeChanged(position,itemList.size)
            }
            snackbar.show()
            snackbar.setAction("Undo delete", View.OnClickListener() {
                itemList.add(position,item)
                adapter.notifyItemInserted(position)
                adapter.notifyItemRangeChanged(position, itemList.size)

            })
        }
    }


}