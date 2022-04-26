package com.iranmobiledev.moodino.ui.states.viewmodel

import android.annotation.SuppressLint
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.iranmobiledev.moodino.data.EntryDate
import saman.zamani.persiandate.PersianDate
import java.time.LocalDate
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

fun StatsFragmentViewModel.getChain(entries: List<com.iranmobiledev.moodino.data.Entry>) {
    val dates = getDatesFromEntries(entries)
    val longestChain = getLongestChainFromDates(dates)

    longestChainLiveData.postValue(longestChain)
}

private fun getDatesFromEntries(entries: List<com.iranmobiledev.moodino.data.Entry>): List<EntryDate> {
    val dates = mutableListOf<EntryDate>()
    for (entry in entries) {
        dates.add(entry.date!!)
    }
    return dates
}

@SuppressLint("NewApi")
private fun getLongestChainFromDates(dates: List<EntryDate>): Int{


    var chainLengthMax = 0
    var chainLength = 1

    for (date in dates){
        val nextDateAsLocalDate = LocalDate.of(date.year,date.month,date.day).plusDays(1)
        if (date != dates.last()){
            val nextDateElement = dates[dates.indexOf(date) + 1]
            val nextDate = LocalDate.of(nextDateElement.year,nextDateElement.month,nextDateElement.day)
            if (nextDateAsLocalDate == nextDate){
                    chainLength++
                }else{
                    if (chainLengthMax <= chainLength){
                        chainLengthMax = chainLength
                    }
                    chainLength = 1
                }
        }
    }

    return chainLengthMax
}

@SuppressLint("NewApi")
fun StatsFragmentViewModel.getLastFiveDaysStatus(entries: List<com.iranmobiledev.moodino.data.Entry>): List<Boolean> {

    val lastFiveDayStatus = mutableListOf(false,false,false,false,false)

    val today = LocalDate.now()
    var index = 5
    while (index != 0){
        if (index != 1){
            val entry = entries[entries.size - index]
            if (LocalDate.of(entry.date!!.year,entry.date!!.month,entry.date!!.day) == today.minusDays(index.toLong())){
                lastFiveDayStatus[index-1] = true
            }else {
                lastFiveDayStatus[index-1] = true
            }
        }else{
            val entry = entries.last()
            lastFiveDayStatus[0] = LocalDate.of(entry.date!!.year,entry.date!!.month,entry.date!!.day) == today.minusDays(index.toLong())
        }
        index--
    }

    lastFiveDayStatus.forEach {
        println("chain123 $it")
    }
    return lastFiveDayStatus.toList()
}

fun StatsFragmentViewModel.getEntriesForLineChart(): ArrayList<Entry> {
    return lineChartEntries
}

fun StatsFragmentViewModel.getEntriesForPieChart():
        MutableList<PieEntry> {
    return pieChartEntries
}

fun StatsFragmentViewModel.getChainDayInRow(): String {
    //TODO need db and entries part for this
    return "todo !"
}

fun StatsFragmentViewModel.setEntriesForLineChart(entriesList: ArrayList<Entry>) {
    entriesList.forEach {
        lineChartEntries.add(it)
    }
}


fun StatsFragmentViewModel.setEntriesForPieChart(entriesList: MutableList<PieEntry>) {
    entriesList.forEach {
        pieChartEntries.add(it)
    }
}