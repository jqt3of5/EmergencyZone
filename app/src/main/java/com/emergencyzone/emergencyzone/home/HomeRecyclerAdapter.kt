package com.emergencyzone.emergencyzone.home

import android.app.Notification
import android.graphics.ColorSpace
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.emergencyzone.emergencyzone.R

class HomeRecyclerAdapter(var weatherModel : WeatherModel, var notifications : List<NotificationModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class ViewTypes {SectionHeader, Weather, Notification}//ProductRecommendation, InventoryNotification, OrderStatus}

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {

        when (type)
        {
            ViewTypes.Weather.ordinal -> {
                var layout = LayoutInflater.from(parent.context).inflate(R.layout.home_noaa_layout, parent, false)
                return NoaaWeatherViewHolder(layout)
            }
            else -> {
                var layout = LayoutInflater.from(parent.context).inflate(R.layout.home_notification_layout, parent, false)
                return NotificationViewHolder(layout)
            }
        }
    }

    override fun getItemCount(): Int = 1 + notifications.count()

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, index: Int) {
        when(viewHolder) {
            is NoaaWeatherViewHolder -> {
                var view = viewHolder.itemView
                view.findViewById<ImageView>(R.id.main_icon_imageview)

                var mainCity = view.findViewById<TextView>(R.id.main_city_textview)
                mainCity.text = weatherModel.location

                var mainTemp = view.findViewById<TextView>(R.id.main_temp_textview)
                mainTemp.text = weatherModel.temp.toString() + " F"

                var mainHumid = view.findViewById<TextView>(R.id.main_humidity_textview)
                mainHumid.text = weatherModel.humidity.toString() +"%"

                var alertImage = view.findViewById<ImageView>(R.id.weather_alerts_imageview)
                var alertTextView = view.findViewById<TextView>(R.id.weather_alerts_textview)

                if (weatherModel.hasAlerts)
                {
                    alertImage.visibility = View.VISIBLE
                    alertTextView.visibility = View.VISIBLE
                }
                else
                {
                    alertImage.visibility = View.GONE
                    alertTextView.visibility = View.GONE
                }


                var alertButton = view.findViewById<Button>(R.id.weather_alerts_button)
                alertButton.setOnClickListener {
                    //Move to NOAA tab
                }
            }
            is NotificationViewHolder -> {
                var view = viewHolder.itemView
                var model = notifications[index - 1]
                var image = view.findViewById<ImageView>(R.id.notification_image_view)

                var title = view.findViewById<TextView>(R.id.notification_title)
                var description = view.findViewById<TextView>(R.id.notification_description)

                var primary_button = view.findViewById<Button>(R.id.notification_action_button)
                var secondary_button = view.findViewById<Button>(R.id.notification_secondary_action_button)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position)
        {
            0 -> ViewTypes.Weather.ordinal
            else -> ViewTypes.Notification.ordinal
        }
    }
}

//For Section Headers
class SectionHeaderViewHolder(sectionHeader: View) : RecyclerView.ViewHolder(sectionHeader) {

}

//For showing the weather and weather notifications
class NoaaWeatherViewHolder(sectionHeader: View) : RecyclerView.ViewHolder(sectionHeader) {

}

class NotificationViewHolder(notification: View) : RecyclerView.ViewHolder(notification) {

}


/*
//Small tiled views to show product recommendations
class ProductRecommendations(productView : View)  : RecyclerView.ViewHolder(productView) {

}

//Show Notifications for food storage expirations/getting low etc.
class InventoryNotification(productView : View)  : RecyclerView.ViewHolder(productView) {

}

//If a customerhas an order, then show a status tracker
class OrderStatusViewHolder(productView : View)  : RecyclerView.ViewHolder(productView) {

}
*/
