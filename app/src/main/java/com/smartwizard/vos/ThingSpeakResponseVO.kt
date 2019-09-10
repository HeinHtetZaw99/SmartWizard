package com.smartwizard.vos


import com.google.gson.annotations.SerializedName

data class ThingSpeakResponseVO(

	@field:SerializedName("channel")
	val channel: ChannelVO? = null,

	@field:SerializedName("feeds")
	val feeds: List<FeedsItemVO?>? = null
)