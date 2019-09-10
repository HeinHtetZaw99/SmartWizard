package com.smartwizard.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.smartwizard.R
import com.smartwizard.fragments.DeviceLogHistoryFragment
import com.smartwizard.fragments.SensorHistoryFragment
import java.util.*

class TabsAdapter(private val fragmentManager: FragmentManager, context: Context) :
    FragmentPagerAdapter(fragmentManager)
{


    private val sensorHistoryFragment = SensorHistoryFragment()
    private val deviceLogHistoryFragment = DeviceLogHistoryFragment()

    private val fragmentList = ArrayList<Fragment>()
    /* private SportsFragment sportsFragment = new SportsFragment();*/
    private val tabTitles: Array<String> = arrayOf(
        context.getString(R.string.tab_title_sensor_history),
        context.getString(R.string.tab_title_device_log_history)
    )

    init
    {
        fragmentList.add(sensorHistoryFragment)
        fragmentList.add(deviceLogHistoryFragment)
    }

    fun refreshFragments()
    {
        val fragmentTransaction = fragmentManager.beginTransaction()
        for (fragment in fragmentList)
        {
            fragmentTransaction.detach(fragment)
            fragmentTransaction.attach(fragment)
            fragmentTransaction.commit()
        }
    }


    // overriding getPageTitle()
    override fun getPageTitle(position: Int): CharSequence?
    {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment?
    {
        return when (position)
        {
            0 -> sensorHistoryFragment
            1 -> deviceLogHistoryFragment
            else -> null
        }
    }

    override fun getCount(): Int
    {
        return tabTitles.size
    }
}
