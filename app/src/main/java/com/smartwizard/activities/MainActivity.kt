package com.smartwizard.activities

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smartwizard.fragments.AccountFragment
import com.smartwizard.fragments.HistoryFragment
import com.smartwizard.fragments.HomeFragment
import com.smartwizard.R
import com.smartwizard.components.SingleEventLiveData
import com.smartwizard.viewmodels.MainActivityViewModel
import com.smartwizard.vos.ErrorVO
import com.smartwizard.vos.LastEntryResponse
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity()
{
    override fun retry()
    {

    }

    lateinit var viewModel: MainActivityViewModel
    private var homeFragment = HomeFragment()
    private var historyFragment = HistoryFragment()
    private var accountFragment = AccountFragment()
    private var fm = supportFragmentManager
    private var activeFragment: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.getFieldsData()

        buildFragmentList()
        activeFragment = homeFragment
        viewModel.errorLD
            .observe(this, object : SingleEventLiveData<ErrorVO>(), Observer<ErrorVO>
            {
                override fun onChanged(error: ErrorVO?)
                {
                    showSnackBar(rootMainLayout, error!! , "ok")
                }
            })

        viewModel.lastEntryLD.observe(this , Observer<LastEntryResponse> {

        })

        disableShiftMode(bottomNavigationView)
        bottomNavigationView
            .setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId)
                {
                    R.id.home ->
                    {
                        fm.beginTransaction().hide(activeFragment!!)
                            .show(homeFragment).commit()

                        activeFragment = homeFragment
//                        bottomNavigationView.selectedItemId = R.id.home
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.history ->
                    {
                        fm.beginTransaction().hide(activeFragment!!)
                            .show(historyFragment).commit()

                        activeFragment = historyFragment
//                        bottomNavigationView.selectedItemId = R.id.history
                       return@setOnNavigationItemSelectedListener true
                    }
                    R.id.account ->
                    {
                        fm.beginTransaction().hide(activeFragment!!)
                            .show(accountFragment).commit()

                        activeFragment = accountFragment
//                        bottomNavigationView.selectedItemId = R.id.history
                        return@setOnNavigationItemSelectedListener true
                    }

                }
                return@setOnNavigationItemSelectedListener false
            }


//        var drawable : Drawable?  = null
        Glide.with(this)
            .load(R.drawable.dummy_user_photo)
            .apply(RequestOptions().centerCrop().transform(CircleCrop()))
            .listener(object : RequestListener<Drawable>
            {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean
                {
                    val menu = bottomNavigationView.menu
                    menu.findItem(R.id.account).icon = resource!!
//                    drawable = resource!!
                    return true
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean
                {
                    return false
                }
            })

//        bottomNavigationView.menu.findItem(R.id.account).icon= Resources.getSystem().getDrawable(R.drawable.dummy_user_photo)
//        findPrimeP(10.000, 2000000)


    }


    private fun buildFragmentList()
    {
        fm.beginTransaction().add(R.id.fragmentFrameLayout, homeFragment, "0")
            .commitAllowingStateLoss()
        fm.beginTransaction().add(R.id.fragmentFrameLayout, historyFragment, "1")
            .hide(historyFragment).commitAllowingStateLoss()
        fm.beginTransaction().add(R.id.fragmentFrameLayout, accountFragment, "2")
            .hide(accountFragment).commitAllowingStateLoss()
    }

    @SuppressLint("RestrictedApi")
    fun disableShiftMode(view: BottomNavigationView)
    {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try
        {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount)
            {
                val item = menuView.getChildAt(i) as BottomNavigationItemView

                item.setShifting(false)
                // set once again checked value, so view will be updated

                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e("BNVHelper", "Unable to get shift mode field", e)
        } catch (e: IllegalAccessException) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e)
        }

    }

}
