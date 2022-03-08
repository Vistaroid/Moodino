package com.iranmobiledev.moodino.ui.calendar.calendarpager

import com.iranmobiledev.moodino.R
import io.github.persiancalendar.calendar.AbstractDate
import io.github.persiancalendar.calendar.PersianDate

enum class CalendarType(val title: Int, val shortTitle: Int ,val preferredDigits: CharArray) {
    SHAMSI(R.string.shamsi_calendar ,R.string.shamsi_calendar_short ,Language.PERSIAN_DIGITS);

    fun createDate(year: Int, month: Int, day: Int): AbstractDate = when(this){
         SHAMSI -> PersianDate(year ,month , day)
    }
}