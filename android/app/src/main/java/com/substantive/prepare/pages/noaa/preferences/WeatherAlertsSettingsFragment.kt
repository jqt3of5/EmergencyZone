package com.substantive.prepare.pages.noaa.preferences

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.substantive.prepare.R
import com.substantive.prepare.pages.noaa.regionselect.CountyFipsData
import com.substantive.prepare.pages.noaa.regionselect.SpinnerDialogFragment
import com.substantive.prepare.pages.noaa.regionselect.SpinnerDialogSelectedItemListener
import com.substantive.prepare.repository.Room.Entities.ZoneEntity
import com.substantive.prepare.repository.WeatherRepository

class WeatherAlertsSettingsFragment : Fragment(), SpinnerDialogSelectedItemListener<ZoneEntity?> {

    private lateinit var mLocationsAdapter : LocationsAdapter
    private var selectedZoneCodes : MutableList<String> = mutableListOf()
    lateinit private var prefs : SharedPreferences

    private var mStates : MutableList<String> = mutableListOf()
    private var mStateToZoneMap : HashMap<String, MutableList<ZoneEntity>> = hashMapOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_alert_settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO Need an add button
        val addButton = view.findViewById(R.id.nws_add_location_button) as Button
        addButton.setOnClickListener {
            showRegionSelectDialog()
        }

        val recyclerView = view.findViewById(R.id.nws_locations_recycler_view) as RecyclerView

        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        selectedZoneCodes = prefs.getStringSet(getString(R.string.ews_zones), emptySet()).toMutableList()

        mLocationsAdapter = LocationsAdapter(selectedZoneCodes)
        mLocationsAdapter.setOnClickListener {
            removeZoneAt(it)
        }
        recyclerView.adapter = mLocationsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        WeatherRepository().getForecastZones().observe(this, Observer {
            it?.let {
                val states = it.map {
                    it.state
                }
                mStates.clear()
                mStates.addAll(0, states)

                it.forEach {
                    if (!mStateToZoneMap.containsKey(it.state))
                    {
                        mStateToZoneMap[it.state] = mutableListOf()
                    }
                    mStateToZoneMap[it.state]?.add(it)
                }
            }
        })
    }

    fun showRegionSelectDialog()
    {
        val dialog = SpinnerDialogFragment()
        dialog.mStates = mStates
        dialog.mStateToZoneMap = mStateToZoneMap
        dialog.mTitle = "Select Zone"
        dialog.mListener = this
        dialog.show(fragmentManager, "state_select_fragment")
    }

    override fun ItemSelected(selection: ZoneEntity?) {
        selection?.let {
            addZone(it.zoneId)
        }
    }

    fun addZone(zone : String)
    {
        selectedZoneCodes.add(zone)
        prefs.edit().putStringSet(getString(R.string.ews_zones), selectedZoneCodes.toSet()).commit()
        mLocationsAdapter.notifyDataSetChanged()
    }

    fun removeZoneAt(position : Int)
    {
        selectedZoneCodes.removeAt(position)
        prefs.edit().putStringSet(getString(R.string.ews_zones), selectedZoneCodes.toSet()).commit()
        mLocationsAdapter.notifyDataSetChanged()
    }
}