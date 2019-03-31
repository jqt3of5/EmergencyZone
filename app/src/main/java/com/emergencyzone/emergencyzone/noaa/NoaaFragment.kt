package com.emergencyzone.emergencyzone.noaa

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.emergencyzone.emergencyzone.R

class NoaaFragment : Fragment() {

    lateinit var alertAdapter : AlertsAdapter
    lateinit var citiesAdapter : CitiesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.noaa_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertAdapter = AlertsAdapter(
            listOf(
                "Severe Weather Alert",
                "Winter Storm Warning"
            )
        )
        citiesAdapter =
                CitiesAdapter(listOf("Lehi, UT", "La Habra, CA"))

        var pager = view.findViewById<ViewPager>(R.id.weather_pager)
        pager.adapter = NoaaPagerAdapter()
    }

    class TextViewViewHolder(textView : TextView) : RecyclerView.ViewHolder(textView)

    class AlertsAdapter(var alerts : List<Any>) : RecyclerView.Adapter<TextViewViewHolder>() {
        override fun getItemCount(): Int = alerts.count()

        override fun onCreateViewHolder(container: ViewGroup, p1: Int): TextViewViewHolder {
            var tv = TextView(container.context)
            var vh = TextViewViewHolder(tv)
            return vh
        }

        override fun onBindViewHolder(vh: TextViewViewHolder, position: Int) {
            var view = vh.itemView
            if (view is TextView)
            {
                view.text = alerts[position].toString()
            }
        }
    }

    class CitiesAdapter (var cities : List<Any>) : RecyclerView.Adapter<TextViewViewHolder>() {
        override fun getItemCount(): Int = cities.count()

        override fun onCreateViewHolder(container: ViewGroup, p1: Int): TextViewViewHolder {
            var tv = TextView(container.context)
            var vh = TextViewViewHolder(tv)
            return vh
        }

        override fun onBindViewHolder(vh: TextViewViewHolder, position: Int) {
            var view = vh.itemView
            if (view is TextView)
            {
                view.text = cities[position].toString()
            }
        }
    }
    inner class NoaaPagerAdapter : PagerAdapter()
    {
        override fun getCount(): Int = 2
        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var recycler = RecyclerView(container.context)
            recycler.layoutManager = LinearLayoutManager(container.context)
            recycler.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            when (position)
            {
                0 -> recycler.adapter = alertAdapter
                1 -> recycler.adapter = citiesAdapter
            }
            container.addView(recycler)
            return recycler
        }
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            (`object` as? View)?.let {
                container.removeView(it)
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
           return when(position)
            {
                0 -> "Alerts"
                else -> "Cities"
            }
        }

    }

}


