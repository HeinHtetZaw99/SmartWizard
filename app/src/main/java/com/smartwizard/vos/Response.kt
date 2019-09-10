package com.smartwizard.vos

import com.google.gson.annotations.SerializedName


data class Response(

    @field:SerializedName("elevation") val elevation: String? = null,

    @field:SerializedName("latitude") val latitude: String? = null,

    @field:SerializedName("created_at") val createdAt: String? = null,

    @field:SerializedName("field1") val field1: String? = null,

    @field:SerializedName("field7") val field7: String? = null,

    @field:SerializedName("field6") val field6: String? = null,

    @field:SerializedName("field8") val field8: String? = null,

    @field:SerializedName("field3") val field3: String? = null,

    @field:SerializedName("channel_id") val channelId: Int? = null,

    @field:SerializedName("field2") val field2: String? = null,

    @field:SerializedName("entry_id") val entryId: Int? = null,

    @field:SerializedName("field5") val field5: String? = null,

    @field:SerializedName("field4") val field4: String? = null,

    @field:SerializedName("status") val status: String? = null,

    @field:SerializedName("longitude") val longitude: String? = null

)