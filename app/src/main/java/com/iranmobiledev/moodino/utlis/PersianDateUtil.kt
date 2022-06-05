package com.iranmobiledev.moodino.utlis

import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.EntryTime
import com.iranmobiledev.moodino.utlis.dialog.TimePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat


/**
 * 1 : Persian
 * 2 : English
 * @return مثال : اردیبهشت 1400
 * doc : https://github.com/samanzamani/persianDate
 */
fun persianDateFormat(
    pattern: String = "Y F",
    date: PersianDate = PersianDate()
): String {
    return PersianDateFormat.format(
        date,
        pattern,
        PersianDateFormat.PersianDateNumberCharacter.FARSI
    )
}

fun getTime(persianDate: PersianDate = PersianDate()): String {
    return PersianDateFormat.format(persianDate, "H:i")
}

fun getDate(
    date: PersianDate = PersianDate(),
    pattern: String = "Y F",
    language: Int = PERSIAN
): String {
    val type = when (language) {
        PERSIAN -> PersianDateFormat.PersianDateNumberCharacter.FARSI
        ENGLISH -> PersianDateFormat.PersianDateNumberCharacter.ENGLISH
        else -> PersianDateFormat.PersianDateNumberCharacter.FARSI
    }
    return PersianDateFormat.format(
        date,
        pattern,
        type
    )
}

fun getDate(date: EntryDate, pattern: String = "j F Y"): String{
    val persianDate = PersianDate()
    persianDate.shDay = date.day
    persianDate.shMonth = date.month
    persianDate.shYear = date.year
    return PersianDateFormat.format(persianDate,pattern)
}

fun PersianDate.newDate(entryDate :EntryDate): PersianDate {
    val newDate = PersianDate().apply {
        shYear = entryDate.year
        shMonth = entryDate.month
        shDay = entryDate.day

    }
    return newDate
}

fun PersianDate.yesterDay(): EntryDate {
    return when (shDay) {
        in 2..31 -> EntryDate(shYear, shMonth, shDay - 1)
        1 -> {
            when (shMonth) {
                in 2..12 -> {
                    shMonth -= 1
                    EntryDate(shYear, shMonth, monthDays)
                }
                1 -> {
                    shMonth = 12
                    shYear -= 1
                    shDay = monthDays
                    EntryDate(shYear, shMonth, shDay)
                }
                else -> EntryDate(0, 0, 0)
            }
        }
        else -> EntryDate(0, 0, 0)
    }
}

fun PersianDate.tomorrow(): EntryDate {
    return when (shDay) {
        in 1 until monthDays -> return EntryDate(shYear, shMonth, shDay + 1)
        monthDays -> {
            when (shMonth) {
                in 1..11 -> return EntryDate(shYear, shMonth + 1, 1)
                12 -> return EntryDate(shYear + 1, 1, 1)
                else -> return EntryDate(0, 0, 0)
            }
        }
        else -> return EntryDate(0, 0, 0)
    }
}

fun PersianDate.isGreaterThan(persianDate: PersianDate): Boolean{
    return when{
        shYear > persianDate.shYear -> return false
        shYear == persianDate.shYear && shMonth > persianDate.shMonth -> return false
        shYear == persianDate.shYear && shMonth == persianDate.shMonth && shDay > persianDate.shDay -> return false
        shYear == persianDate.shYear && shMonth == persianDate.shMonth && shDay == persianDate.shDay -> return false
        else -> return true
    }
}

fun checkDateAndTimeState(dateDialogData: PersianPickerDate, timeDialogData: TimePickerDialog, persianDate: PersianDate): Boolean{
    return dateDialogData.persianYear == persianDate.shYear &&
        dateDialogData.persianMonth == persianDate.shMonth &&
        dateDialogData.persianDay == persianDate.shDay &&
        timeDialogData.currentTime.hour.toInt() > persianDate.hour ||
        timeDialogData.currentTime.hour.toInt() == persianDate.hour &&
        timeDialogData.currentTime.minutes.toInt() > persianDate.minute
}