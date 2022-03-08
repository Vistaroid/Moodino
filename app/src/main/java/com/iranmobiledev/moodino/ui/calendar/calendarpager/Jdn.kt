package com.iranmobiledev.moodino.ui.calendar.calendarpager

import io.github.persiancalendar.calendar.AbstractDate

// Julian day number, basically a day counter starting from some day in concept
// https://en.wikipedia.org/wiki/Julian_day
@JvmInline
value class Jdn(val value: Long) {
     constructor(value: AbstractDate) : this(value.toJdn())
     constructor(calendar: CalendarType, year: Int, month: Int, day: Int):
             this(calendar.createDate(year ,month ,day))
}