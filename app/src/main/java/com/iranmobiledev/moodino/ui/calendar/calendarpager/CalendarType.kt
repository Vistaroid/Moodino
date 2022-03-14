package com.iranmobiledev.moodino.ui.calendar.calendarpager

import com.iranmobiledev.moodino.R
import io.github.persiancalendar.calendar.*

enum class CalendarType(val title: Int, val shortTitle: Int ,val preferredDigits: CharArray) {
    SHAMSI(R.string.shamsi_calendar ,R.string.shamsi_calendar_short ,Language.PERSIAN_DIGITS);

    fun createDate(year: Int, month: Int, day: Int): AbstractDate = when(this){
         SHAMSI -> PersianDate(year ,month , day)
    }

    private fun getMonthStartFromMonthsDistance(
        baseYear: Int, baseMonth: Int, monthsDistance: Int
    ): AbstractDate = when (this) {
//        ISLAMIC -> IslamicDate(baseYear, baseMonth, 1).monthStartOfMonthsDistance(monthsDistance)
//        GREGORIAN -> CivilDate(baseYear, baseMonth, 1).monthStartOfMonthsDistance(monthsDistance)
        SHAMSI -> PersianDate(baseYear, baseMonth, 1).monthStartOfMonthsDistance(monthsDistance)
//        NEPALI -> NepaliDate(baseYear, baseMonth, 1).monthStartOfMonthsDistance(monthsDistance)
    }

    fun getMonthsDistance(baseJdn: Jdn, toJdn: Jdn): Int = when (this) {
//        ISLAMIC -> baseJdn.toIslamicCalendar().monthsDistanceTo(toJdn.toIslamicCalendar())
//        GREGORIAN -> baseJdn.toGregorianCalendar().monthsDistanceTo(toJdn.toGregorianCalendar())
        SHAMSI -> baseJdn.toPersianCalendar().monthsDistanceTo(toJdn.toPersianCalendar())
//        NEPALI -> baseJdn.toNepaliCalendar().monthsDistanceTo(toJdn.toNepaliCalendar())
    }

    fun getMonthLength(year: Int, month: Int) =
        Jdn(getMonthStartFromMonthsDistance(year, month, 1)) -  Jdn(this, year, month, 1)


    fun getMonthStartFromMonthsDistance(baseJdn: Jdn, monthsDistance: Int): AbstractDate {
        val date = baseJdn.toCalendar(this)
        return getMonthStartFromMonthsDistance(date.year, date.month, monthsDistance)
    }

    val monthsNames: List<String>
        get() = when (this) {
            SHAMSI -> persianMonths
//            ISLAMIC -> islamicMonths
//            GREGORIAN -> gregorianMonths
//            NEPALI -> nepaliMonths
        }
}