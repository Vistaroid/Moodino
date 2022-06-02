package com.iranmobiledev.moodino.ui.states.customView

import android.content.Context
import android.graphics.Paint
import androidx.core.content.res.ResourcesCompat
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.utlis.ColorArray
import com.iranmobiledev.moodino.utlis.EmojiValue
import saman.zamani.persiandate.PersianDate
import kotlin.math.roundToInt


class YearViewHelper(context: Context) {

    private val persianDate= PersianDate()
    val defaultColor= context.resources.getColor(R.color.gray_icon,context.theme)

    val textPaint= Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.typeface= ResourcesCompat.getFont(context,R.font.shabnam_medium)
        it.color = defaultColor
        it.textSize = context.resources.getDimension(R.dimen.textSize)
        it.textAlign = Paint.Align.CENTER

    }

    val circlePaint= Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.color = defaultColor
    }

     val monthsLength = listOf<Int>(
        31, // for dayNumber
        persianDate.getMonthLength(persianDate.shYear, 1),
        persianDate.getMonthLength(persianDate.shYear, 2),
        persianDate.getMonthLength(persianDate.shYear, 3),
        persianDate.getMonthLength(persianDate.shYear, 4),
        persianDate.getMonthLength(persianDate.shYear, 5),
        persianDate.getMonthLength(persianDate.shYear, 6),
        persianDate.getMonthLength(persianDate.shYear, 7),
        persianDate.getMonthLength(persianDate.shYear, 8),
        persianDate.getMonthLength(persianDate.shYear, 9),
        persianDate.getMonthLength(persianDate.shYear, 10),
        persianDate.getMonthLength(persianDate.shYear, 11),
        persianDate.getMonthLength(persianDate.shYear, 12),
    )

     val monthsName = listOf(
        persianDate.setShMonth(1).monthName().elementAt(0),
        persianDate.setShMonth(2).monthName().elementAt(0),
        persianDate.setShMonth(3).monthName().elementAt(0),
        persianDate.setShMonth(4).monthName().elementAt(0),
        persianDate.setShMonth(5).monthName().elementAt(0),
        persianDate.setShMonth(6).monthName().elementAt(0),
        persianDate.setShMonth(7).monthName().elementAt(0),
        persianDate.setShMonth(8).monthName().elementAt(0),
        persianDate.setShMonth(9).monthName().elementAt(0),
        persianDate.setShMonth(10).monthName().elementAt(0),
        persianDate.setShMonth(11).monthName().elementAt(0),
        persianDate.setShMonth(12).monthName().elementAt(0),
    )

    // calculate mood average color in each day
    fun getColor(entries: List<Entry>): Int {
        val sum= entries.sumOf { it.emojiValue }.toDouble()
        val average= sum/entries.size
        val roundedAverage= average.roundToInt()
        return  getColor(roundedAverage)
    }

    private fun getColor(emojiValue: Int):Int = when(emojiValue) {
        EmojiValue.RAD -> ColorArray.rad
        EmojiValue.GOOD -> ColorArray.good
        EmojiValue.MEH -> ColorArray.meh
        EmojiValue.BAD -> ColorArray.bad
        EmojiValue.AWFUL -> ColorArray.awful
        else -> ColorArray.meh
    }
}