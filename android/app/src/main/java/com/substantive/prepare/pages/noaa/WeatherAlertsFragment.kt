package com.substantive.prepare.pages.noaa

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.substantive.prepare.R
import com.substantive.prepare.pages.noaa.regionselect.CountyFipsData
import com.substantive.prepare.pages.noaa.regionselect.FipsDataLoader
import com.substantive.prepare.repository.Room.Entities.ZoneEntity
import com.substantive.prepare.repository.WeatherRepository

class WeatherAlertsFragment : Fragment()
{
    private lateinit var weatherView : DailyWeatherView
    private lateinit var alertList : RecyclerView
    private lateinit var mAdapter : WeatherAlertsAdapter
    private lateinit var rootView : View

    var autoSuggestItems : List<ZoneEntity> = listOf()
    lateinit var autoSuggestAdapter : ArrayAdapter<CountyFipsData>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.weather_fragment, container, false)

        weatherView = rootView.findViewById(R.id.daily_weather_view)
        weatherView.onItemSelected {

        }

        /*FipsDataLoader.stateToCountiesMap?.let {

            val items = it.map {
                it.value.toMutableList()
            }.reduce { acc, list ->
                acc.addAll(list)
                acc
            }
            var autoSuggestAdapter = ArrayAdapter<CountyFipsData>(this.context, R.layout.simple_spinner_layout, autoSuggestItems)
            weatherView.searchTextView.setAdapter(autoSuggestAdapter)
        }*/



        WeatherRepository().getForecastZones().observe(this, Observer {
            var autoSuggestAdapter = ArrayAdapter<ZoneEntity>(this.context, R.layout.simple_spinner_layout, it)
            weatherView.searchTextView.setAdapter(autoSuggestAdapter)
        })

        val layout = LinearLayoutManager(this.activity)
        mAdapter = WeatherAlertsAdapter()

        alertList = rootView.findViewById<RecyclerView>(R.id.weather_alerts_recycler_view).apply {
            layoutManager = layout
            adapter = mAdapter
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.getStringSet(getString(R.string.ews_zones), null)?.let { zoneCodes ->
            updateZoneCode(zoneCodes.first())
        }
    }

    fun updateZoneCode(zoneCode : String)
    {
        WeatherRepository().getAlertsForZone(zoneCode).observe(this, Observer {
            it?.let {
                mAdapter.updateWeatherAlerts(it)
            }
        })
    }
}