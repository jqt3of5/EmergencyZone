package com.substantive.prepare.preferences

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
import com.substantive.prepare.noaa.regionselect.CountyFipsData
import com.substantive.prepare.noaa.regionselect.FipsDataLoader
import com.substantive.prepare.noaa.regionselect.SpinnerDialogFragment
import com.substantive.prepare.noaa.regionselect.SpinnerDialogSelectedItemListener

class WeatherAlertsSettingsFragment : Fragment(), SpinnerDialogSelectedItemListener<CountyFipsData?> {

    var mAvailableZones : Map<String, List<CountyFipsData>> = FipsDataLoader.stateToCountiesMap

    lateinit var mLocationsAdapter : LocationsAdapter

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
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val recyclerView = view.findViewById(R.id.nws_locations_recycler_view) as RecyclerView
        mLocationsAdapter = LocationsAdapter(sharedPreferences)
        recyclerView.adapter = mLocationsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun showRegionSelectDialog()
    {
        val dialog = SpinnerDialogFragment()

        dialog.mCountyMap = mAvailableZones
        dialog.mStates = mAvailableZones.keys.toList().sorted()
        dialog.mTitle = "Select Zone"
        dialog.mListener = this
        dialog.show(fragmentManager, "state_select_fragment")
    }

    override fun ItemSelected(selection: CountyFipsData?) {
        selection?.let {
            mLocationsAdapter.addZone(it.getZoneCode())
        }
    }
}