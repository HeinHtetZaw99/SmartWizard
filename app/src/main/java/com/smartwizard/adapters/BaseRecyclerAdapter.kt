package com.smartwizard.adapters


import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.smartwizard.viewholders.BaseViewHolder
import java.util.*


abstract class BaseRecyclerAdapter<T : BaseViewHolder<W>, W>(context: Context) :
    RecyclerView.Adapter<T>()
{

    protected var mData: MutableList<W>? = null
    protected var mLayoutInflator: LayoutInflater

    val items: List<W>
        get() = (if (mData == null) ArrayList() else mData)!!

    init
    {
        mData = ArrayList()
        mLayoutInflator = LayoutInflater.from(context)
    }


    override fun onBindViewHolder(holder: T, position: Int)
    {
        if (!mData!!.isEmpty())
            holder.setData(mData!![position])
        //        holder.setTrendingList(new ProductVO());
    }

    override fun getItemCount(): Int
    {

        return mData!!.size
    }

    fun getItemAt(position: Int): W?
    {
        return if (position < mData!!.size - 1) mData!![position] else null

    }

    fun addNewData(newItem: W, position: Int)
    {
        if (mData != null)
        {
            mData!!.add(position, newItem)
            notifyDataSetChanged()
        }
    }

    fun appendNewData(newData: List<W>)
    {
        clearData()
        mData!!.addAll(newData)
        notifyDataSetChanged()
    }

    fun removeData(data: W)
    {
        mData!!.remove(data)
        notifyDataSetChanged()
    }

    fun addNewData(data: W)
    {
        mData!!.add(data)
        notifyDataSetChanged()
    }

    fun addNewDataList(dataList: List<W>)
    {
        mData!!.addAll(dataList)
        notifyDataSetChanged()
    }

    fun clearData()
    {
        mData = ArrayList()
        notifyDataSetChanged()
    }

}
