package com.smartwizard.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.smartwizard.R
import com.smartwizard.vos.ErrorVO
import java.util.*
import kotlin.collections.HashMap

abstract class BaseActivity : AppCompatActivity()
{

    abstract fun retry()

    /**For Showing snackbar with action btn
     * @param view -> parent view where the snackbar will be displayed
     * @param errorVO -> error data with status
     * @param buttonText -> the text for action button of snackbar
     */
    fun showSnackBar(view: View, errorVO: ErrorVO, buttonText: String)
    {
        val snackbar: Snackbar
        val message: String = getErrorMsg(errorVO)

        if (errorVO.errorType === ErrorVO.TYPE.ERROR)
        {
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.error_red))
            snackbar.setAction(buttonText) {
                retry()
                snackbar.dismiss()
            }
            snackbar.setActionTextColor(ContextCompat.getColor(view.context, R.color.white))
        } else
        {
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            snackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
            snackbar.setActionTextColor(ContextCompat.getColor(view.context, R.color.greensea))
        }
        snackbar.show()
    }

    fun isOnline(): Boolean
    {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    var keygenList = HashMap<Int, Int>()

    /*  fun findPrimeP(start: Int, end: Int)
      {
          //finding prime p
          for (entry in start..end)
          {
              if (isPrime(entry)){
                  val p = entry
                  val qList = findPrimeQ(start,p)
                  for(q in qList){
                      val remainder = (p-1)% q
                      if(remainder==0 && q>10000)
                          keygenList[p] = q
                      Log.d("PQ","key : ${p} && value : ${q}")
                  }
              }
          }
          if(keygenList.isEmpty())
              Log.d("PQ","no key lists")
          else
          for (keys in keygenList){
              Log.d("PQ","key : ${keys.key} && value : ${keys.value}")
          }
      }

      fun findPrimeQ(start: Int, end: Int): ArrayList<Int>
      {
          val primeQList = ArrayList<Int>()
          //finding prime q
          for (entry in start..end)
          {
              if (isPrime(entry)){
                primeQList.add(entry)
              }
          }
          return primeQList
      }

      fun isPrime(entry: Int): Boolean
      {
          var m = entry / 2
          var flag = 0
          if (entry == 0 || entry == 1)
          {
  //                System.out.println("$entry  is not prime number");
          } else
          {
              for (i in 2..m)
              {
                  if (entry % i == 0)
                  {
  //                    System.out.println("$entry  is not prime number");
                     flag = 1
                  }
              }
          }
          return flag==0
      }
  */

    /**helper method for returning String resources from ErrorVO
     * ErrorVO.getErrorMsg() can be either a String or a String Resource ID ( For Localization Purpose) */
    fun getErrorMsg(errorVO: ErrorVO): String = if (errorVO.errorMsg is Int)
        getString(errorVO.errorMsg as Int)
    else
        errorVO.errorMsg as String


    companion object
    {
        fun showLog(message: String)
        {
            Log.d("SWTAG", message)
        }
    }

    /**Method for modifying and overriding alertDialogs width and height as default width and height is almost 95% */
    fun modifyWindowsParamsAndShow(dialog: AlertDialog, width: Int, height: Int)
    {
        /*        WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
        lWindowParams.copyFrom(dialog.getWindow().getAttributes());
        lWindowParams.width = width; // this is where the magic happens
        lWindowParams.height = height;*/
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.show()// I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
        //        dialog.getWindow().setAttributes(lWindowParams);
        dialog.window!!.setLayout(width, height)
    }

    /**Method for creating dialogs with custom views
     * @param view -> view inflated with desired layout
     * @param gravity -> gravity of the dialog to be displayed
     * @param cancelable -> whether the dialog is cancelable or not by touching outside of the dialog
     * @param theme -> style for the dialog
     */
    fun createCustomDialog(
        context: Context,
        view: View,
        gravity: Int,
        cancelable: Boolean,
        theme: Int
    ): AlertDialog
    {
        val dialog = AlertDialog.Builder(context, theme).create()
        dialog.setView(view)
        val window = dialog.window
        Objects.requireNonNull(window).setGravity(gravity)
        window!!.setBackgroundDrawableResource(R.color.black20)
        window.attributes.windowAnimations = R.style.MyAlertDialogTheme //style id
        dialog.setCancelable(cancelable)
        return dialog
    }


    /**method for getting the current screen's width with desired percentage */
    fun getScreenWidth(context: Context, percentage: Double): Int
    {
        val displaymetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
        return (displaymetrics.widthPixels * percentage).toInt()
    }

}
