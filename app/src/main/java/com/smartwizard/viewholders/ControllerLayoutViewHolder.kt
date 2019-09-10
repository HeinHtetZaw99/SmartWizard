package com.smartwizard.viewholders

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.smartwizard.ControllerDelegate
import com.smartwizard.adapters.ControllerAdapter
import com.smartwizard.vos.ControlListVO
import kotlinx.android.synthetic.main.layout_group.view.*

class ControllerLayoutViewHolder(val view: View, private val delegate: ControllerDelegate) : BaseViewHolder<ControlListVO>(view)
{
    override fun setData(mData: ControlListVO)
    {
        val controllerAdapter = ControllerAdapter(view.context, delegate)
        view.layoutRv.layoutManager = GridLayoutManager(view.context , 2)
        view.layoutRv.adapter =controllerAdapter
        controllerAdapter.appendNewData(mData.getControlList())
    }

}
