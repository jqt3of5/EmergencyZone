package com.substantive.prepare.pages.noaa

import android.os.Bundle
import android.support.v4.app.BundleCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.substantive.prepare.R
import com.substantive.prepare.pages.noaa.preferences.WeatherAlertsSettingsFragment

class NoaaPageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.noaa_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var pager = view.findViewById<ViewPager>(R.id.weather_pager)
        fragmentManager?.let {
            pager.adapter = NoaaPagerAdapter(it)
        }

    }

    class NoaaPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm)
    {
        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position)
            {
                0 -> "Alerts"
                else -> "Cities"
            }
        }

        override fun getItem(position: Int): Fragment {
            //TODO Make other fragment
            return when(position)
            {
                0 -> {
                    val frag = WeatherAlertsFragment()

                    return frag
                }
                else -> WeatherAlertsSettingsFragment()
            }
        }
    }
}


