package com.smartwizard.network

import com.smartwizard.vos.LastEntryResponse
import com.smartwizard.vos.Response
import com.smartwizard.vos.ThingSpeakResponseVO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SmartWizardAPI
{
    @GET("update.json")
    fun updateField(@Query("api_key") apiKey: String, @Query("field4") field4: String): Observable<Response>

    @GET("channels/760892/fields/4/last.json")
    fun getLastCommand(@Query("api_key") apiKey: String): Observable<LastEntryResponse>

    //    https://api.thingspeak.com/channels/<channel_id>/fields/<field_id>.<format>
//    @GET("channels/{channel_id}/fields/{field_name}.json?result=50")
//    fun readFieldDataFromThingSpeak(
//        @Path("channel_id") channelID: String,
//        @Path("field_name") fieldName: String,
//        @Query("api_key") apiKey: String,
//        @Query("results") entryNumber: String
//    ): Observable<ThingSpeakResponseVO>

    @GET("channels/{channel_id}/feeds.json?")
    fun readFieldDataFromThingSpeak(
        @Path("channel_id") channelID: String,
        @Query("api_key") apiKey: String,
        @Query("results") entryNumber: String
    ): Observable<ThingSpeakResponseVO>

    //    GET https://api.thingspeak.com/channels/9/feeds/last.json
    @GET("channels/{channel_id}/feeds/last.json")
    fun readLastDataFields(
        @Path("channel_id") channelID: String,
        @Query("api_key") apiKey: String
    ): Observable<LastEntryResponse>
}