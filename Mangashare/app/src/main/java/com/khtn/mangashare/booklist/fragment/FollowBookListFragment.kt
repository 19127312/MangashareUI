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
import com.khtn.mangashare.booklist.adapter.followBookAdapter
import com.khtn.mangashare.booklist.adapter.historyBookListAdapter
import com.khtn.mangashare.comicDetail.ViewComicDetailActivity
import com.khtn.mangashare.model.chapterItem
import com.khtn.mangashare.model.comicItem
import com.khtn.mangashare.model.commentItem
import kotlinx.android.synthetic.main.fragment_follow_book_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowBookListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowBookListFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var adapter: followBookAdapter
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_follow_book_list, container, false)

        initRecyclerView(view)
        return view
    }
    private fun initRecyclerView(view: View?) {
        val recyclerView= view?.findViewById<RecyclerView>(R.id.followRV)
        if (recyclerView != null) {
            recyclerView.layoutManager= LinearLayoutManager(activity)
        }
        var itemList:ArrayList<comicItem>
        itemList= ArrayList()

        itemList.add(initItem("Whole Piece",R.drawable.manga_conan))
        itemList.add(initItem("Doremi",R.drawable.manga_onepiece))
        itemList.add(initItem("My Ball",R.drawable.manga_dragonball))
        itemList.add(initItem("Demon Killer",R.drawable.manga_naruto))
        itemList.add(initItem("Kintama",R.drawable.manga_bleach))

        val root= view?.findViewById<View>(R.id.rootFollow)



        adapter= root?.let { followBookAdapter(it,context,itemList) }!!
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }
        adapter.setOnItemClickListener(object: followBookAdapter.onItemClickListener{
            lateinit var intent: Intent
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ViewComicDetailActivity::class.java)
                intent.putExtra("comicName",itemList[position].name)

                startActivity(intent)
            }
        })

    }
    private fun initItem(name: String, cover: Int): comicItem {
        val imageList = arrayListOf<Int>()
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)
        imageList.add(R.drawable.cover_manga)

        val imageList2 = arrayListOf<Int>()
        imageList2.add(R.drawable.manga_naruto)
        imageList2.add(R.drawable.manga_naruto)
        imageList2.add(R.drawable.manga_naruto)
        imageList2.add(R.drawable.manga_naruto)
        imageList2.add(R.drawable.manga_naruto)
        imageList2.add(R.drawable.manga_naruto)

        val chapterList = arrayListOf<chapterItem>()
        chapterList.add(chapterItem(1, "20/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(2, "21/05/2022", 100, false, 200, imageList2))
        chapterList.add(chapterItem(3, "22/05/2022", 220, false, 200, imageList2))
        chapterList.add(chapterItem(4, "23/05/2022", 0, false, 200, imageList2))
        chapterList.add(chapterItem(5, "24/05/2022", 0, true, 200, imageList2))
        chapterList.add(chapterItem(6, "25/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(7, "26/05/2022", 100, false, 200, imageList))
        chapterList.add(chapterItem(8, "27/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(9, "21/05/2022", 100, false, 200, imageList2))
        chapterList.add(chapterItem(10, "22/05/2022", 220, false, 200, imageList))
        chapterList.add(chapterItem(11, "23/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(12, "24/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(13, "25/05/2022", 0, false, 200, imageList))
        chapterList.add(chapterItem(14, "26/05/2022", 100, false, 200, imageList))
        chapterList.add(chapterItem(15, "10/07/2022", 0, false, 200, imageList))

        val commentList = arrayListOf<commentItem>()
        commentList.add(commentItem("Nguyễn Văn A",1,"20/07/2022","Truyện hay quá. Art đẹp"))
        commentList.add(commentItem("Nguyễn Văn B",2,"20/07/2022","Truyện hay quá. Nên đọc"))
        commentList.add(commentItem("Nguyễn Văn C",3,"20/07/2022","Truyện hay"))
        commentList.add(commentItem("Nguyễn Đức Đạt",11,"20/07/2022","Truyện hay quá. Art đẹp"))
        commentList.add(commentItem("Ngô Nguyễn Kiết Tường",12,"20/07/2022","Truyện hay quá. Art đẹp"))
        commentList.add(commentItem("Đào Duy An",14,"20/07/2022","Truyện hay quá. Art đẹp"))
        commentList.add(commentItem("Tạ Công Điền",15,"20/07/2022","Truyện hay quá. Art đẹp"))
        commentList.add(commentItem("Nguyễn Văn A",9,"20/07/2022","Truyện hay quá. Art đẹp"))
        commentList.add(commentItem("Nguyễn Văn A",10,"20/07/2022","..."))
        commentList.add(commentItem("Nguyễn Văn A",8,"20/07/2022","Truyện hay quá. Art đẹp"))
        commentList.add(commentItem("Nguyễn Văn A",4,"20/07/2022","Truyện hay quá. Art đẹp"))
        commentList.add(commentItem("Nguyễn Văn A",5,"20/07/2022","Truyện hay quá. Art đẹp"))
        commentList.add(commentItem("Nguyễn Văn D",1,"20/07/2022","Truyện hay quá. Art đẹp"))

        val category = arrayListOf<String>()
        category.add("Phiêu lưu")
        category.add("Hành động")
        category.add("Hài hước")
        category.add("Phiêu lưu")
        val des =
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
        val comic =
            comicItem(
                name,
                cover,
                "Aoyama Gosho",
                100,
                23,
                15,
                56,
                34,
                des,
                false,
                category, chapterList, commentList
            )
        return comic
    }


}