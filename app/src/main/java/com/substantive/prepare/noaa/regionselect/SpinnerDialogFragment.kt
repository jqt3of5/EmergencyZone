package com.substantive.prepare.noaa.regionselect

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.substantive.prepare.R

class SpinnerDialogFragment : DialogFragment() {

    private lateinit var mStateSpinner : Spinner
    private lateinit var mCountySpinner : Spinner
    private lateinit var mStatesAdapter : ArrayAdapter<String>
    private lateinit var mCountyAdapter : ArrayAdapter<String>
    private lateinit var mAlertCountTextView : TextView
    lateinit var mStates : List<String>
    lateinit var mCountyMap : Map<String, List<CountyFipsData>>
    var mZoneCountMap : Map<String, Int>? = null

    var mTitle : String? = null
    var mListener : SpinnerDialogSelectedItemListener<CountyFipsData?>? = null

    var selectedState : String? = null
    var selectedCounty : CountyFipsData? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.spinner_dialog_fragment, container)

        val title = view.findViewById(R.id.spinner_dialog_title) as TextView
        title.text = mTitle

        val button = view.findViewById(R.id.spinner_dialog_confirm) as Button
        button.setOnClickListener{
            mListener?.ItemSelected(selectedCounty)
            dialog.dismiss()
        }

        initializeStateSpinner(view)
        initializeCountySpinner(view)


        selectedState = mStates[0]
        mStatesAdapter.addAll(mStates)
        mStatesAdapter.notifyDataSetChanged()

        mCountyMap[selectedState!!]?.let {
            selectedCounty = it[0]
        }

        mCountyAdapter.addAll(mCountyMap[selectedState!!]!!.map{it.countyName})
        mCountyAdapter.notifyDataSetChanged()

        mAlertCountTextView = view.findViewById(R.id.alert_count_text_view)
        mAlertCountTextView.text = "Number of active Alerts: 0"

        refreshAlertCount()

        return view
    }

    fun initializeCountySpinner(view : View)
    {
        mCountySpinner = view.findViewById(R.id.dialog_county_spinner) as AppCompatSpinner
        mCountyAdapter = ArrayAdapter<String>(context, R.layout.simple_spinner_layout)
        mCountySpinner.adapter = mCountyAdapter
        mCountySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCounty = mCountyMap[selectedState!!]?.get(position)
                refreshAlertCount()
            }
        }
    }

    fun initializeStateSpinner(view : View)
    {
        mStateSpinner = view.findViewById(R.id.dialog_state_spinner) as AppCompatSpinner
        mStatesAdapter = ArrayAdapter<String>(context, R.layout.simple_spinner_layout)
        mStateSpinner.adapter = mStatesAdapter
        mStateSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                selectedState = mStates[position]
                mCountyMap[selectedState!!]?.let {
                    selectedCounty = it[0]
                }

                refreshAlertCount()

                mCountyAdapter.clear()
                val counties = mCountyMap.get(selectedState!!)?.map {it.countyName}
                mCountyAdapter.addAll(counties)
                mCountyAdapter.notifyDataSetChanged()
            }
        }

    }

    fun refreshAlertCount()
    {
        mZoneCountMap?.let { map ->
            selectedCounty?.let {
                val zoneCode = it.getZoneCode()
                map[zoneCode]?.let {
                    mAlertCountTextView.text = "Number of active Alerts: " + it
                }
            }
        }
    }
}