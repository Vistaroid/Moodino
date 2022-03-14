package com.iranmobiledev.moodino.ui.calendar.calendarpager

import com.iranmobiledev.moodino.utlis.toCivilDate
import com.iranmobiledev.moodino.utlis.toJavaCalendar
import io.github.persiancalendar.calendar.AbstractDate
import io.github.persiancalendar.calendar.PersianDate
import java.util.*

// Julian day number, basically a day counter starting from some day in concept
// https://en.wikipedia.org/wiki/Julian_day
@JvmInline
value class Jdn(val value: Long) {
     constructor(value: AbstractDate) : this(value.toJdn())
     constructor(calendar: CalendarType, year: Int, month: Int, day: Int):
             this(calendar.createDate(year ,month ,day))

     // 0 means Saturday in it, see #`test day of week from jdn`() in the testsuite
     val dayOfWeek: Int get() = ((value + 2L) % 7L).toInt()

     fun toCalendar(calendar: CalendarType): AbstractDate = when (calendar) {
//          CalendarType.ISLAMIC -> toIslamicCalendar()
//          CalendarType.GREGORIAN -> toGregorianCalendar()
            CalendarType.SHAMSI -> toPersianCalendar()
//          CalendarType.NEPALI -> toNepaliCalendar()
     }

    operator fun compareTo(other: Jdn) = value.compareTo(other.value)
    operator fun plus(other: Int): Jdn = Jdn(value + other)
    operator fun minus(other: Int): Jdn = Jdn(value - other)

    // Difference of two Jdn values in days
    operator fun minus(other: Jdn): Int = (value - other.value).toInt()

     fun toPersianCalendar() = PersianDate(value)

     val dayOfWeekName: String get() = weekDays[this.dayOfWeek]

     companion object {
          fun today() = Jdn(Date().toJavaCalendar().toCivilDate())
     }
}