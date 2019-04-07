package com.substantive.prepare.repository.Api.Weather.DataObjects

import java.util.*

class WeatherServiceAlerts {
    var title:String = ""
    var zoneCode: String = ""
    var features : Array<AlertFeature>? = null
}

class AlertFeature {
    var id : String = ""
    var properties : AlertProperties? = null
}

class AlertProperties{
    var areaDesc : String = ""
    var headline : String = ""
    var description : String = ""
    var severity : String = ""
    var certainty : String = ""
    var event : String = ""
    var instruction: String = ""
    var sent : Date? = null
    var effective: Date? = null
    var expires : Date? = null
    var ends : Date? = null
}
