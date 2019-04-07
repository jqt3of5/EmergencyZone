package com.substantive.prepare.pages.noaa

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.substantive.prepare.repository.Room.Entities.WeatherAlert


class WeatherAlertsAdapter : RecyclerView.Adapter<WeatherAlertsAdapter.WeatherAlertViewHolder>()
{
    private var weatherAlerts : List<WeatherAlert>? = null

    fun updateWeatherAlerts(alerts : List<WeatherAlert>)
    {
        weatherAlerts = alerts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAlertViewHolder {
        var view = WeatherAlertView(parent.context)
        return WeatherAlertViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherAlerts?.size ?: 0
    }

    override fun onBindViewHolder(holder: WeatherAlertViewHolder, position: Int) {
        val view = holder.itemView as WeatherAlertView

        weatherAlerts?.get(position)?.let {
            view.bind(it)
        }
    }

    class WeatherAlertViewHolder(view : WeatherAlertView) : RecyclerView.ViewHolder(view)
}