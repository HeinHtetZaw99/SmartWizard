package com.smartwizard.vos

import com.smartwizard.StatusVO

data class ResponseVO(

    private var status : StatusVO ,
    private var controlListVO: ControlListVO
)
{

}