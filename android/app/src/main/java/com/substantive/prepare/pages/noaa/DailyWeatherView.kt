package com.substantive.prepare.pages.noaa

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import com.substantive.prepare.R
import com.substantive.prepare.pages.noaa.regionselect.CountyFipsData


//The View for displaying weather data for one specific region
class DailyWeatherView: ConstraintLayout {

    lateinit var searchTextView: AutoCompleteTextView
    private var itemSelectedblock : ((CountyFipsData) -> Unit)? = null

    constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.daily_weather_view, this, true)
    }

    constructor(context: Context) : super(context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.daily_weather_view, this, true)
        layoutParams = RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        onFinishInflate()
    }

    fun onItemSelected(block:(CountyFipsData) -> Unit)
    {
        itemSelectedblock = block
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        searchTextView = findViewById(R.id.daily_weather_search_text_view)
        searchTextView.onItemClickListener = object:AdapterView.OnItemClickListener{
            override fun onItemClick(adapter: AdapterView<*>?, view: View?, index: Int, id: Long) {
                adapter?.let {
                    itemSelectedblock?.invoke(it.getItemAtPosition(index) as CountyFipsData)
                }
            }
        }
    }
}
