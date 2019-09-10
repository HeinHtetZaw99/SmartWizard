package com.smartwizard

import com.smartwizard.vos.ControlVO

interface ControllerDelegate
{
    fun onClickPowerBtn(id: String, onOffStatus: Boolean, controllerType: ControlVO.ControllerType)

}
