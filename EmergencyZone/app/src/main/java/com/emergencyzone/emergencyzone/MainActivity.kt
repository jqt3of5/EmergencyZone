package com.emergencyzone.emergencyzone

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun selectFragment(itemId : Int)
    {
        var fragment : Fragment? = null
        when (itemId)
        {
            R.id.navigation_home -> {
                fragment = HomeFragment();
            }
            R.id.navigation_guides -> {
                fragment = GuidesFragment();
            }
            R.id.navigation_noaa -> {
                fragment = NoaaFragment();
            }
            R.id.navigation_tracker -> {
                fragment = TrackerFragment();
            }
            R.id.navigation_search_products -> {
                fragment = ProductFragment();
            }
        }

        fragment?.let {
            var transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, fragment, fragment.tag)
            transaction.commit()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
