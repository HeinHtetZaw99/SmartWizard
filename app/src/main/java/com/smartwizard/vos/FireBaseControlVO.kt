package com.smartwizard.vos

import com.google.gson.annotations.SerializedName

class FireBaseControlVO
{
    @SerializedName("fans_on")
    var isFanOn: Boolean = false
    @SerializedName("humidifier_on")
    var isHumidifierOn: Boolean = false
    @SerializedName("light_on")
    var isLightOn: Boolean = false
    @SerializedName("water_pump_on")
    var isWaterPumpOn: Boolean = false

}
