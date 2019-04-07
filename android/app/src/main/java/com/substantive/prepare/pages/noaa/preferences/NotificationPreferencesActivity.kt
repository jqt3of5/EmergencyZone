package com.substantive.prepare.pages.noaa.preferences

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.substantive.prepare.R

class NotificationPreferencesActivity : AppCompatActivity() {

    lateinit var mLocationsAdapter : LocationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_preferences_activity)

        val moreSettingsButton = findViewById(R.id.nws_more_settings_button) as Button
        moreSettingsButton.setOnClickListener {
            val intent = Intent(this,WeatherAlertsSettingsFragment::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById(R.id.nws_locations_recycler_view) as RecyclerView
        recyclerView.adapter = mLocationsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val zones = sharedPreferences.getStringSet(getString(R.string.ews_zones), emptySet()).toList()
        mLocationsAdapter = LocationsAdapter(zones)
    }
}