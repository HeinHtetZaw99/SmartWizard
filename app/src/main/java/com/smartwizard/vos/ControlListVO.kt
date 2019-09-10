package com.smartwizard.vos

class ControlListVO(val fieldData: List<Int>, val fieldOnOffData: FireBaseControlVO) : BaseResponse
{


    private lateinit var actuatorList: List<ControlVO>

    fun getControlList(): ArrayList<ControlVO>
    {
        val list = ArrayList<ControlVO>()
        try
        {

            list.add(
                ControlVO(
                    "Fans",
                    "field1",
                    fieldOnOffData.isFanOn,
                    fieldData[0],
                    "/'F",
                    ControlVO.ControllerType.FAN
                )
            )
            list.add(
                ControlVO(
                    "Motors",
                    "field2",
                    fieldOnOffData.isWaterPumpOn,
                    fieldData[1],
                    "ppm",
                    ControlVO.ControllerType.WATERPUMP
                )
            )
            list.add(
                ControlVO(
                    "Lights",
                    "field3",
                    fieldOnOffData.isLightOn, fieldData[2],
                    "units",
                    ControlVO.ControllerType.LIGHT
                )
            )
            list.add(
                ControlVO(
                    "Humidifier",
                    "field4",
                    fieldOnOffData.isHumidifierOn, fieldData[3],
                    "ppm",
                    ControlVO.ControllerType.HUMIDIFIER
                )
            )
            return list
        } catch (e: IndexOutOfBoundsException)
        {
            return list
        }
    }

    /* fun getControlList(): ArrayList<ControlVO>
     {
         val list = ArrayList<ControlVO>()
         list.add(ControlVO("Fans", "field1", false, 32, "/'F", ControlVO.ControllerType.FAN))
         list.add(
             ControlVO(
                 "Motors",
                 "field2",
                 false,
                 100,
                 "ppm",
                 ControlVO.ControllerType.WATERPUMP
             )
         )
         list.add(
             ControlVO(
                 "Lights",
                 "field3",
                 false,
                 24,
                 "units",
                 ControlVO.ControllerType.LIGHT
             )
         )
         list.add(
             ControlVO(
                 "Humidifier",
                 "field4",
                 false,
                 120,
                 "ppm",
                 ControlVO.ControllerType.HUMIDIFIER
             )
         )
         return list
     }*/
}