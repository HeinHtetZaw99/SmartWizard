package com.smartwizard.models

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.smartwizard.activities.BaseActivity.Companion.showLog
import com.smartwizard.components.SingleEventLiveData
import com.smartwizard.network.SmartWizardAPI
import com.smartwizard.vos.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.ConnectException

class MainModel constructor(context: Context) : BaseModel(context)
{

    // Write a message to the database
    val database = FirebaseDatabase.getInstance()
    val HISTORY_REF = "history"
    val CONTROL_REF = "control"
    val CHANNEL_ID = "760892"

    val USER_ID = "110100"
    val smartWizardRef = database.getReference("message")


    private val API_WRITE_KEY = "E79E6CFBIBI5G9KS"
    private val API_READ_KEY = "25KZQFD9H5XB3NMC"

    private var apiClient: SmartWizardAPI? = createService(SmartWizardAPI::class.java)

    var mainError: SingleEventLiveData<ErrorVO> = SingleEventLiveData()

    companion object
    {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: MainModel? = null

        @Synchronized
        fun getInstance(): MainModel
        {
            if (INSTANCE == null)
            {
                throw RuntimeException("Model is being invoked before initialized")
            }
            return INSTANCE as MainModel
        }

        fun initModel(context: Context)
        {
            INSTANCE = MainModel(context)

        }
    }

