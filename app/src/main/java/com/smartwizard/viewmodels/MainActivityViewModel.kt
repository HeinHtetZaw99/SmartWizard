package com.smartwizard.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.smartwizard.components.SingleEventLiveData
import com.smartwizard.models.MainModel
import com.smartwizard.vos.*
import io.reactivex.Observable
import java.util.*

class MainActivityViewModel : BaseViewModel()
{
    val sensorValueLD: MutableLiveData<List<FeedsItemVO?>?> by lazy { MutableLiveData<List<FeedsItemVO?>?>() }
    val errorLD: SingleEventLiveData<ErrorVO> by lazy { SingleEventLiveData<ErrorVO>() }
    val lastEntryLD: MutableLiveData<LastEntryResponse> by lazy { MutableLiveData<LastEntryResponse>() }
    val histroyLD: MutableLiveData<FireBaseHistoryVO> by lazy { MutableLiveData<FireBaseHistoryVO>() }
    val firebaseControlLD: MutableLiveData<FireBaseControlVO> by lazy { MutableLiveData<FireBaseControlVO>() }
    val notificationLD: MutableLiveData<FireBaseNotificationVO> by lazy { MutableLiveData<FireBaseNotificationVO>() }


    fun sendData(controlData: FireBaseControlVO)
    {
        MainModel.getInstance().sendControl(controlData)
    }



    fun getFieldsData()
    {
        MainModel.getInstance().readLastEntries(lastEntryLD, errorLD)
        MainModel.getInstance().readSensorDataList(sensorValueLD, errorLD)
        MainModel.getInstance().readHistoryFromFirebase(histroyLD)
        MainModel.getInstance().readControllDataFromFirebase(firebaseControlLD)
        MainModel.getInstance().readNotificationFromFirebase(notificationLD)
    }

}