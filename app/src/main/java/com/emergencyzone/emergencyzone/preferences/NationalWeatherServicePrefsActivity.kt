package com.emergencyzone.emergencyzone.preferences

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.emergencyzone.emergencyzone.R
import com.emergencyzone.emergencyzone.noaa.regionselect.CountyFipsData
import com.emergencyzone.emergencyzone.noaa.regionselect.FipsDataLoader
import com.emergencyzone.emergencyzone.noaa.regionselect.SpinnerDialogFragment
import com.emergencyzone.emergencyzone.noaa.regionselect.SpinnerDialogSelectedItemListener

class NationalWeatherServicePrefsActivity : AppCompatActivity(), SpinnerDialogSelectedItemListener<CountyFipsData?> {

    var mAvailableZones : Map<String, List<CountyFipsData>>? = null

    lateinit var mLocationsAdapter : LocationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.national_weather_service_prefs_activity)

        val addButton = findViewById(R.id.nws_add_location_button) as Button
        addButton.setOnClickListener {
            showRegionSelectDialog()
        }
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val recyclerView = findViewById(R.id.nws_locations_recycler_view) as RecyclerView
        mLocationsAdapter = LocationsAdapter(sharedPreferences)
        recyclerView.adapter = mLocationsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    fun showRegionSelectDialog()
    {
        mAvailableZones = mAvailableZones ?: FipsDataLoader.stateToCountiesMap
        val dialog = SpinnerDialogFragment()

        dialog.mCountyMap = mAvailableZones!!
        dialog.mStates = mAvailableZones!!.keys.toList().sorted()
        dialog.mTitle = "Select Zone"
        dialog.mListener = this
        dialog.show(supportFragmentManager, "state_select_fragment")
    }

    override fun ItemSelected(selection: CountyFipsData?) {
        selection?.let {
            mLocationsAdapter.addZone(it.getZoneCode())
        }
    }
}