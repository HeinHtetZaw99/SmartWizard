package com.smartwizard.vos

import com.google.gson.annotations.SerializedName


data class ChannelVO(

	@field:SerializedName("field1")
	val field1: String? = null,

	@field:SerializedName("field2")
	val field2: String? = null,

	@field:SerializedName("field3")
	val field3: String? = null,

	@field:SerializedName("field4")
	val field4: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("last_entry_id")
	val lastEntryId: Int? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)