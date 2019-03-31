package com.substantive.prepare.preferences

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.substantive.prepare.R
import com.substantive.prepare.noaa.regionselect.FipsDataLoader

class LocationsAdapter : RecyclerView.Adapter<LocationsViewHolder> {

    private var zoneCodes : MutableList<String>
    private val preferences: SharedPreferences

    constructor(prefs: SharedPreferences) : super()
    {
        preferences = prefs
        zoneCodes = prefs.getStringSet("ews_zones", emptySet()).toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.location_view, parent, false) as LocationView
        return LocationsViewHolder(view)
    }

    override fun getItemCount(): Int {
       return zoneCodes.count()
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {

        val location = zoneCodes[position]
        holder.view.locationName.text = FipsDataLoader.zoneToCountyMap?.get(location)
        holder.view.clearImageView.setOnClickListener{
            removeZoneAt(position)

        }
    }

    fun reloadZones()
    {
        zoneCodes = preferences.getStringSet("ews_zones", emptySet()).toMutableList()
        notifyDataSetChanged()
    }
    fun addZone(zone : String)
    {
        zoneCodes.add(zone)
        preferences.edit().putStringSet("ews_zones", zoneCodes.toSet()).commit()
        notifyDataSetChanged()
    }

    fun removeZoneAt(position : Int)
    {
        zoneCodes.removeAt(position)
        preferences.edit().putStringSet("ews_zones", zoneCodes.toSet()).commit()
        notifyDataSetChanged()
    }
}

class LocationsViewHolder(val view : LocationView) : RecyclerView.ViewHolder(view){}