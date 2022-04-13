package com.iranmobiledev.moodino.ui.calendar.toolbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.ui.calendar.calendarpager.dp
import com.iranmobiledev.moodino.ui.calendar.calendarpager.resolveColor

class CircleImageButton(context: Context, attrs: AttributeSet): AppCompatImageButton(context, attrs) {


    init {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas!= null)
        drawCircle(canvas)
    }

    private fun drawCircle(canvas: Canvas){
        val paint= Paint(Paint.ANTI_ALIAS_FLAG).also {
          it.style= Paint.Style.STROKE
          it.strokeWidth= 1.dp
          it.color= context.resolveColor(R.attr.colorCurrentDay)
        }
        canvas.drawCircle(width/2f, height/2f, (width/2f) - 1,paint)

    }
}