    fun sendData(data: Int)
    {
        apiClient!!.updateField(apiKey = API_WRITE_KEY, field4 = data.toString())
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response>
            {
                override fun onComplete()
                {
                }

                override fun onSubscribe(d: Disposable)
                {
                }

                override fun onNext(t: Response)
                {
                    getLastEntry(data)
                    mainError.postValue(ErrorVO("Command sent", ErrorVO.TYPE.POSITIVE))
                }

                override fun onError(e: Throwable)
                {
                    Log.e("NTWERROR", e.message)
                    mainError.postValue(ErrorVO("Command failed to send", ErrorVO.TYPE.ERROR))
                }
            })
    }

    fun sendControl(controlData: FireBaseControlVO)
    {
        database.getReference(CONTROL_REF).child(USER_ID).setValue(controlData)
    }

    fun readControllDataFromFirebase(controlDataLD: MutableLiveData<FireBaseControlVO>)
    {
        database.getReference(CONTROL_REF).child(USER_ID)
            .addValueEventListener(object : ValueEventListener
            {
                override fun onCancelled(p0: DatabaseError)
                {
                    controlDataLD.value = FireBaseControlVO()
                }

                override fun onDataChange(snapshot: DataSnapshot)
                {
                    controlDataLD.value = snapshot.getValue(FireBaseControlVO::class.java)
                }

            })
    }

    fun readNotificationFromFirebase(notificationLD: MutableLiveData<FireBaseNotificationVO>)
    {
        database.getReference(CONTROL_REF).child(USER_ID)
            .addValueEventListener(object : ValueEventListener
            {
                override fun onCancelled(p0: DatabaseError)
                {
                    notificationLD.value = FireBaseNotificationVO()
                }

                override fun onDataChange(snapshot: DataSnapshot)
                {
                    notificationLD.value = snapshot.getValue(FireBaseNotificationVO::class.java)
                }

            })
    }

    fun readHistoryFromFirebase(historyLD: MutableLiveData<FireBaseHistoryVO>)
    {
        database.getReference(CONTROL_REF).child(USER_ID)
            .addValueEventListener(object : ValueEventListener
            {
                override fun onCancelled(p0: DatabaseError)
                {
                    historyLD.value = FireBaseHistoryVO()
                }

                override fun onDataChange(snapshot: DataSnapshot)
                {
                    historyLD.value = snapshot.getValue(FireBaseHistoryVO::class.java)
                }

            })
    }


    fun readHistory()
    {
        // Read from the database
        database.getReference(HISTORY_REF).addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(dataSnapshot: DataSnapshot)
            {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue(String::class.java)

            }

            override fun onCancelled(error: DatabaseError)
            {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }

    fun getLastEntry(data: Int)
    {
        apiClient!!.getLastCommand(API_WRITE_KEY).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LastEntryResponse>
            {
                override fun onComplete()
                {
                }

                override fun onSubscribe(d: Disposable)
                {
                }

                override fun onNext(t: LastEntryResponse)
                {
                    if (t.field4.equals(data.toString()))
                        mainError.postValue(ErrorVO("Successfully Executed", ErrorVO.TYPE.POSITIVE))
                    else
                        mainError.postValue(
                            ErrorVO(
                                "Failed in Executing the Command",
                                ErrorVO.TYPE.ERROR
                            )
                        )
                }

                override fun onError(e: Throwable)
                {
                    mainError.postValue(
                        ErrorVO(
                            "Failed in Executing the Command",
                            ErrorVO.TYPE.ERROR
                        )
                    )
                    showLog(e.message.toString())
                }
            })
    }

    fun readLastEntries(
        lastEntryLD: MutableLiveData<LastEntryResponse>,
        errorLD: SingleEventLiveData<ErrorVO>
    )
    {
        apiClient!!.readLastDataFields(
            channelID = CHANNEL_ID,
            apiKey = API_READ_KEY
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LastEntryResponse>
            {
                override fun onComplete()
                {
                    errorLD.value = ErrorVO("", ErrorVO.TYPE.POSITIVE)
                }

                override fun onSubscribe(d: Disposable)
                {
                }

                override fun onNext(t: LastEntryResponse)
                {
                    lastEntryLD.value = (t)
                }

                override fun onError(e: Throwable)
                {
                    showLog(e.message + " was encountered in getting last entry data from thingspeak")
                    handleErrors(e, errorLD)
                    if (e !is ConnectException)
                        readLastEntries(
                            lastEntryLD,
                            errorLD
                        ) // if the error is due to some other reason , retry
                }

            })
    }

    fun readSensorDataList(
        sensorLD: MutableLiveData<List<FeedsItemVO?>?>,
        errorLD: SingleEventLiveData<ErrorVO>
    )
    {
        apiClient!!.readFieldDataFromThingSpeak(
            channelID = CHANNEL_ID,
            entryNumber = "20",
            apiKey = API_READ_KEY
        )

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ThingSpeakResponseVO>
            {
                override fun onComplete()
                {
                    errorLD.value = ErrorVO("", ErrorVO.TYPE.POSITIVE)
                }

                override fun onSubscribe(d: Disposable)
                {
                }

                override fun onNext(t: ThingSpeakResponseVO)
                {
                    sensorLD.value = (t.feeds)
                }

                override fun onError(e: Throwable)
                {
                    showLog(e.message + " was encountered in getting data from thingspeak")
                    handleErrors(e, errorLD)
                    if (e !is ConnectException)
                        readSensorDataList(
                            sensorLD,
                            errorLD
                        ) // if the error is due to some other reason , retry
                }

            })
    }

    private fun handleErrors(e: Throwable, errorLD: SingleEventLiveData<ErrorVO>)
    {
        if (e is ConnectException)
            errorLD.value = ErrorVO("No Internet Connection", ErrorVO.TYPE.ERROR)
        else if (e is HttpException)
        {
            when (e.code())
            {
                400 -> errorLD.value = ErrorVO("Bad Request", ErrorVO.TYPE.ERROR)
                401 -> errorLD.value = ErrorVO("Authorization", ErrorVO.TYPE.ERROR)
                404 -> errorLD.value = ErrorVO("Resource Not Found", ErrorVO.TYPE.ERROR)
                405 -> errorLD.value = ErrorVO("Method are not allowed", ErrorVO.TYPE.ERROR)
                500 -> errorLD.value = ErrorVO("Internal server error", ErrorVO.TYPE.ERROR)
                502 -> errorLD.value = ErrorVO("Bad Gateway", ErrorVO.TYPE.ERROR)
                503 -> errorLD.value = ErrorVO("Service Unavailable", ErrorVO.TYPE.ERROR)

            }
        } else
            errorLD.value = ErrorVO("Error in Loading from servers", ErrorVO.TYPE.ERROR)

    }

    init
    {
        apiClient = createService(SmartWizardAPI::class.java)
    }

}