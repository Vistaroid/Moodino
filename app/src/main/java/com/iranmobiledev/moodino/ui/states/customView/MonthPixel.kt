package com.iranmobiledev.moodino.ui.states.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.compose.ui.graphics.Color
import com.iranmobiledev.moodino.data.Entry

class YearView(context: Context, attr: AttributeSet? = null) : View(context, attr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var canvas: Canvas? = null
    private var ySpace = 4f
    private var xSpace = 12f
    private val months = 12
    private lateinit var entries : List<Entry>
    private val monthsLength = listOf<Int>(
        31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30
    )

    fun setEntries(entries: List<Entry>) {
        this.entries = entries
        val radius = (width / 12) / 2f

        for (i in 1..31) {
            val index = if (i == 0) 1 else i
            drawText(i.toString(), radius, radius * index + ySpace)
        }
        drawMonths(radius)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.canvas = canvas
    }


    private fun drawCircle(cx: Float, cy: Float, radius: Float, color: Int) {
        paint.color = color
        canvas?.drawCircle(cx, cy, radius, paint)
    }

    private fun drawText(text: String, x: Float, y: Float) {
        paint.color = android.graphics.Color.BLACK
        paint.textSize = 30f
        paint.textAlign = Paint.Align.CENTER
        canvas?.drawText(text, x, y, paint)
    }

    fun drawMonths(radius: Float) {
        val distinctList = entries.distinctBy { it.date }
        monthsLength.forEachIndexed { index, length ->
            val monthEntries = distinctList.filter { it.date.month == index + 1 }
            drawMonth(index + 1, length, monthEntries, radius)
        }
    }

    private fun drawMonth(month: Int, length: Int, monthEntries: List<Entry>, radius: Float) {
        for (i in 1..length) {
            val index = if (i == 0) 1 else i
            val entry = monthEntries.find { it.date.day == i }
            val color = if (entry != null) android.graphics.Color.BLUE else android.graphics.Color.GRAY
            drawCircle(
                cx = (radius * month + xSpace * month) + radius,
                cy = radius * index + ySpace,
                radius - (ySpace * 8),
                color
            )
        }
    }

}