package com.iranmobiledev.moodino.utlis

import saman.zamani.persiandate.PersianDate
import java.text.DateFormat
import java.util.*


class DateContainerImpl : DateContainer {
    private val persianDate = PersianDate()
    override fun today(): String {
        val monthName = android.text.format.DateFormat.format("MMM", Date()).toString()
        val monthDay = android.text.format.DateFormat.format("dd", Date()).toString()
        return "Today,$monthName $monthDay"
    }

    override fun time(): String {
        return "${persianDate.hour}:${persianDate.minute}"
    }
}

