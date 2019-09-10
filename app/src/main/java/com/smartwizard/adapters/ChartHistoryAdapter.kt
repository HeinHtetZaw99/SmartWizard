package com.smartwizard.adapters

import android.content.Context
import android.view.ViewGroup
import com.smartwizard.R
import com.smartwizard.viewholders.ChartHistoryViewHolder
import com.smartwizard.vos.TSHistoryVO

class ChartHistoryAdapter(context: Context) :
    BaseRecyclerAdapter<ChartHistoryViewHolder, TSHistoryVO>(context)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartHistoryViewHolder
    {
        val view = mLayoutInflator.inflate(R.layout.cardview_item_chart, parent, false)
        return ChartHistoryViewHolder(view)
    }

}