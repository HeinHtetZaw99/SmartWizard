package com.smartwizard.models

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.smartwizard.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

open class BaseModel(context: Context)
{

//    https://api.thingspeak.com/channels/<channel_id>/fields/<field_id>.<format>
    private val BASE_URL = "https://api.thingspeak.com/channels/"
    private var sharedPreferences: SharedPreferences? = null

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //TODO remove logging interceptor when about to release

    private val httpClientBuilder = OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS).connectTimeout(5, TimeUnit.SECONDS).cache(null).addInterceptor(logger)

    private val gson = GsonBuilder().setLenient().create()


    private val retrofitBuilder = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())).addConverterFactory(GsonConverterFactory.create(gson)).client(httpClientBuilder.build())

    init
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }


     fun <T> createService(serviceClass: Class<T>, authToken: String?): T {
        if (!TextUtils.isEmpty(authToken)) {
            val interceptor = AuthenticationInterceptor(authToken)
            if (!httpClientBuilder.interceptors().contains(interceptor)) {
                httpClientBuilder.addInterceptor(interceptor)
                retrofitBuilder.client(httpClientBuilder.build())
            }
        }
        return retrofitBuilder.build().create(serviceClass)
    }

    fun <T> createService(serviceClass: Class<T>): T
    {
        return retrofitBuilder.build().create(serviceClass)
    }

}

class AuthenticationInterceptor(authToken: String?) : Interceptor
{

    private var authToken: String? = null

    /**
     * For Adding @app_token in the header
     */
    fun AuthenticationInterceptor(authToken: String?) {
        this.authToken = authToken

    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder().addHeader("app_token", authToken!!)
        val request = builder.build()
        return chain.proceed(request)
    }
}

