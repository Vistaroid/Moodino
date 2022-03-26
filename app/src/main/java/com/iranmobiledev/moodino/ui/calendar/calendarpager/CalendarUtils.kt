package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import io.github.persiancalendar.calendar.AbstractDate
import io.github.persiancalendar.calendar.CivilDate
import io.github.persiancalendar.calendar.IslamicDate
import io.github.persiancalendar.calendar.NepaliDate
import java.util.*



val Number.dp : Float get() = this.toFloat() * Resources.getSystem().displayMetrics.density
val Number.sp : Float get() = this.toFloat() * Resources.getSystem().displayMetrics.scaledDensity

fun Context.resolveColor(attribute: Int)= TypedValue().let {
    theme.resolveAttribute(attribute,it,true)
    ContextCompat.getColor(this,it.resourceId)
}

fun applyWeekStartOffsetToWeekDay(dayOfWeek: Int): Int = (dayOfWeek + 7 - weekStartOffset) % 7

fun revertWeekStartOffsetFromWeekDay(dayOfWeek: Int): Int = (dayOfWeek + weekStartOffset) % 7

fun getInitialOfWeekDay(position: Int) = weekDaysInitials[position % 7]

fun getWeekDayName(position: Int) = weekDays[position % 7]


fun Date.toJavaCalendar(forceLocalTime: Boolean = false): Calendar = Calendar.getInstance().also {
    if (!forceLocalTime)
        it.timeZone = TimeZone.getTimeZone("Asia/Tehran")
    it.time = this
}

val AbstractDate.monthName get() = this.calendarType.monthsNames.getOrNull(month - 1) ?: ""

fun Calendar.toCivilDate() =
    CivilDate(this[Calendar.YEAR], this[Calendar.MONTH] + 1, this[Calendar.DAY_OF_MONTH])

val Context.layoutInflater: LayoutInflater get() = LayoutInflater.from(this)

val AbstractDate.calendarType: CalendarType
    get() = when (this) {
//        is IslamicDate -> CalendarType.ISLAMIC
//        is CivilDate -> CalendarType.GREGORIAN
//        is NepaliDate -> CalendarType.NEPALI
        else -> CalendarType.SHAMSI
    }
fun formatNumber(number: Int, digits: CharArray = preferredDigits): String =
    formatNumber(number.toString(), digits)

fun formatNumber(number: String, digits: CharArray = preferredDigits): String {
  //  if (isArabicDigitSelected) return number
    return number.map { digits.getOrNull(Character.getNumericValue(it)) ?: it }
        .joinToString("")
}

fun <T> listOf31Items(
    x1: T, x2: T, x3: T, x4: T, x5: T, x6: T, x7: T, x8: T, x9: T, x10: T, x11: T, x12: T,
    x13: T, x14: T, x15: T, x16: T, x17: T, x18: T, x19: T, x20: T, x21: T, x22: T,
    x23: T, x24: T, x25: T, x26: T, x27: T, x28: T, x29: T, x30: T, x31: T
) = listOf(
    x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12,
    x13, x14, x15, x16, x17, x18, x19, x20, x21, x22,
    x23, x24, x25, x26, x27, x28, x29, x30, x31
)

fun <T> listOf12Items(
    x1: T, x2: T, x3: T, x4: T, x5: T, x6: T, x7: T, x8: T, x9: T, x10: T, x11: T, x12: T
) = listOf(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12)

fun <T> listOf7Items(
    x1: T, x2: T, x3: T, x4: T, x5: T, x6: T, x7: T
) = listOf(x1, x2, x3, x4, x5, x6, x7)