package com.substantive.prepare.noaa

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.substantive.prepare.R
import com.substantive.prepare.noaa.regionselect.FipsDataLoader
import com.substantive.prepare.repository.NoaaAlertsRepository

class WeatherAlertsFragment : Fragment()
{
    lateinit var weatherView : DailyWeatherView
    lateinit var alertList : RecyclerView
    lateinit var mAdapter : WeatherAlertsAdapter
    lateinit var rootView : View

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.weather_fragment, container, false)

        weatherView = rootView.findViewById(R.id.daily_weather_view)
        weatherView.onItemSelected {
            updateZoneCode(it.getZoneCode())
        }
        val layout = LinearLayoutManager(this.activity)
        mAdapter = WeatherAlertsAdapter()

        alertList = rootView.findViewById<RecyclerView>(R.id.weather_alerts_recycler_view).apply {
            layoutManager = layout
            adapter = mAdapter
        }

        arguments?.let {
            val zoneCode = it["zone"] as String
            FipsDataLoader.zoneToCountyMap?.let {
                weatherView.searchTextView.setText(it[zoneCode], true)
            }
            updateZoneCode(zoneCode)
        }
        return rootView
    }

    fun updateZoneCode(zoneCode : String)
    {
        NoaaAlertsRepository().getAlertsForZone(zoneCode).observe(this, Observer {
            it?.let {
                mAdapter.updateWeatherAlerts(it)
            }
        })
    }
}