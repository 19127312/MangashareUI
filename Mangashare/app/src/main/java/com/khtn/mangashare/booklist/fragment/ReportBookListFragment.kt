package com.khtn.mangashare.booklist.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.activity.AddComicActivity
import com.khtn.mangashare.booklist.activity.ViewReportActivity
import com.khtn.mangashare.booklist.adapter.historyBookListAdapter
import com.khtn.mangashare.booklist.adapter.myBookListAdapter
import com.khtn.mangashare.model.comicItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReportBookListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportBookListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var itemList:ArrayList<comicItem>
    private lateinit var adapter: myBookListAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_report_book_list, container, false)
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View?) {
        val recyclerView= view?.findViewById<RecyclerView>(R.id.reportRV)
        if (recyclerView != null) {
            recyclerView.layoutManager= LinearLayoutManager(activity)
        }
        itemList= ArrayList()
        itemList.add(comicItem(R.drawable.manga_conan,"Konan","Kasi","Nguyễn Văn A","Đã","Không hợp ly","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ac quam lacus. Nullam nec erat sed neque suscipit luctus. Morbi vitae egestas nisi. Mauris tincidunt in mi vel lobortis. Integer vel urna sagittis, vestibulum eros nec, aliquet leo. Morbi at quam ut sem efficitur imperdiet."))
        itemList.add(comicItem(R.drawable.manga_onepiece,"Mangas","Kaies","Nguyễn Văn A","Đã","Không hợp ly","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ac quam lacus. Nullam nec erat sed neque suscipit luctus. Morbi vitae egestas nisi. Mauris tincidunt in mi vel lobortis. Integer vel urna sagittis, vestibulum eros nec, aliquet leo. Morbi at quam ut sem efficitur imperdiet."))
        itemList.add(comicItem(R.drawable.manga_dragonball,"Lith","Lust","Nguyễn Văn A","Chưa","","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ac quam lacus. Nullam nec erat sed neque suscipit luctus. Morbi vitae egestas nisi. Mauris tincidunt in mi vel lobortis. Integer vel urna sagittis, vestibulum eros nec, aliquet leo. Morbi at quam ut sem efficitur imperdiet."))
        itemList.add(comicItem(R.drawable.manga_naruto,"Grand Red","Hanama","Nguyễn Văn A","Chưa","Không hợp ly","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ac quam lacus. Nullam nec erat sed neque suscipit luctus. Morbi vitae egestas nisi. Mauris tincidunt in mi vel lobortis. Integer vel urna sagittis, vestibulum eros nec, aliquet leo. Morbi at quam ut sem efficitur imperdiet."))
        itemList.add(comicItem(R.drawable.manga_bleach,"Lucky Ball","Osu","Nguyễn Văn A","Chưa","","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ac quam lacus. Nullam nec erat sed neque suscipit luctus. Morbi vitae egestas nisi. Mauris tincidunt in mi vel lobortis. Integer vel urna sagittis, vestibulum eros nec, aliquet leo. Morbi at quam ut sem efficitur imperdiet."))
        itemList.add(comicItem(R.drawable.manga_hunter,"Authentic","Montai","Nguyễn Văn A","Đã","Không hợp ly","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ac quam lacus. Nullam nec erat sed neque suscipit luctus. Morbi vitae egestas nisi. Mauris tincidunt in mi vel lobortis. Integer vel urna sagittis, vestibulum eros nec, aliquet leo. Morbi at quam ut sem efficitur imperdiet."))
        itemList.add(comicItem(R.drawable.manga_conan,"Faker","Lusky","Nguyễn Văn A","Chưa","Không hợp ly","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ac quam lacus. Nullam nec erat sed neque suscipit luctus. Morbi vitae egestas nisi. Mauris tincidunt in mi vel lobortis. Integer vel urna sagittis, vestibulum eros nec, aliquet leo. Morbi at quam ut sem efficitur imperdiet."))
        adapter= myBookListAdapter(itemList)
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }
        adapter.setOnItemClickListener(object: myBookListAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                var intent: Intent
                intent= Intent(context,ViewReportActivity::class.java)
                intent.putExtra("comic",itemList[position])
                startActivity(intent)
            }
        })
    }


}