package com.smartwizard.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.smartwizard.R

import java.util.*

/**
 * created by Daniel McCoy @ 28th, May , 2019
 */
class SharePrefUtils private constructor(private val context: Context)
{
    private val sharedPreferences: SharedPreferences

    init
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun clearALL()
    {
        val keyList = ArrayList(EnumSet.allOf(KEYS::class.java))
        for (key in keyList)
        {
            sharedPreferences.edit().putString(context.getString(key.label), key.defaultValue)
                .apply()
        }
    }

    fun save(key: KEYS, value: String)
    {
        sharedPreferences.edit().putString(context.getString(key.label), value).apply()
    }

    fun load(key: KEYS): String?
    {
        return sharedPreferences.getString(context.getString(key.label), key.defaultValue)
    }


    enum class KEYB private constructor(val label: Int, val defaultValue: Boolean)
    {
//        IS_FIRST_TIME(R.string.SP_IS_FIRST_TIME, false)
    }

    /**
     * Define your keys here and set Default Values here
     * In case if u had more SP_VAlUES , just modify here
     */

    enum class KEYS private constructor(val label: Int, val defaultValue: String)
    {


    }

    companion object
    {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: SharePrefUtils? = null

        val instance: SharePrefUtils
            get() = (if (INSTANCE == null)
                throw RuntimeException("SharePrefUtils has not been initialized")
            else
                INSTANCE)!!

        fun init(context: Context)
        {
            if (INSTANCE == null)
                INSTANCE = SharePrefUtils(context)
        }
    }
}
