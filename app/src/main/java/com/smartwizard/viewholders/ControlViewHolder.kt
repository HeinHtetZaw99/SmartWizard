package com.smartwizard.viewholders

import com.smartwizard.ControllerDelegate
import com.smartwizard.R
import com.smartwizard.databinding.CardviewControllerBinding
import com.smartwizard.vos.ControlVO


class ControlViewHolder(
    private var binding: CardviewControllerBinding,
    private var delegate: ControllerDelegate
) :
    BaseViewHolder<ControlVO>(binding.root)
{
    override fun setData(mData: ControlVO)
    {
        binding.data = mData
        binding.lightGaugeView.pointSize = mData.currentValue
        binding.lightNumberTv.text = mData.currentValue.toString()

        val bgImg = when
        {
            mData.controllerType == ControlVO.ControllerType.FAN -> R.drawable.ic_temperature_green
            mData.controllerType == ControlVO.ControllerType.HUMIDIFIER -> R.drawable.ic_humidity_green
            mData.controllerType == ControlVO.ControllerType.WATERPUMP -> R.drawable.ic_water_green
            else -> R.drawable.ic_light_green
        }

        binding.symbolImageView.setImageResource(bgImg)

        binding.onOffBtn.isChecked = mData.onOffStatus

        // Add a Switch state observer
        binding.onOffBtn.addSwitchObserver { _, isChecked ->
            delegate.onClickPowerBtn("1", isChecked, mData.controllerType)
        }
    }


}
