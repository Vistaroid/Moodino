package com.iranmobiledev.moodino.ui.states.viewmodel

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry

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