package com.khtn.mangashare.booklist.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.activity.ViewCensorComicActivity
import com.khtn.mangashare.booklist.adapter.censorListAdapter
import com.khtn.mangashare.booklist.adapter.historyBookListAdapter
import com.khtn.mangashare.comicDetail.ViewComicDetailActivity
import com.khtn.mangashare.model.comicItem
import kotlinx.android.synthetic.main.fragment_add_book_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PendingBookListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PendingBookListFragment : Fragment() {



    private lateinit var adapter: censorListAdapter
    lateinit var itemList:ArrayList<comicItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_pending_book_list, container, false)
        initRecyclerView(view)
        return view
    }
    val DELETE_CODE=666;

    private fun initRecyclerView(view: View?) {
        val recyclerView= view?.findViewById<RecyclerView>(R.id.censorRV)
        if (recyclerView != null) {
            recyclerView.layoutManager= LinearLayoutManager(activity)
        }
        itemList= ArrayList()

        itemList.add(comicItem("Conan",R.drawable.cover_manga,"Nguyễn Văn A","23/12/2022"))
        itemList.add(comicItem("Elsep",R.drawable.cover_manga,"Nguyễn Văn B","23/12/2022"))
        itemList.add(comicItem("Berk",R.drawable.cover_manga,"Nguyễn Văn C","23/12/2022"))
        itemList.add(comicItem("Mona",R.drawable.cover_manga,"Nguyễn Văn D","23/12/2022"))
        itemList.add(comicItem("Leep",R.drawable.cover_manga,"Nguyễn Văn E","23/12/2022"))
        itemList.add(comicItem("Hoime ",R.drawable.cover_manga,"Nguyễn Văn F","23/12/2022"))


        adapter= censorListAdapter(context,itemList)
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }
        adapter.setOnItemClickListener(object: censorListAdapter.onItemClickListener{
            lateinit var intent: Intent
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ViewCensorComicActivity::class.java)
                intent.putExtra("comic",itemList[position])
                intent.putExtra("position",position.toString())

                startActivityForResult(intent,DELETE_CODE)
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==DELETE_CODE &&resultCode== Activity.RESULT_OK&& data!=null){
            val position = data!!.getStringExtra("position")?.toInt()
            if (position != null) {
                itemList.removeAt(position)
            }
            if (position != null) {
                adapter.notifyItemRemoved(position)
            }
            if (position != null) {
                adapter.notifyItemRangeChanged(position,itemList.size)
            }

        }
    }


}