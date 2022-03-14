package com.iranmobiledev.moodino.ui.calendar.calendarpager

var enabledCalendars = listOf(CalendarType.SHAMSI) //, CalendarType.GREGORIAN, CalendarType.ISLAMIC
    private set

val mainCalendar inline get() = enabledCalendars.getOrNull(0) ?: CalendarType.SHAMSI

private val weekDaysEmptyList = List(7) { "" }
var weekDays = weekDaysEmptyList
    private set

var language = Language.FA
    private set

private val monthNameEmptyList = List(12) { "" }
var persianMonths = monthNameEmptyList
    private set

var preferredDigits = Language.PERSIAN_DIGITS
    private set
