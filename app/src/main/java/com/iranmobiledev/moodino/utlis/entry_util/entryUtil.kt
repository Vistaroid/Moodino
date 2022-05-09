package com.iranmobiledev.moodino.utlis.entry_util

import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.PersianEntryDate

fun EntryDate.toPersian(): PersianEntryDate {
    return PersianEntryDate(
        getPersianNumber(year),
        getPersianNumber(month),
        getPersianNumber(day)
    )
}

fun getPersianNumber(number: Int): String {
    val numberString = number.toString()
    return numberString
        .replace("0", "۰")
        .replace("1", "۱")
        .replace("2", "۲")
        .replace("3", "۳")
        .replace("4", "۴")
        .replace("5", "۵")
        .replace("6", "۶")
        .replace("7", "۷")
        .replace("8", "۸")
        .replace("9", "۹");
}