package com.smartwizard.activities

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.smartwizard.R
import kotlin.system.exitProcess

class SplashActivity : BaseActivity()
{

    var noInternetDialog: AlertDialog? = null
    override fun retry()
    {

    }

    val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


    }

    override fun onResume()
    {
        super.onResume()
        checkPrerequisitives()
    }

    fun checkPrerequisitives()
    {
        if (isOnline())
        {
            handler.postDelayed({
                startActivity(ChartActivity.newIntent(this))
                finish()
            }, 600)
        } else
        {
            //do something
            if (noInternetDialog == null)
                createNoInternetConnectionDialog()
            modifyWindowsParamsAndShow(
                noInternetDialog!!,
                getScreenWidth(this, 0.8),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }


    private fun createNoInternetConnectionDialog()
    {
        val noInternetDialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_no_internet_connection, null)
        noInternetDialog = createCustomDialog(
            this,
            noInternetDialogView,
            Gravity.CENTER,
            false,
            R.style.LanguageChooserDialogTheme
        )
        val exitBtn = noInternetDialogView.findViewById<TextView>(R.id.exitBtn)
        val retryBtn = noInternetDialogView.findViewById<TextView>(R.id.retryBtn)
        exitBtn.setOnClickListener {
            exitProcess(0)
        }
        retryBtn.setOnClickListener {
            noInternetDialog!!.dismiss()
            handler.postDelayed({ checkPrerequisitives() }, 1000)
        }


    }
}
