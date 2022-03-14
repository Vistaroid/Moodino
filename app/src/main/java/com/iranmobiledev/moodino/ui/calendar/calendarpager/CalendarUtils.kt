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