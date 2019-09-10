package com.smartwizard

import android.app.Application
import com.smartwizard.models.MainModel

class SmartWizardAPP : Application() {
    override fun onCreate() {
        super.onCreate()
        MainModel.initModel(applicationContext)
    }
}