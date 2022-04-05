package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class MoodCountDayView(context: Context, attr: AttributeSet): View(context, attr) {

    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val text: Int= defaultText

    init {
        textPaint.also {
            it.color= defaultColor
            it.textAlign= Paint.Align.CENTER
            it.textSize= 40f
        }
        rectPaint.color= defaultColor
    }

    companion object{
        const val defaultColor= Color.GRAY
        const val defaultText= 0
        const val rectHeight= 20
        const val rectWidth= 100
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas!= null){
            drawText(canvas)
            drawRec(canvas)
        }
    }

    private fun drawText(canvas: Canvas){
        canvas.drawText(text.toString(),  width/2f,  height/2f, textPaint)

    }

    private fun drawRec(canvas: Canvas){
        val rect= Rect()
        rect.set((width/2) - rectWidth/2 , height/2 + 20, (width/2) + rectWidth/2, height/2 + 20 + rectHeight)
        canvas.drawRect(rect, rectPaint)
    }
}