package com.smartwizard.vos

import com.google.gson.annotations.SerializedName

class FireBaseHistoryVO
{
    @SerializedName("description")
    var description: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("timestamp")
    var timestamp: String? = null

    @SerializedName("title")
    var title: String? = null

}