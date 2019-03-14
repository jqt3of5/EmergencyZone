package com.emergencyzone.emergencyzone

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mPager : ViewPager
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        //TODO: I really really do not like how the ids/indexes are mapped like this, and again mapped down in the adapter.
        //I'm sure there is a better way, but for now I just want this to work.
        when (item.itemId)
        {
            R.id.navigation_home -> mPager.currentItem = 0
            R.id.navigation_guides -> mPager.currentItem = 1
            R.id.navigation_noaa -> mPager.currentItem = 2
            R.id.navigation_tracker -> mPager.currentItem = 3
            R.id.navigation_search_products -> mPager.currentItem = 4
        }

        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPager = findViewById<ViewPager>(R.id.container)
        mPager.adapter = MainPagerAdapter(supportFragmentManager)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}

class MainPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm)
{
    override fun getCount(): Int {
        return 5
    }

    override fun getItem(index: Int): Fragment {

        when (index)
        {
            0 -> return HomeFragment()
            1 -> return GuidesFragment()
            2 -> return NoaaFragment()
            3 -> return TrackerFragment()
            4 -> return ProductFragment()
        }

        return HomeFragment()
    }
}
