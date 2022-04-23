package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context

var enabledCalendars = listOf(CalendarType.SHAMSI) //, CalendarType.GREGORIAN, CalendarType.ISLAMIC
    private set

val mainCalendar inline get() = enabledCalendars.getOrNull(0) ?: CalendarType.SHAMSI

private val weekDaysEmptyList = List(7) { "" }
var weekDays = weekDaysEmptyList
    private set

var language = Language.FA
    private set

var weekEnds = BooleanArray(7)
    private set

private val monthNameEmptyList = List(12) { "" }
var persianMonths = monthNameEmptyList
    private set

var preferredDigits = Language.PERSIAN_DIGITS
    private set
var weekStartOffset = 0
    private set
var isShowWeekOfYearEnabled = false
    private set
var isTalkBackEnabled = false
    private set
var weekDaysInitials = weekDaysEmptyList
    private set
 var monthLimit= 5000
 private set

var monthPositionGlobal: Int= 2500

// This should be called before any use of Utils on the activity and services
fun initGlobal(context: Context) {
//    debugLog("Utils: initGlobal is called")
//    updateStoredPreference(context)
//    applyAppLanguage(context)
    loadLanguageResources(context)
//    scheduleAlarms(context)
//    configureCalendarsAndLoadEvents(context)
}


fun loadLanguageResources(context: Context) {
    persianMonths = language.getPersianMonths(context)
//    islamicMonths = language.getIslamicMonths(context)
//    gregorianMonths = language.getGregorianMonths(context, easternGregorianArabicMonths)
//    nepaliMonths = language.getNepaliMonths()
    weekDays = language.getWeekDays(context)
    weekDaysInitials = language.getWeekDaysInitials(context)
}

