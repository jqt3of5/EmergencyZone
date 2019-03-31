package com.emergencyzone.emergencyzone.preferences

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.emergencyzone.emergencyzone.R


class EmergencyZonePrefsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
       setPreferencesFromResource(R.xml.emergency_zone_preferences,rootKey)
    }
}