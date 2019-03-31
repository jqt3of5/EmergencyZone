package com.substantive.prepare.noaa.regionselect

import android.content.Context
import com.substantive.prepare.R
import java.io.InputStreamReader
import kotlin.collections.HashMap


class FipsDataLoader {

    companion object {

        lateinit var stateToCountiesMap : Map<String, List<CountyFipsData>>
        lateinit  var zoneToCountyMap : Map<String, String>

        fun loadFipsData(context : Context)
        {
            val stream = context.resources.openRawResource(R.raw.state_county_fips)
            val lines = InputStreamReader(stream).readLines()

            var zoneMap = HashMap<String, String>()
            var stateMap = HashMap<String, MutableList<CountyFipsData>>()
            for (line in lines)
            {
                val split = line.split(",")
                val data = CountyFipsData(stateCodeToNameHash[split[0]]
                        ?: "Unknown", split[0], split[1], split[2], split[3], split[4])
                zoneMap.put(data.getZoneCode(), data.getStateCountyName())
                if (!stateMap.containsKey(data.stateName))
                {
                    stateMap.put(data.stateName, mutableListOf())
                }
                val list = stateMap.get(data.stateName)!!
                list.add(data)
            }
            zoneToCountyMap = zoneMap
            stateToCountiesMap = stateMap

        }

        private val stateCodeToNameHash : HashMap<String, String> = hashMapOf(
                "AK" to "Alaska",
                "AL" to "Alabama",
                "AR" to "Arkansas",
                "AZ" to "Arizona",
                "CA" to "California",
                "CO" to	 "Colorado",
                "CT" to	 "Connecticut",
                "DC" to "Washington DC",
                "DE" to "Delaware",
                "FL" to "Florida",
                "GA" to	 "Georgia",
                "GU" to	 "Guam",
                "HI" to "Hawaii",
                "IA" to "Iowa",
                "ID" to "Idaho",
                "IL" to	"Illinois",
                "IN" to	"Indiana",
                "KS" to	"Kansas",
                "KY" to "Kentucky",
                "LA" to "Louisiana",
                "MA" to "Massachusetts",
                "MD" to "Maryland",
                "ME" to "Maine",
                "MI" to "Michigan",
                "MN" to "Minnesota",
                "MO" to "Missouri",
                "MS" to "Mississippi",
                "MT" to "Montana",
                "NC" to "North Carolina",
                "ND" to "North Dakota",
                "NE" to "Nebraska",
                "NH" to "New Hampshire",
                "NJ" to "New Jersey",
                "NM" to "New Mexico",
                "NV" to "Nevada",
                "NY" to "New York",
                "OH" to "Ohio",
                "OK" to "Oklahoma",
                "OR" to "Oregon",
                "PA" to "Pennsylvania",
                "PR" to "Puerto Rico",
                "RI" to "Rhode Island",
                "SC" to "South Carolina",
                "SD" to "South Dakota",
                "TN" to "Tennessee",
                "TX" to "Texas",
                "UT" to "Utah",
                "VA" to "Virginia",
                "VI" to "Virgin Islands",
                "VT" to "Vermont",
                "WA" to "Washington",
                "WI" to "Wisconsin",
                "WV" to "West Virginia",
                "WY" to "Wyoming"
        )
    }
}