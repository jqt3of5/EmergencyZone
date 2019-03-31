package com.emergencyzone.emergencyzone.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emergencyzone.emergencyzone.R

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.home_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recycler = view.findViewById<RecyclerView>(R.id.home_recycler_view)
        recycler.layoutManager = LinearLayoutManager(this.context)
        recycler.adapter = HomeRecyclerAdapter(WeatherModel(63.4f, 23f, "Lehi, UT", WeatherModel.WeatherType.Sunny, true), listOf())
        recycler.adapter?.notifyDataSetChanged()
    }
}