package com.smartwizard.adapters

import android.content.Context
import android.view.ViewGroup
import com.smartwizard.*
import com.smartwizard.viewholders.BaseViewHolder
import com.smartwizard.viewholders.ControllerLayoutViewHolder
import com.smartwizard.viewholders.StatusViewHolder
import com.smartwizard.vos.BaseResponse
import com.smartwizard.vos.ControlListVO

class HomeAdapter(
    val context: Context,
    val delegate: ControllerDelegate
) :
    BaseRecyclerAdapter<BaseViewHolder<BaseResponse>, BaseResponse>(context)
{
    private val STATUS_VIEW_TYPE = R.layout.cardview_range_v2
    private val CONTROL_VIEW_TYPE = R.layout.layout_group

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseResponse>
    {
        val view = mLayoutInflator.inflate(viewType, parent, false)
        return (if (viewType == STATUS_VIEW_TYPE)
            StatusViewHolder(view)
        else
            ControllerLayoutViewHolder(view, delegate)) as BaseViewHolder<BaseResponse>


    }

    override fun getItemViewType(position: Int): Int
    {
        return when (mData!![position])
        {
            is StatusVO -> STATUS_VIEW_TYPE
            is ControlListVO -> CONTROL_VIEW_TYPE
            else -> throw RuntimeException("NO SUCH VIEW TYPES")
        }
    }
}