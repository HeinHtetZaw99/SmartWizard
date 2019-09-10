package com.smartwizard.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.smartwizard.viewholders.ControlViewHolder
import com.smartwizard.ControllerDelegate
import com.smartwizard.R
import com.smartwizard.databinding.CardviewControllerBinding
import com.smartwizard.vos.ControlVO

class ControllerAdapter(context: Context , var delegate: ControllerDelegate) :
    BaseRecyclerAdapter<ControlViewHolder, ControlVO>(context)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControlViewHolder
    {
        val binding  = DataBindingUtil.inflate<CardviewControllerBinding>(mLayoutInflator,R.layout.cardview_controller,parent,false)
        return ControlViewHolder(binding = binding, delegate = delegate)
    }

}