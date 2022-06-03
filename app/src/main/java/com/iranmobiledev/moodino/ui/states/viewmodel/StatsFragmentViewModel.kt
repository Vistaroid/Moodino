package com.iranmobiledev.moodino.ui.states.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.*
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.repository.entry.EntryRepository
import com.iranmobiledev.moodino.ui.states.customView.YearView
import com.iranmobiledev.moodino.ui.states.customView.YearViewHelper
import com.iranmobiledev.moodino.utlis.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import saman.zamani.persiandate.PersianDate
import kotlin.collections.ArrayList


class StatsFragmentViewModel(
    private val entryRepository: EntryRepository
) : BaseViewModel() {

    val TAG = "viewmodelStats"

    private val _entries = MutableLiveData(listOf<com.iranmobiledev.moodino.data.Entry>())
    private val _lineChartEntries = MutableLiveData(listOf(Entry(1f, 2f)))
    private val _lineChartDates = MutableLiveData(listOf(10))
    private val pieChartEntries = mutableListOf<PieEntry>()
    private val _weekDays = MutableLiveData<ArrayList<Int>>()
    private val _longestChainLiveData: MutableLiveData<Int> = MutableLiveData(0)
    private val _latestChainLiveData: MutableLiveData<Int> = MutableLiveData(0)
    private val _lastFiveDaysStatus: MutableLiveData<List<Boolean>> =
        MutableLiveData(listOf(false, false, false, false, false))

    val lineChartEntries: LiveData<List<Entry>>
        get() = _lineChartEntries
    val longestChainLiveData: LiveData<Int>
        get() = _longestChainLiveData
    val latestChainLiveData: LiveData<Int>
        get() = _latestChainLiveData
    val lastFiveDaysStatus: LiveData<List<Boolean>>
        get() = _lastFiveDaysStatus
    val entries: LiveData<List<com.iranmobiledev.moodino.data.Entry>>
        get() = _entries
    val weekDays: LiveData<ArrayList<Int>>
        get() = _weekDays

    fun getEntries() {
        viewModelScope.launch {
            entryRepository.getAll().collectLatest { entries ->
                var sortedList = entries.sortedWith(compareBy(
                    { it.date.year },
                    { it.date.month },
                    { it.date.day }
                ))
                _entries.postValue(sortedList)
            }
        }
    }

    fun initDaysInRow() {
        viewModelScope.launch {
            entries.asFlow().collectLatest {

                //Adding week days name to daysInRow card
                launch {
                    getFiveDaysAsWeekDays()
                }

                val dates = withContext(Dispatchers.IO) {
                    getDatesFromEntries()
                }

                launch {
                    getLongestChainFromDates(dates)
                }

                launch {
                    getLatestChain(dates)
                }

                launch {
                    getLastFiveDaysStatus(dates)
                }
            }
        }
    }

    private fun getLatestChain(datesList: List<EntryDate>) {
        val distinctList = datesList.distinct()
        var latestChain = 0
        val persianDate = PersianDate()

        if (distinctList.isNotEmpty()) {

            latestChain = 1
            distinctList.forEach { entryDate ->

                val currentDate =
                    persianDate.newDate(EntryDate(entryDate.year, entryDate.month, entryDate.day))
                val prevDatePersianDate = currentDate.addDay(-1)
                val prevDate = EntryDate(
                    prevDatePersianDate.shYear,
                    prevDatePersianDate.shMonth,
                    prevDatePersianDate.shDay
                )

                if (distinctList.contains(prevDate)) {
                    latestChain++
                } else {
                    latestChain = 1
                }
            }
        }

        _latestChainLiveData.postValue(latestChain)
    }

    private fun getLongestChainFromDates(datesList: List<EntryDate>) {

        val distinctList = datesList.distinct()
        var longestChainMax = 0
        var longestChain = 0
        val persianDate = PersianDate()

        if (distinctList.isNotEmpty()) {
            longestChain = 1
            distinctList.forEach { entryDate ->

                val currentDate =
                    persianDate.newDate(EntryDate(entryDate.year, entryDate.month, entryDate.day))
                val prevDatePersianDate = currentDate.addDay(-1)
                val prevDate = EntryDate(
                    prevDatePersianDate.shYear,
                    prevDatePersianDate.shMonth,
                    prevDatePersianDate.shDay
                )

                if (distinctList.contains(prevDate)) {
                    longestChain++
                } else {
                    if (longestChainMax < longestChain) longestChainMax = longestChain
                    longestChain = 1
                }
            }
        }

        _longestChainLiveData.postValue(longestChainMax)
    }

    fun initLineChart() {
        val persianDate = PersianDate()
        viewModelScope.launch {
            _entries.asFlow().collectLatest {
                launch {
                    var currentMonthEntries =
                        it.filter { it.date.year == persianDate.shYear && it.date.month == persianDate.shMonth }
                    getEntriesForLineChart(currentMonthEntries)
                }
            }
        }
    }

    private fun getEntriesForLineChart(entries: List<com.iranmobiledev.moodino.data.Entry>) {
        var entriesLineChart: MutableList<Entry> = mutableListOf()
        val entriesDaysNumber: MutableList<Int> = mutableListOf()
        val optimizedLineChartEntries = mutableListOf<Entry>()

        for (entry in entries) {
            val y = getYFromEntry(entry)
            val x = getXFromEntry(entry)
            entriesDaysNumber.add(entry.date!!.day)
            entriesLineChart.add(Entry(x, y))
        }

        optimizeEntries(entriesLineChart, optimizedLineChartEntries)
        _lineChartEntries.postValue(optimizedLineChartEntries)
        _lineChartDates.postValue(entriesDaysNumber.toList())
    }

    private fun optimizeEntries(
        entries: MutableList<Entry>,
        optimizedLineChartEntries: MutableList<Entry>
    ) {
        if (entries.size == 0) {
            return
        }

        val first = entries[0]
        val filtered = entries.filter { it.x == first.x }
        optimizedLineChartEntries.add(getNewChartEntry(filtered))
        val notFiltered = entries.filterNot { it.x == first.x }
        optimizeEntries(notFiltered as MutableList<Entry>, optimizedLineChartEntries)
    }

    private fun getNewChartEntry(filteredList: List<Entry>): Entry {
        var sum = 0f
        filteredList.forEach {
            sum += it.y
        }
        val averageY = sum / filteredList.size.toFloat()
        return Entry(filteredList[0].x, averageY)
    }

    private fun getXFromEntry(entry: com.iranmobiledev.moodino.data.Entry): Float {
        return entry.date!!.day.toFloat()
    }

    private fun getYFromEntry(entry: com.iranmobiledev.moodino.data.Entry): Float {
        return entry.emojiValue.toFloat()
    }

    private fun getFiveDaysAsWeekDays() {
        var days = arrayListOf<Int>()
        for (i in 0..4) {
            val date = PersianDate.today().addDay(-i.toLong())
            when (date.dayOfWeek()) {
                0 -> days.add(R.string.sat)
                1 -> days.add(R.string.sun)
                2 -> days.add(R.string.mon)
                3 -> days.add(R.string.tue)
                4 -> days.add(R.string.wed)
                5 -> days.add(R.string.thu)
                6 -> days.add(R.string.fri)
            }
        }

        days.reverse()
        _weekDays.postValue(days)
    }

    private fun getDatesFromEntries(): List<EntryDate> {
        val dates = mutableListOf<EntryDate>()

        val entries = entries.value
        if (entries != null) {
            for (entry in entries) {
                dates.add(entry.date!!)
            }
        }

        return dates
    }

    private fun getLastFiveDaysStatus(entryDates: List<EntryDate>) {
        val lastFiveDayStatus = mutableListOf<Boolean>()

        for (i in 0..4) {
            val date = PersianDate.today().addDay(-i.toLong())
            if (entryDates.contains(EntryDate(date.shYear, date.shMonth, date.shDay))) {
                lastFiveDayStatus.add(true)
            } else {
                lastFiveDayStatus.add(false)
            }
        }

        _lastFiveDaysStatus.postValue(lastFiveDayStatus)
    }

    fun initYearView(yearView: YearView){
        viewModelScope.launch(Dispatchers.Main) {
            entries.asFlow().collectLatest {
                // most filter entries in one year
                yearView.entries = it
                yearView.postInvalidate()
            }
        }
    }
}