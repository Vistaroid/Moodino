package com.iranmobiledev.moodino.utlis

import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

object PersianDateObj {
    val persianDate = PersianDate()
}

/**
 * 1 : Persian
 * 2 : English
 * @return مثال : اردیبهشت 1400
 * doc : https://github.com/samanzamani/persianDate
 */
fun persianDateFormat(language: Int = 1, pattern: String = "Y F", date: PersianDate = PersianDateObj.persianDate) : String{
    return when(language){
        1 -> {
            PersianDateFormat.format(
                date,
                pattern,
                PersianDateFormat.PersianDateNumberCharacter.FARSI
            )
        }
        2 -> {
            PersianDateFormat.format(
                date,
                pattern,
                PersianDateFormat.PersianDateNumberCharacter.ENGLISH
            )
        }
        else -> {""}
    }
}
fun getTime(persianDate: PersianDate = PersianDate()): String {
    return PersianDateFormat.format(persianDate, "H:i")
}
fun getDate(date: PersianDate = PersianDate(), pattern: String = "Y F", language: Int = PERSIAN): String {
    val type = when(language){
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