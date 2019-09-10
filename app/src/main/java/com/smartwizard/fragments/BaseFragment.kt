package com.smartwizard.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.material.appbar.AppBarLayout
import com.smartwizard.R
import com.smartwizard.activities.MainActivity
import com.smartwizard.components.Connectivity
import com.smartwizard.components.EmptyLoadingViewPod
import com.smartwizard.vos.ErrorVO

abstract class BaseFragment : Fragment()
{
    abstract fun onCreateViewModel() : ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        onCreateViewModel()
    }

    abstract fun onError()
    abstract fun loadData()




    /**Method used for handling fetching data from network and NO_NETWORK_CONNECTION cases */
    fun fetchDataFromNetwork(
        view: View,
        emptyLoadingView: EmptyLoadingViewPod?,
        appBarLayout: AppBarLayout?,
        isLoadingEnabled: Boolean
    )
    {
        if (Connectivity.isConnected(context))
        {
            emptyLoadingView!!.setCurrentState(if (isLoadingEnabled) EmptyLoadingViewPod.EMPTY_VIEW_STATE.LOADING else EmptyLoadingViewPod.EMPTY_VIEW_STATE.INITIAL)
            loadData()
        } else
        {
            if (emptyLoadingView != null)
            {
                emptyLoadingView!!.setCurrentState(EmptyLoadingViewPod.EMPTY_VIEW_STATE.NETWORK_ERROR)
            }
            onError()
            appBarLayout?.setExpanded(false, false)

            (activity as MainActivity).
            showSnackBar(
                view,
                ErrorVO(getString(R.string.error_msg_no_network), ErrorVO.TYPE.ERROR),
                getString(R.string.button_msg_ok)
            )
        }
    }

}
