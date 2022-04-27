package com.iranmobiledev.moodino.ui.states.viewmodel

import android.annotation.SuppressLint
import com.github.mikephil.charting.data.PieEntry
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

object Mock {

    val mockEntries = listOf<Entry>(
        Entry(date = EntryDate(2022, 4, 21)),
        Entry(date = EntryDate(2022, 4, 22)),
        Entry(date = EntryDate(2022, 4, 23)),
        Entry(date = EntryDate(2022, 4, 24)),
        Entry(date = EntryDate(2022, 4, 25)),
        Entry(date = EntryDate(2022, 4, 20)),
        Entry(date = EntryDate(2022, 4, 27)),
    )
}

fun StatsFragmentViewModel.getFiveDaysAsWeekDays(): ArrayList<String> {

    var days = arrayListOf<String>()
    val calendar = Calendar.getInstance()

    for (i in 1..5) {
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

fun StatsFragmentViewModel.getChain(entries: List<com.iranmobiledev.moodino.data.Entry>) {
    val dates = getDatesFromEntries(entries)
    getLongestChainFromDates(dates)
}

private fun getDatesFromEntries(entries: List<com.iranmobiledev.moodino.data.Entry>): List<EntryDate> {
    val dates = mutableListOf<EntryDate>()
    for (entry in entries) {
        dates.add(entry.date!!)
    }
    return dates
}

@SuppressLint("NewApi")
private fun StatsFragmentViewModel.getLongestChainFromDates(dates: List<EntryDate>){

    var chainLengthMax = 0
    var latestChainLength = 1

    for (date in dates) {
        val nextDateAsLocalDate = LocalDate.of(date.year, date.month, date.day).plusDays(1)
        if (date != dates.last()) {
            val nextDateElement = dates[dates.indexOf(date) + 1]
            val nextDate =
                LocalDate.of(nextDateElement.year, nextDateElement.month, nextDateElement.day)
            if (nextDateAsLocalDate == nextDate) {
                latestChainLength++
            } else {
                if (chainLengthMax <= latestChainLength) {
                    chainLengthMax = latestChainLength
                }
                latestChainLength = 1
            }
        }
    }

    longestChainLiveData.postValue(chainLengthMax)
    latestChainLiveData.postValue(latestChainLength)
}

@SuppressLint("NewApi")
fun StatsFragmentViewModel.getLastFiveDaysStatus(entries: List<Entry>){

    val lastFiveDayStatus = mutableListOf<Boolean>()
    val today = LocalDate.now()

    for (i in 0..4) {
        val index = i + 1
        val entry = entries[entries.size - index]
        val entryAsLocalDate = LocalDate.of(entry.date!!.year,entry.date!!.month,entry.date!!.day)
        val localDate = today.minusDays(i.toLong())

        if (entryAsLocalDate == localDate) lastFiveDayStatus.add(true) else lastFiveDayStatus.add(false)
    }

    lastFiveDaysStatus.postValue(lastFiveDayStatus.toList())
}

fun StatsFragmentViewModel.getEntriesForLineChart(): ArrayList<com.github.mikephil.charting.data.Entry> {
    return lineChartEntries
}

fun StatsFragmentViewModel.getEntriesForPieChart():
        MutableList<PieEntry> {
    return pieChartEntries
}


fun StatsFragmentViewModel.setEntriesForLineChart(entriesList: ArrayList<com.github.mikephil.charting.data.Entry>) {
    entriesList.forEach {
        lineChartEntries.add(it)
    }
}


fun StatsFragmentViewModel.setEntriesForPieChart(entriesList: MutableList<PieEntry>) {
    entriesList.forEach {
        pieChartEntries.add(it)
    }
}