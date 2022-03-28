package com.iranmobiledev.moodino.ui.states.viewmodel

import android.util.SizeF
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry

fun StatsFragmentViewModel.getEntriesForLineChart(): ArrayList<Entry> {
    return lineChartEntries
}

fun StatsFragmentViewModel.getEntriesForPieChart():
        MutableList<PieEntry> {
    return pieChartEntries
}

fun StatsFragmentViewModel.getLongestChain(): String {
    //TODO need db and entries part for this
    return "todo !"
}

fun StatsFragmentViewModel.getChainDayInRow(): String {
    //TODO need db and entries part for this
    return "todo !"
}