package com.substantive.prepare.noaa.preferences

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.TextView
import com.substantive.prepare.R

class LocationView(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    lateinit var locationName : TextView
    lateinit var clearImageView : ImageButton


    override fun onFinishInflate() {
        super.onFinishInflate()
        locationName = findViewById(R.id.location_text_view)
        clearImageView = findViewById(R.id.location_clear_button)
    }
}