package com.iranmobiledev.moodino.service

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.iranmobiledev.moodino.NOTIFICATION_ID
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.repository.more.source.POPUP_REMIDER
import com.iranmobiledev.moodino.ui.MainActivity


class AlarmReciver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val shared = context?.getSharedPreferences("sharedPref", AppCompatActivity.MODE_PRIVATE)
        val popup = shared?.getBoolean(POPUP_REMIDER , true)

        if (popup == true){
            val i = Intent()
            i.setClassName(context.packageName , "com.iranmobiledev.moodino.service.NotificationView")
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
        else notification(context!!)

        val notifiSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(context , notifiSound)
        r.play()
    }

    fun notification(context : Context){


        val intent = Intent(context , MainActivity::class.java)
        val content = PendingIntent.getActivity(context , 1 , intent , PendingIntent.FLAG_MUTABLE)

        val notifiManager = context.getSystemService(Application.NOTIFICATION_SERVICE) as NotificationManager

        val notifi = NotificationCompat.Builder(context, NOTIFICATION_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.how_are_you))
            .setContentIntent(content)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(500,500,500,500))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
            .build()

        notifiManager.notify(101 , notifi)
    }

}