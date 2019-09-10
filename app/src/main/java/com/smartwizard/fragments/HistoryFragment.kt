package com.smartwizard.fragments


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.anychart.APIlib
import com.smartwizard.R
import com.smartwizard.components.LineChart
import com.smartwizard.viewmodels.MainActivityViewModel
import com.smartwizard.vos.FeedsItemVO
import kotlinx.android.synthetic.main.fragment_history.*
import java.text.SimpleDateFormat
import java.util.*


@Suppress("UNCHECKED_CAST")
class HistoryFragment : Fragment(), DatePickerDialog.OnDateSetListener
{
    var viewModel: MainActivityViewModel? = null
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int)
    {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        @SuppressLint("SimpleDateFormat") val dateTimeFormatter = SimpleDateFormat("dd/MM/yyyy")
        val currentDateString = dateTimeFormatter.format(calendar.time)
        datePickerBtn.text = currentDateString
    }

    private lateinit var dobPicker: DialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel!!.sensorValueLD.observe(this, androidx.lifecycle.Observer {
            processData(it as List<FeedsItemVO>?)
        })
        /*  historyTabLayout.addTab(historyTabLayout.newTab().setText(getString(R.string.tab_title_sensor_history)))
          historyTabLayout.addTab(historyTabLayout.newTab().setText(getString(R.string.tab_title_device_log_history)))
          val tabsAdapter = TabsAdapter(childFragmentManager, context!!)
          historyViewPagerLayout.adapter = tabsAdapter

          historyTabLayout.setupWithViewPager(historyViewPagerLayout)*/

        dobPicker = SelectDateFragment(this)
        dobPicker.isCancelable = true

        datePickerBtn.setOnClickListener {
            dobPicker.show(fragmentManager, this.getString(R.string.title_msg_select_date))
        }
    }

    private fun processData(it: List<FeedsItemVO>?)
    {
        val dataSet = HashMap<String, Int>()
        for (entry in it!!)
        {
            try
            {
                dataSet[entry.createdAt.toString()] = (entry.field1.toString()).toInt()
            } catch (nfe: NumberFormatException)
            {
                dataSet["field1"] = 0
            }

        }
        showChart(dataSet)
    }


    private fun showChart(data: HashMap<String, Int>)
    {
//        APIlib.getInstance().setActiveAnyChartView(sensorChartView)
//        sensorChartView.setProgressBar(progress_Bar_line)
//        LineChart.Builder().withContext(context!!)
//            .chart(sensorChartView)
//            .withMapData(data)
//            .withTitle(getString(R.string.title_sample_chart))
//            .withExplanation(resources.getString(R.string.map_label_explanation))
//            .show()
    }
}
