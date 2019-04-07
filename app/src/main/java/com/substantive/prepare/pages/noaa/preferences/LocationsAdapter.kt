package com.substantive.prepare.pages.noaa.preferences

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.substantive.prepare.R
import com.substantive.prepare.pages.noaa.regionselect.FipsDataLoader

class LocationsAdapter : RecyclerView.Adapter<LocationsViewHolder> {

    private var zoneCodes : List<String>
    private var onClickListener : ((Int)->Unit)? = null

    fun setOnClickListener(listener : ((Int)->Unit))
    {
        onClickListener = listener
    }

    constructor(zones : List<String>) : super()
    {
        zoneCodes = zones
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
            onClickListener?.invoke(position)
        }
    }
}

class LocationsViewHolder(val view : LocationView) : RecyclerView.ViewHolder(view){}