package com.iranmobiledev.moodino.ui.states.viewmodel

import java.util.*
import kotlin.collections.ArrayList

fun StatsFragmentViewModel.getFiveDaysAsWeekDays(): ArrayList<String> {

    var days = arrayListOf<String>()
    val calendar = Calendar.getInstance()

    for (i in 1..5) {

        /**
         * get five days before from today for dayInaRowCard
         */

        calendar.add(Calendar.DAY_OF_WEEK, -1)
        val weekDay = calendar.time.toString().slice(0..2)

        if (days.size >= 5) {
            days.clear()
            days.add(weekDay)
        } else {
            days.add(weekDay)
        }
    }
    return days
}