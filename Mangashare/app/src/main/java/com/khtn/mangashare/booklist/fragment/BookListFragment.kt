package com.khtn.mangashare.booklist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.khtn.mangashare.R
import com.khtn.mangashare.navigation.TabPageAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookListFragment (type: String): Fragment() {
    var typeUser=type



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val x: View = inflater.inflate(R.layout.fragment_book_list,container, false)
        val myViewPager: ViewPager = x.findViewById<ViewPager>(R.id.viewPagerBookList)
        val myTabLayout: TabLayout = x.findViewById<TabLayout>(R.id.tabsBookList)
        if(myViewPager!=null){
            setupViewPager(typeUser,myViewPager)
        }
        assert(myViewPager != null)
        myTabLayout.setupWithViewPager(myViewPager)

        // Inflate the layout for this fragment
        return x
    }

    private fun setupViewPager(type: String,myViewPager: ViewPager) {
        val newAdapter= TabPageAdapter(childFragmentManager )
        newAdapter.addFragment(HistoryBookListFragment(),"Lịch sử")
        newAdapter.addFragment(FollowBookListFragment(),"Theo dõi")
        when(type){
            "author"->{
                newAdapter.addFragment(AddBookListFragment(),"Thêm truyện")
            }
            "admin"->{
                newAdapter.addFragment(PendingBookListFragment(),"Kiểm duyệt")
                newAdapter.addFragment(ReportBookListFragment(),"Báo cáo")

            }

        }


        myViewPager.adapter=newAdapter
    }


}