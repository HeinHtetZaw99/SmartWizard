package com.smartwizard.vos

import com.google.gson.annotations.SerializedName

class FireBaseNotificationVO
{
    @SerializedName("code")
    var code: Int = 0
    @SerializedName("data")
    var data: String? = null

    public fun getNotificationMap(): HashMap<String, String>
    {
        val data = HashMap<String, String>()
        when (this.code)
        {
            0 ->
            {
                data["title"] = "Smart Wizard is Running "
                data["message"] = "Smart Wizard is protecting your play-ground"
            }
            200 ->
            {
                data["title"] = "Smart Wizard is Running "
                data["message"] = "Smart Wizard is protecting your play-ground"
            }
            411
            ->
            {
                data["title"] = "Undesired Temperature Readings "
                data["message"] = "Current temperature reading is ${this.data}"
            }
            422
            ->
            {
                data["title"] = "Undesired moisture Reading"
                data["message"] = "Current moisture reading is ${this.data}"
            }
            433
            ->
            {
                data["title"] = "Undesired Lighting Reading"
                data["message"] = "Current luminous reading is ${this.data}"
            }
            444
            ->
            {
                data["title"] = "Undesired Soil-Moisture Reading"
                data["message"] = "Current soil-moisture reading is ${this.data}"
            }
        }
        return data
    }
}