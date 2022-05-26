package com.iranmobiledev.moodino.service

import android.app.Activity
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.view.MotionEventCompat
import com.iranmobiledev.moodino.NOTIFICATION_ID
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.ui.MainActivity
import com.iranmobiledev.moodino.utlis.ENGLISH
import com.iranmobiledev.moodino.utlis.LANGUAGE
import com.iranmobiledev.moodino.utlis.MyContextWrapper
import com.iranmobiledev.moodino.utlis.PERSIAN
import java.util.*

object EmojiNotification{
    var emoji : Int ?= null
}

class NotificationView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
        val attr = window.attributes
        attr.gravity = Gravity.TOP
        window.attributes = attr
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setContentView(R.layout.notification_view)


        val cardView = findViewById<CardView>(R.id.card_notificationView)
        val btnClose = findViewById<ImageView>(R.id.btn_notificationView_close)

        val rad = findViewById<LinearLayout>(R.id.radItem)
        val good = findViewById<LinearLayout>(R.id.goodItem)
        val meh = findViewById<LinearLayout>(R.id.mehItem)
        val bad = findViewById<LinearLayout>(R.id.badItem)
        val waful = findViewById<LinearLayout>(R.id.awfulItem)

        btnClose.setOnClickListener {

            val anim = ScaleAnimation(1f , 0f , 1f , 0f ,
            Animation.RELATIVE_TO_SELF  , 0.5f ,
            Animation.RELATIVE_TO_SELF , 0.5f)
            anim.duration = 500
            anim.fillAfter = true

            cardView.startAnimation(anim)
            anim.setAnimationListener(object :Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    notification(this@NotificationView)
                    finish()
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }

        rad.setOnClickListener {
            clickEmoji(5)
        }

        good.setOnClickListener {
            clickEmoji(4)
        }

        meh.setOnClickListener {
            clickEmoji(3)
        }

        bad.setOnClickListener {
            clickEmoji(2)
        }

        waful.setOnClickListener {
            clickEmoji(1)
        }

    }

    fun clickEmoji(emoji : Int){
        EmojiNotification.emoji = emoji
        val i = Intent(this , MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun attachBaseContext(newBase: Context?) {
        val shared = newBase?.getSharedPreferences("sharedPref", MODE_PRIVATE)
        val lan = when (shared?.getInt(LANGUAGE, PERSIAN)) {
            ENGLISH -> "en"
            PERSIAN -> "fa"
            else -> "en"
        }
        super.attachBaseContext(MyContextWrapper.wrap(newBase, Locale(lan).toString()))
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

        val notifiSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(context , notifiSound)
        r.play()

        notifiManager.notify(101 , notifi)
    }
}