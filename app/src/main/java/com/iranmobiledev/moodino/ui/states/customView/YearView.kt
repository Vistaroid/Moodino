package com.iranmobiledev.moodino.ui.states.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.utlis.ColorArray
import com.iranmobiledev.moodino.utlis.EmojiValue
import saman.zamani.persiandate.PersianDate
import kotlin.math.roundToInt

class YearView(context: Context, attr: AttributeSet? = null) : View(context, attr) {


    private var firstDraw: Boolean= true
    var entries: List<Entry> = listOf()
     var yearViewHelper: YearViewHelper?= null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (yearViewHelper==null || canvas== null){
            return
        }else{
            setEntries(canvas, yearViewHelper!!)
        }
    }

    private fun setEntries(canvas: Canvas, helper: YearViewHelper){
        if (firstDraw){
            firstDraw= false
        }
        val maxItemWidth= (width / 14f) // 12month + 1monthNumber +1
        val maxItemHeight= (height/33f) // 31day + 1monthName +1
        val radius= maxItemWidth/4

        // draw monthName in row one
        for (i in 1..12){
            canvas.drawText(helper.monthsName[i-1].toString(),maxItemWidth*(i+1) , maxItemHeight,helper.textPaint)
        }
        // draw 13 col => 1 for dayNumber and 12 for month Circles
        for (i in 1..13){
            for (j in 1..helper.monthsLength[i-1]){
                if (i==1){
                    canvas.drawText(j.toString(),maxItemWidth , maxItemHeight*(j+1),helper.textPaint)
                }else{
                    val filterList= entries.filter { it.date.month ==  i-1 && it.date.day == j}
                    if (!filterList.isNullOrEmpty()){
                        helper.circlePaint.color= helper.getColor(filterList)
                    }else{
                        helper.circlePaint.color= helper.defaultColor
                    }
                    canvas.drawCircle(maxItemWidth*i,maxItemHeight*(j+1),radius,helper.circlePaint)
                }
            }
        }
    }
}