package com.substantive.prepare.pages.noaa.preferences

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.substantive.prepare.R
import com.substantive.prepare.repository.Room.Entities.ZoneEntity

class LocationsAdapter : RecyclerView.Adapter<LocationsViewHolder> {

    private var zones : List<ZoneEntity>
    private var onClickListener : ((Int)->Unit)? = null

    fun setOnClickListener(listener : ((Int)->Unit))
    {
        onClickListener = listener
    }

    constructor(zones : List<ZoneEntity>) : super()
    {
        this.zones = zones
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.location_view, parent, false) as LocationView
        return LocationsViewHolder(view)
    }

    override fun getItemCount(): Int {
       return zones.count()
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {

        val zone = zones[position]
        holder.view.locationName.text = zone.toString()
        holder.view.clearImageView.setOnClickListener{
            onClickListener?.invoke(position)
        }
    }
}

class LocationsViewHolder(val view : LocationView) : RecyclerView.ViewHolder(view){}