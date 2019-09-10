package com.smartwizard

import com.smartwizard.vos.BaseResponse

class StatusVO(
    private var currentTemperature: String,
    private var currentHumidity: String,
    private var currentWaterLvl: String,
    private var currentLight: String
) : BaseResponse
