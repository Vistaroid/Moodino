package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class DayView(context: Context,attrs: AttributeSet?= null): View(context,attrs) {

    private var dayIsSelected= false
    private var today= false

    var sharedDayViewData: SharedDayViewData?= null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val shared= sharedDayViewData ?: return

        val radius= min(width,height)/2f
        drayCircle(canvas,shared,radius) // background circle if is needed

    }

    private fun drayCircle(canvas: Canvas?, shared: SharedDayViewData, radius: Float){
       if (dayIsSelected)canvas?.drawCircle(
           width/2f,height/2f,radius,shared.selectedPaint)

       if (today)canvas?.drawCircle(
           width/2f,height/2f,radius,shared.todayPaint)
    }

    private fun drawText(){

    }
}