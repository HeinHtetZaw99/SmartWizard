package com.smartwizard.components

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.smartwizard.R
import com.smartwizard.activities.ChartActivity
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import java.util.*


class NotificationHelper(private val mContext: Context)
{

    init
    {
        MDetect.init(mContext)
    }

    /**
     * Create and push the notification
     */
    fun createNotification(data: HashMap<String, String>, bitmap: Bitmap?)
    {
        val title = data["title"]
        val message = data["message"]
        var id = data["id"]


        val notiTitle: String
        val notiMessage: String

        notiTitle = if (title != null)
        {
            if (MDetect.isUnicode())
                title
            else
                Rabbit.uni2zg(title)
        } else
            mContext.getString(R.string.title_msg_noti_title)


        notiMessage = if (message != null)
        {
            if (MDetect.isUnicode())
                message
            else
                Rabbit.uni2zg(message)
        } else
            mContext.getString(R.string.title_msg_noti_message)


        val resultIntent = createIntent()


        val resultPendingIntent = PendingIntent.getActivity(
            mContext,
            0  /*Request code*/, resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        // Get the layouts to use in the custom notification
        /* RemoteViews notificationLayoutExpanded = new RemoteViews(mContext.getPackageName(), R.layout.custom_notification);
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_notification, null);
        ImageView logo = view.findViewById(R.id.appLogo);
        Glide.with(mContext).load(R.mipmap.ic_launcher).apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(30))).into(logo);
        */

        val mBuilder = buildNotificationBody(notiTitle, notiMessage, resultPendingIntent)

        if (bitmap != null)
        {
            val bpStyle = NotificationCompat.BigPictureStyle()
            bpStyle.bigPicture(bitmap).build()
            mBuilder.setStyle(bpStyle)
        }

        displayNotification(mBuilder)
    }


    private fun createIntent(): Intent
    {
        val resultIntent: Intent = ChartActivity.newIntent(mContext)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return resultIntent
    }

    private fun buildNotificationBody(
        notiTitle: String,
        notiMessage: String,
        resultPendingIntent: PendingIntent
    ): NotificationCompat.Builder
    {
        val mBuilder = NotificationCompat.Builder(
            mContext,
            mContext.getString(R.string.default_notification_channel_id)
        )
        mBuilder.setSmallIcon(R.drawable.ic_icon)
        mBuilder.setContentTitle(notiTitle)
            .setContentText(notiMessage)
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setContentIntent(resultPendingIntent)
        return mBuilder
    }

    private fun displayNotification(mBuilder: NotificationCompat.Builder)
    {
        val mNotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel =
                NotificationChannel(NOTIFICATION_CHANNEL_ID, TOPIC_NAME, importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build())
    }

    companion object
    {
        private val NOTIFICATION_CHANNEL_ID = "10001"

        private val TOPIC_NAME = "MOVIE_ADS"
        private var INSTANCE: NotificationHelper? = null

        fun initNotiHelper(context: Context)
        {
            if (INSTANCE == null)
                INSTANCE = NotificationHelper(context)
        }

        val instance: NotificationHelper
            @Synchronized get() = if (INSTANCE == null)
                throw RuntimeException("NotificationHelper is not initialized yet")
            else
                INSTANCE!!
    }
}
