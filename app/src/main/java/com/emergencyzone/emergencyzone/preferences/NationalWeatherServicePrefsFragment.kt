package com.emergencyzone.emergencyzone.preferences

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.emergencyzone.emergencyzone.R

class NationalWeatherServicePrefsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
       setPreferencesFromResource(R.xml.national_weather_service_prefs,rootKey)
    }
}