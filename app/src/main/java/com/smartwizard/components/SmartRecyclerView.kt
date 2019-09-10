package com.smartwizard.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.smartwizard.R


class SmartRecyclerView : RecyclerView
{

    private var mEmptyView: EmptyLoadingViewPod? = null

    private val dataObserver = object : RecyclerView.AdapterDataObserver()
    {
        override fun onChanged()
        {
            super.onChanged()
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int)
        {
            super.onItemRangeInserted(positionStart, itemCount)
            runLayoutAnimation()
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int)
        {
            super.onItemRangeRemoved(positionStart, itemCount)
            checkIfEmpty()
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?)
    {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(dataObserver)

        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(dataObserver)
        checkIfEmpty()
    }

    fun setEmptyView(emptyView: EmptyLoadingViewPod)
    {
        mEmptyView = emptyView
    }

    /**
     * check if adapter connected to SRV is empty. If so, show emptyView.
     */
    private fun checkIfEmpty()
    {
        val isEmpty = adapter!!.itemCount == 0
        if (mEmptyView != null)
        {
            mEmptyView!!.visibility = if (isEmpty) View.VISIBLE else View.INVISIBLE
            visibility = if (isEmpty) View.INVISIBLE else View.VISIBLE
            mEmptyView!!.toErrorView()
        }
    }

    fun runLayoutAnimation()
    {
        val context = this.context
        val controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        this.layoutAnimation = controller
        this.adapter!!.notifyDataSetChanged()
        this.scheduleLayoutAnimation()
    }

}
