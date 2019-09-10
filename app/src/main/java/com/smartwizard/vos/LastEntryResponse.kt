package com.smartwizard.vos

import com.google.gson.annotations.SerializedName

data class LastEntryResponse
    (
    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("entry_id")
    val entryId: Int? = null,

    @field:SerializedName("field1")
    val field1: String? = null,

    @field:SerializedName("field2")
    val field2: String? = null,

    @field:SerializedName("field3")
    val field3: String? = null,

    @field:SerializedName("field4")
    val field4: String? = null
)
