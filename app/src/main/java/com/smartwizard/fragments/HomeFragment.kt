package com.smartwizard.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.smartwizard.ControllerDelegate
import com.smartwizard.R
import com.smartwizard.adapters.HomeAdapter
import com.smartwizard.viewmodels.MainActivityViewModel
import com.smartwizard.vos.BaseResponse
import com.smartwizard.vos.ControlListVO
import com.smartwizard.vos.ControlVO
import com.smartwizard.vos.FireBaseControlVO
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(), ControllerDelegate
{

    var code = 1000;
    var humitifierOn = false
    var fansOn = false
    var waterPumpOn = false


    private lateinit var viewModel: MainActivityViewModel


    override fun onClickPowerBtn(
        id: String,
        onOffStatus: Boolean,
        controllerType: ControlVO.ControllerType
    )
    {


        sendDataToServer()

    }

    private fun sendDataToServer()
    {
//        viewModel.sendData(controlData = controlVO)
    }

    lateinit var homeAdapter: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
//         Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter(view.context, this)
        val layoutManager = LinearLayoutManager(view.context)
        view.mainRv.layoutManager = layoutManager
        view.mainRv.adapter = homeAdapter

        Glide.with(view.userImageIv)
            .load(R.drawable.dummy_user_photo)
            .apply(RequestOptions().centerCrop().transform(CircleCrop()))
            .into(view.userImageIv)

        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]

        val data = ArrayList<BaseResponse>()
//        data.add(StatusVO("","","",""))
//        data.add(ControlListVO())
        homeAdapter.appendNewData(data)
    }


}
