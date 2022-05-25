package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.iranmobiledev.moodino.R

class MoodCountDayView(context: Context, attr: AttributeSet?): View(context, attr) {

    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var text: Int= defaultText


    init {
        textPaint.also {
        //    it.color= defaultColor
            it.textAlign= Paint.Align.CENTER
            it.textSize= 36f
            it.typeface= ResourcesCompat.getFont(context, R.font.shabnam_medium)
        }
      //  rectPaint.color= defaultColor
    }


    fun setData(number: Int?, color: Int){
        this.text= number?: defaultText
        textPaint.color= color //?: defaultColor
        rectPaint.color= color //?: defaultColor
        postInvalidate()
    }

    fun refresh(color: Int){
        this.text= defaultText
        textPaint.color= color
        rectPaint.color= color
        postInvalidate()
    }

    companion object{
      //  const val defaultColor= Color.GRAY
        const val defaultText= 0
        const val rectHeight= 16
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