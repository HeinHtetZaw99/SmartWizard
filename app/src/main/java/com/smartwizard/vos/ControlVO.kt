package com.smartwizard.vos

class ControlVO(
    var actuatorName: String,
    var actuatorChannelID: String,
    var onOffStatus: Boolean,
    var currentValue: Int,
    var unit: String,
    var controllerType: ControllerType

)
{
    enum class ControllerType
    {
        FAN, HUMIDIFIER, LIGHT, WATERPUMP
    }


}
