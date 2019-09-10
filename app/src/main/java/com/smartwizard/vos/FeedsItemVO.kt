package com.smartwizard.vos

import com.google.gson.annotations.SerializedName


data class FeedsItemVO(

    @field:SerializedName("field1")
    val field1: String? = "0.0",

    @field:SerializedName("field2")
    val field2: String =  "0.0",

    @field:SerializedName("field3")
    val field3: String? = "0.0",

    @field:SerializedName("field4")
    val field4: String? = "0.0",

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("entry_id")
    val entryId: Int? = null
)