package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.graphics.Paint
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.utils.dp
import com.iranmobiledev.moodino.utils.resolveColor

class SharedDayViewData(context: Context) {

    val selectedPaint= Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style= Paint.Style.FILL
      //  it.color= context.resolveColor(R.attr.colorSelectDay)
    }

    val todayPaint= Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style= Paint.Style.STROKE
        it.strokeWidth= 1.dp
      //  it.color= context.resolveColor(R.attr.colorCurrentDay)
    }

}