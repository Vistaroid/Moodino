package com.iranmobiledev.moodino.service

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.utlis.ENGLISH
import com.iranmobiledev.moodino.utlis.LANGUAGE
import com.iranmobiledev.moodino.utlis.MyContextWrapper
import com.iranmobiledev.moodino.utlis.PERSIAN
import java.util.*


class NotificationView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
        val attr = window.attributes
        attr.gravity = Gravity.TOP
        window.attributes = attr
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setContentView(R.layout.notification_view)



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
}