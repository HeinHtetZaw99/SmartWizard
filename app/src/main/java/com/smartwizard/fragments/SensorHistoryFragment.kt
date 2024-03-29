package com.smartwizard.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.APIlib
import com.smartwizard.R
import com.smartwizard.adapters.ChartHistoryAdapter
import com.smartwizard.components.LineChart
import com.smartwizard.viewmodels.MainActivityViewModel
import com.smartwizard.vos.FeedsItemVO
import kotlinx.android.synthetic.main.fragment_sensor_history.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SensorHistoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SensorHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SensorHistoryFragment : BaseFragment()
{
    var viewModel : MainActivityViewModel? = null
    override fun onError()
    {
    }

    override fun loadData()
    {
    }

    override fun onCreateViewModel(): ViewModel =
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var adapter: ChartHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensor_history, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        adapter = ChartHistoryAdapter(context!!)
        var layoutManager = LinearLayoutManager(context!!)
//        sensorHistoryRv.layoutManager = layoutManager
//        sensorHistoryRv.adapter = adapter
        viewModel!!.sensorValueLD.observe(this , Observer {
//            processData(it)
        })

    }



    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri)
    {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener)
        {
            listener = context
        } else
        {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    private fun processData(it: List<FeedsItemVO>?)
    {
        val dataSet = HashMap<String ,String>()
        for ( entry in it!! ){
            dataSet["field1"] = entry.field1.toString()
        }
        showChart(dataSet)
    }


    fun showChart(data : HashMap<String , String>){
//        APIlib.getInstance().setActiveAnyChartView(sensorChartView)
//        sensorChartView.setProgressBar(progress_Bar_line)
//        LineChart.Builder().withContext(context!!)
//            .chart(sensorChartView)
//            .withMapData(data)
//            .withTitle(getString(R.string.title_sample_chart))
//            .withExplanation(resources.getString(R.string.map_label_explanation))
//            .show()
    }

    override fun onDetach()
    {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object
    {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SensorHistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SensorHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
