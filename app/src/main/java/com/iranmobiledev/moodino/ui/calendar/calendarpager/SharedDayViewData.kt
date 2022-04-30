package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.graphics.Paint
import android.view.ViewGroup
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.utlis.ColorArray

class SharedDayViewData(context: Context,height: Float) {


    val layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height.toInt())

    private val textSize = height * (/*if (isArabicDigitSelected) 18 else */ 25) / 40

    val selectedPaint= Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style= Paint.Style.FILL
        it.color= context.resolveColor(R.attr.colorSelectDay)
//        addShadowIfNeeded(it)
    }

    val haveEntryPaint= Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style= Paint.Style.FILL
        it.color= ColorArray.good // context.resolveColor(R.attr.colorSelectDay)
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
    val dayOfMonthNumberTextSelectedPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textAlign = Paint.Align.CENTER
        it.textSize = textSize
        it.color = colorTextDaySelected
  //      addShadowIfNeeded(it)
    }

    private val colorTextDay = context.resolveColor(R.attr.colorTextDay)
    val dayOfMonthNumberTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textAlign = Paint.Align.CENTER
        it.textSize = textSize
        it.color = colorTextDay
  //      addShadowIfNeeded(it)
    }

    val weekNumberTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textAlign = Paint.Align.CENTER
        it.textSize = headerTextSize
        it.color = colorTextDayName
   //     addShadowIfNeeded(it)
    }

    private val colorTextDayHavingEntry= context.resolveColor(R.attr.colorTextDayHaveEntry)
    val haveEntryTextPaint= Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textAlign= Paint.Align.CENTER
        it.textSize= textSize
        it.color= colorTextDayHavingEntry
    }
}