package com.khtn.mangashare.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.khtn.mangashare.R
import com.khtn.mangashare.booklist.fragment.BookListFragment
import com.khtn.mangashare.home.fragment.HomeFragment
import com.khtn.mangashare.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var typeUser=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent=intent
        typeUser= intent.getStringExtra("typeUser").toString()
        setUpTabBar()

    }

    private fun setUpTabBar() {
        val newAdapter= TabPageAdapter(supportFragmentManager)
        newAdapter.addFragment(HomeFragment())
        newAdapter.addFragment(BookListFragment(typeUser))
        newAdapter.addFragment(ProfileFragment())
        viewPager.adapter=newAdapter
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_home_selected)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_booklist)
        tabs.getTabAt(2)!!.setIcon(R.drawable.ic_user_outline)
                tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {

                viewPager.currentItem=tab.position
                when(tab.position){
                    0->tab.setIcon(R.drawable.ic_home_selected)
                    1->tab.setIcon(R.drawable.ic_booklist_selected)
                    2->tab.setIcon(R.drawable.ic_user_selected)
                }

                //Log.d("MyScreen",tab.position.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                var oldPos=tab?.position
                when(oldPos){
                    0->tab?.setIcon(R.drawable.ic_home)
                    1->tab?.setIcon(R.drawable.ic_booklist)
                    2->tab?.setIcon(R.drawable.ic_user_outline)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}