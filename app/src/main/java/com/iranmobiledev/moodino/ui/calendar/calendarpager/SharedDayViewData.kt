package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.graphics.Paint
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.utlis.dp
import com.iranmobiledev.moodino.utlis.resolveColor

class SharedDayViewData(context: Context,height: Float) {

    val selectedPaint= Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style= Paint.Style.FILL
        it.color= context.resolveColor(R.attr.colorSelectDay)
//        addShadowIfNeeded(it)
    }

    val todayPaint= Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style= Paint.Style.STROKE
        it.strokeWidth= 1.dp
        it.color= context.resolveColor(R.attr.colorCurrentDay)
//        addShadowIfNeeded(it)
    }

    private val colorTextDayName= context.resolveColor(R.attr.colorTextDayName)
    private val headerTextSize= height * 11 / 40

    val headerTextPaint= Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textAlign= Paint.Align.CENTER
        it.textSize= headerTextSize
        it.color= colorTextDayName
//        addShadowIfNeeded(it)
    }

    private val colorTextDaySelected= context.resolveColor(R.attr.colorTextDaySelected)
    val headerTextSelectedPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textAlign = Paint.Align.CENTER
        it.textSize = headerTextSize
        it.color = colorTextDaySelected
//        addShadowIfNeeded(it)
    }

    val weekDayInitialsTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textAlign = Paint.Align.CENTER
        it.textSize = height * 20 / 40
        it.color = colorTextDayName
//        addShadowIfNeeded(it)
    }
}