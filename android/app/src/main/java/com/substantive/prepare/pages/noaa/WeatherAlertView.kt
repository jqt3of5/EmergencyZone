package com.substantive.prepare.pages.noaa

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.substantive.prepare.R
import com.substantive.prepare.repository.Room.Entities.WeatherAlert
import java.util.*


/*enum class NotificationEvent(val redId : Int)
{
    Beach(R.drawable.ic_warning_black_24dp),
    Wind(R.drawable.ic_warning_black_24dp),
    Heat(R.drawable.ic_brightness_high_black_24dp),
    Fire(R.drawable.ic_fire_black_24dp),
    Flood(R.drawable.ic_warning_black_24dp),
    Tornado(R.drawable.ic_warning_black_24dp),
    Lightning(R.drawable.ic_lightning_black_24dp),
    Rain(R.drawable.ic_cloud_black_24dp),
    Smoke(R.drawable.ic_lightning_black_24dp),
    Unknown(R.drawable.ic_warning_black_24dp)
}*/

enum class Severity(val colorId : Int, var str : String)
{
    Severe(R.color.colorSevere, "Severe"),
    Moderate(R.color.colorModerate, "Moderate"),
    Unknown(R.color.colorPrimaryDark, "Unknown")
}
class WeatherAlertView : ConstraintLayout{

    lateinit var eventTextView : TextView
    lateinit var severityTextView : TextView
    lateinit var dateTextView : TextView
    lateinit var eventTypeIcon : ImageView
    lateinit var areaDescriptionTextView : TextView
    lateinit var zoneTextView : TextView

    constructor(context:Context, attrSet:AttributeSet ) : super (context, attrSet)
    {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.weather_alert_view, this, true)
    }

    constructor(context:Context) : super(context)
    {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.weather_alert_view, this, true)
        layoutParams = RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        onFinishInflate()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        eventTextView = findViewById(R.id.notification_event)
        severityTextView = findViewById(R.id.notification_severity)
        dateTextView = findViewById(R.id.notification_date)
        eventTypeIcon = findViewById(R.id.event_icon) as ImageView
        eventTypeIcon.visibility = GONE
        areaDescriptionTextView = findViewById(R.id.notification_area_description)
        zoneTextView = findViewById(R.id.notification_zone)

    }

    fun bind(alert : WeatherAlert)
    {
        dateTextView.text = alert?.sent?.toString()
        eventTextView.text = alert?.event
        areaDescriptionTextView.text = alert?.description?.replace("\n", " ")
        zoneTextView.text = FipsDataLoader.zoneToCountyMap?.get(alert.zoneCode)

        when(alert?.severity)
        {
            "Severe" -> setSeverity(Severity.Severe)
            "Moderate" -> setSeverity(Severity.Moderate)
            else -> setSeverity(Severity.Unknown)
        }

        //The alert has expired, so lets update the ui to reflect that
        if (alert.expires?.before(Date()) == true)
        {
            alpha = 0.25f
            severityTextView.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        }
    }

    fun setSeverity(severity : Severity)
    {
        severityTextView.text = severity.str
        when(severity)
        {
            Severity.Severe -> severityTextView.setTextColor(resources.getColor(R.color.colorSevere))
            Severity.Moderate -> severityTextView.setTextColor(resources.getColor(R.color.colorModerate))
            else -> severityTextView.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        }
    }

}