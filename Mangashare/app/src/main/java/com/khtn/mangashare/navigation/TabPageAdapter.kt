package com.khtn.mangashare.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabPageAdapter(sFM: FragmentManager): FragmentPagerAdapter(sFM) {
    private val FragmentList= ArrayList<Fragment>()
    private  val FragmentTitle= ArrayList<String>()

    override fun getCount(): Int {
        return FragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return FragmentList[position]
    }


    fun addFragment(fm: Fragment){
        FragmentList.add(fm)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(FragmentTitle.size!=0){
            return FragmentTitle[position]

        }
        else{
            return ""
        }
    }
    fun addFragment(fm:Fragment,title:String){
        FragmentList.add(fm)
        FragmentTitle.add(title)
    }
}