package com.smartwizard.viewholders


import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder<W>(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener
{
//    protected var requestOptions: RequestOptions

    init
    {
        /*      requestOptions = RequestOptions()
              requestOptions.placeholder(R.drawable.placeholder_jewel)
              requestOptions.error(R.drawable.placeholder_jewel)*/
        itemView.setOnClickListener(this)
    }


    fun getScreenWidth(context: Context, percentage: Double): Int
    {
        val displaymetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
        return (displaymetrics.widthPixels * percentage).toInt()
    }

    override fun onClick(v: View)
    {
    }

    abstract fun setData(mData: W)
}
