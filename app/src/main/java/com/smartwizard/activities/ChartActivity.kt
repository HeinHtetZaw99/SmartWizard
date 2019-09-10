package com.smartwizard.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.APIlib
import com.anychart.AnyChartView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.smartwizard.ControllerDelegate
import com.smartwizard.R
import com.smartwizard.adapters.HomeAdapter
import com.smartwizard.components.LineChart
import com.smartwizard.components.NotificationHelper
import com.smartwizard.viewmodels.MainActivityViewModel
import com.smartwizard.vos.*
import kotlinx.android.synthetic.main.activity_chart.*
import java.util.*
import kotlin.collections.ArrayList

class ChartActivity : AppCompatActivity(), ControllerDelegate
{
    lateinit var chartView: AnyChartView
    var viewModel: MainActivityViewModel? = null
    var controlVO: FireBaseControlVO = FireBaseControlVO()

    lateinit var homeAdapter: HomeAdapter
    val fieldDataList = ArrayList<Int>()
    val fieldOnOffList = ArrayList<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        chartView = findViewById(R.id.sensorChartView)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        NotificationHelper.initNotiHelper(this)

        viewModel!!.sensorValueLD.observe(this, androidx.lifecycle.Observer {
            processData(it as List<FeedsItemVO>?)
        })
        viewModel!!.getFieldsData()

        viewModel!!.lastEntryLD.observe(this, androidx.lifecycle.Observer {
            waterNumberTv.text = it.field3 ?: "N/A"
            temperatureNumberTv.text = it.field1 ?: "N/A"
            humidityNumberTv.text = it.field2 ?: "N/A"

            fieldDataList.clear()
            fieldDataList.add(it.field1!!.toInt())
            fieldDataList.add(it.field3!!.toInt())
            fieldDataList.add(it.field4!!.toInt())
            fieldDataList.add(it.field2!!.toInt())


            val data = ArrayList<BaseResponse>()
            data.add(ControlListVO(fieldDataList, FireBaseControlVO()))
            homeAdapter.appendNewData(data)
        })


        viewModel!!.firebaseControlLD.observe(this, androidx.lifecycle.Observer {
            this.controlVO = it
            val data = ArrayList<BaseResponse>()
            data.add(ControlListVO(getSensorReadings(), controlVO))
        })

        viewModel!!.notificationLD.observe(this, androidx.lifecycle.Observer {
            NotificationHelper.instance.createNotification(it.getNotificationMap(), null)
        })

        homeAdapter = HomeAdapter(this, this)
        val layoutManager = LinearLayoutManager(this)
        mainRv.layoutManager = layoutManager
        mainRv.adapter = homeAdapter

        Glide.with(userImageIv)
            .load(R.drawable.dummy_user_photo)
            .apply(RequestOptions().centerCrop().transform(CircleCrop()))
            .into(userImageIv)


        val data = ArrayList<BaseResponse>()
//        data.add(StatusVO("","","",""))
        data.add(ControlListVO(fieldDataList, FireBaseControlVO()))
        homeAdapter.appendNewData(data)
        /*  historyTabLayout.addTab(historyTabLayout.newTab().setText(getString(R.string.tab_title_sensor_history)))
          historyTabLayout.addTab(historyTabLayout.newTab().setText(getString(R.string.tab_title_device_log_history)))
          val tabsAdapter = TabsAdapter(childFragmentManager, context!!)
          historyViewPagerLayout.adapter = tabsAdapter

          historyTabLayout.setupWithViewPager(historyViewPagerLayout)*/

        /*   dobPicker = SelectDateFragment(this)
           dobPicker.isCancelable = true

           datePickerBtn.setOnClickListener {
               dobPicker.show(fragmentManager, this.getString(R.string.title_msg_select_date))
           }*/

    }

    private fun processData(it: List<FeedsItemVO>?)
    {
        val dataSet = HashMap<String, String>()
        for (entry in it!!)
        {
            dataSet[entry.createdAt.toString()] =
                "${entry.field1}:${entry.field2}:${entry.field3}:${entry.field4}"
        }
        showChart(dataSet)
    }


    private fun showChart(data: HashMap<String, String>)
    {
        APIlib.getInstance().setActiveAnyChartView(chartView)
        chartView.setProgressBar(progressBarline)
        LineChart.Builder().withContext(this)
            .chart(chartView)
            .withMapData(data)
            .withTitle(getString(R.string.title_sample_chart))
            .withExplanation(resources.getString(R.string.map_label_explanation))
            .show()
    }


    override fun onClickPowerBtn(
        id: String,
        onOffStatus: Boolean,
        controllerType: ControlVO.ControllerType
    )
    {

        when (controllerType)
        {
            ControlVO.ControllerType.FAN ->
            {
                controlVO.isFanOn = onOffStatus
            }
            ControlVO.ControllerType.HUMIDIFIER ->
            {
                controlVO.isHumidifierOn = onOffStatus
            }
            ControlVO.ControllerType.WATERPUMP ->
            {
                controlVO.isWaterPumpOn = onOffStatus
            }
            ControlVO.ControllerType.LIGHT ->
            {
                controlVO.isLightOn = onOffStatus
            }

        }
        sendDataToServer()

    }

    private fun getSensorReadings(): ArrayList<Int>
    {
        if (fieldDataList.size == 0)
        {
            for (i in 1..4)
            {
                fieldDataList.add(0)
            }
        }
        return fieldDataList
    }

    private fun String.toInt(): Int = toString().toFloat().toInt()

    private fun sendDataToServer()
    {
        viewModel!!.sendData(controlData = controlVO)
    }

    companion object
    {
        fun newIntent(context: Context) = Intent(context, ChartActivity::class.java)
    }
}
