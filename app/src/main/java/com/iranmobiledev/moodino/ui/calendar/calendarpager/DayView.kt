package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.graphics.*
import android.provider.Settings
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.utlis.ColorArray
import com.iranmobiledev.moodino.utlis.EmojiValue
import com.iranmobiledev.moodino.utlis.entry_util.toPersian
import kotlin.math.min
import kotlin.math.roundToInt

class DayView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var text = ""
    private var dayIsSelected = false
    private var today = false
    private var holiday = false
    var jdn: Jdn? = null
        private set
    var dayOfMonth = -1
        private set
    private var isWeekNumber = false
    private var header = ""

    var sharedDayViewData: SharedDayViewData? = null
    var entries: List<Entry>?= null
    private var dayHaveEntry: Boolean= false

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val shared = sharedDayViewData ?: return
       // val entries= entries

        val radius = min(width, height) / 2f
        drayCircle(canvas, shared, radius) // background circle if is needed
        drawText(canvas, shared)
        drawHeader(canvas, shared)

    }

    private fun drayCircle(canvas: Canvas?, shared: SharedDayViewData, radius: Float) {
//        if (dayIsSelected) canvas?.drawCircle(
//            width / 2f, height / 2f, radius, shared.selectedPaint
//        )
        if (!entries.isNullOrEmpty()){
            val filterList= entries?.filter { it.date?.toPersian()?.day.toString() == text }
            if (!filterList.isNullOrEmpty()){
                if (isDailyMoods){
                     drawPieChart(canvas,filterList)
                }else{
                    dayHaveEntry= true
                    drawAverageChart(canvas,filterList,shared,radius)
                }

            }
        }

        if (today) canvas?.drawCircle(
            width / 2f, height / 2f, radius-2, shared.todayPaint
        )
    }

    private fun drawPieChart(canvas: Canvas?,entries: List<Entry>){
        val paint= Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.style= Paint.Style.FILL
            }
        val smallSide= min(width,height)
        val centerX= width/2f
        val centerY= height/2f
        val left= centerX-smallSide/2
        val top= centerY-smallSide/2
        val right= centerX+smallSide/2
        val bottom= centerY+smallSide/2

        val rectF= RectF(left,top,right,bottom)
        val unit= 360f/entries.size

        val radCount= entries.filter { it.emojiValue == EmojiValue.RAD }.size
        val radDegree= radCount*unit

        val goodCount= entries.filter { it.emojiValue == EmojiValue.GOOD }.size
        val goodDegree= goodCount*unit

        val mehCount= entries.filter { it.emojiValue == EmojiValue.MEH }.size
        val mehDegree=  mehCount*unit

        val badCount= entries.filter { it.emojiValue == EmojiValue.BAD }.size
        val badDegree= badCount*unit

        val awfulCount= entries.filter { it.emojiValue == EmojiValue.AWFUL }.size
        val awfulDegree=  awfulCount*unit
        var divider= 3f
        if (entries.size == 1){
            divider= 0f
        }
        var sumDegree= 0f
        if (radCount > 0){
            paint.color= ColorArray.rad
            canvas?.drawArc(rectF,sumDegree+divider,radDegree-divider, true, paint)
            sumDegree+= radDegree
        }

        if (goodCount > 0){
            paint.color= ColorArray.good
            canvas?.drawArc(rectF,sumDegree+divider,goodDegree-divider, true, paint)
            sumDegree+= goodDegree
        }

        if (mehCount > 0){
            paint.color= ColorArray.meh
            canvas?.drawArc(rectF,sumDegree+divider,mehDegree-divider, true, paint)
            sumDegree+= mehDegree
        }

        if (badCount > 0){
            paint.color= ColorArray.bad
            canvas?.drawArc(rectF,sumDegree+divider,badDegree-divider, true, paint)
            sumDegree+= badDegree
        }

        if (awfulCount > 0){
            paint.color= ColorArray.awful
            canvas?.drawArc(rectF,sumDegree+divider,awfulDegree-divider, true, paint)
            sumDegree+= awfulDegree
        }

        // draw circle white in center
        paint.color= Color.WHITE
        val radius= (smallSide/2f)*2/3
        canvas?.drawCircle(centerX,centerY,radius,paint)

    }

    private fun drawAverageChart(canvas: Canvas?, entries: List<Entry>,shared: SharedDayViewData, radius: Float){
        val sum: Double= entries.sumOf { it.emojiValue }.toDouble()
        val average: Double = sum/entries.size
        val roundedAverage= average.roundToInt()
        canvas?.drawCircle(width / 2f, height /2f, radius - 3 , shared.haveEntryPaint(roundedAverage) )
    }

    private val textBounds= Rect()
    private fun drawText(canvas: Canvas?, shared: SharedDayViewData) {
        val textPaint = when {
            jdn != null -> when {
             //   holiday -> shared.dayOfMonthNumberTextHolidayPaint
               // dayIsSelected -> shared.dayOfMonthNumberTextSelectedPaint
                dayHaveEntry -> shared.haveEntryTextPaint
                else /*!dayIsSelected*/ -> shared.dayOfMonthNumberTextPaint
            }
            isWeekNumber -> shared.weekNumberTextPaint
            else -> shared.weekDayInitialsTextPaint
        }

        textPaint.getTextBounds(text, 0 , text.length , textBounds)
        val yPos= (height + textBounds.height()) / 2f
        canvas?.drawText(text, width/2f, yPos + 0.sp, textPaint)
    }

    private fun drawHeader(canvas: Canvas?, shared: SharedDayViewData){
        if (header.isEmpty()) return
        canvas?.drawText(header ,width/2f, height * 0.26f
            , if(dayIsSelected) shared.headerTextSelectedPaint else shared.headerTextPaint )
    }

    private fun setAll(
        text: String,
        isToday: Boolean = false,
        isSelected: Boolean = false,
        isHoliday: Boolean = false,
        jdn: Jdn? = null,
        dayOfMonth: Int = -1,
        header: String = "",
        isWeekNumber: Boolean = false
    ) {
        this.text= text
        this.today= isToday
        this.dayIsSelected= isSelected
        this.holiday= isHoliday
        this.jdn= jdn
        this.dayOfMonth= dayOfMonth
        this.header= header
        this.isWeekNumber= isWeekNumber

        postInvalidate()
    }

    fun setDayOfMonthItem(
        isToday: Boolean,
        isSelected: Boolean,
//        hasEvent: Boolean,
//        hasAppointment: Boolean,
    //    isHoliday: Boolean,
        jdn: Jdn,
        dayOfMonth: Int,
      //  header: String
    ) = setAll(
        text = formatNumber(dayOfMonth),
        isToday = isToday,
        isSelected = isSelected,
       // hasEvent = hasEvent,
     //   hasAppointment = hasAppointment,
        jdn = jdn,
        dayOfMonth = dayOfMonth,
      //  header = header,
    //    isHoliday = isHoliday || jdn.isWeekEnd()
    )

    fun setInitialOfWeekDay(text: String) = setAll(text)
}