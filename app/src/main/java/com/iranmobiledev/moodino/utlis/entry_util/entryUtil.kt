package com.iranmobiledev.moodino.utlis.entry_util

import com.iranmobiledev.moodino.data.EntryDate

fun EntryDate.toPersian(entryDate: EntryDate): EntryDate {
    return EntryDate(
        getPersianNumber(entryDate.year),
        getPersianNumber(entryDate.month),
        getPersianNumber(entryDate.day)
    )
}

fun getPersianNumber(number: String): String {
    return number
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

