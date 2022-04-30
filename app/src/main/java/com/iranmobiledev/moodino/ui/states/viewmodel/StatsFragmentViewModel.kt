package com.iranmobiledev.moodino.ui.states.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.repository.entry.EntryRepository
import com.iranmobiledev.moodino.utlis.ColorArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class StatsFragmentViewModel(
    private val entryRepository: EntryRepository
) : BaseViewModel() {


    private val _lineChartEntries = MutableLiveData<List<Entry>>(listOf(Entry(1f, 2f)))
    private val _lineChartDates = MutableLiveData<List<Int>>(listOf(10))
    private val pieChartEntries = mutableListOf<PieEntry>()
    private val _weekDays = MutableLiveData<ArrayList<String>>()
    private val _isEnoughEntries: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private val _longestChainLiveData: MutableLiveData<Int> = MutableLiveData(0)
    private val _latestChainLiveData: MutableLiveData<Int> = MutableLiveData(0)
    private val _lastFiveDaysStatus: MutableLiveData<List<Boolean>> =
        MutableLiveData(listOf(false, false, false, false, false))

    val lineChartEntries: LiveData<List<Entry>>
        get() = _lineChartEntries
    val isEnoughEntries: LiveData<Boolean>
        get() = _isEnoughEntries
    val longestChainLiveData: LiveData<Int>
        get() = _longestChainLiveData
    val latestChainLiveData: LiveData<Int>
        get() = _latestChainLiveData
    val lastFiveDaysStatus: LiveData<List<Boolean>>
        get() = _lastFiveDaysStatus
    val weekDays: LiveData<ArrayList<String>>
        get() = _weekDays

    fun initDaysInRow() {

        viewModelScope.launch {
            entryRepository.getAll().collectLatest { entries ->

                launch {
                    if (entries.size > 3) {
                        _isEnoughEntries.postValue(true)
                    } else {
                        _isEnoughEntries.postValue(false)
                    }
                }

                //Adding week days name to days in a row card
                launch {
                    getFiveDaysAsWeekDays()
                }

                val dates = withContext(Dispatchers.IO) {
                    getDatesFromEntries(entries)
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

    @SuppressLint("NewApi")
    private fun getLatestChain(dates: List<EntryDate>) {

        val reversedDate = dates.reversed()
        var latestChain = 1

        for (date in reversedDate) {
            val nextDateAsLocalDate =
                LocalDate.of(date.year, date.month, date.day).minusDays(1)

            if (date != reversedDate.last()) {
                val nextDateElement = reversedDate[reversedDate.indexOf(date) + 1]
                val nextDate = LocalDate.of(
                    nextDateElement.year,
                    nextDateElement.month,
                    nextDateElement.day
                )
                if (nextDateAsLocalDate == nextDate) {
                    latestChain++

                } else {
                    break
                }
            }
        }
        _latestChainLiveData.postValue(latestChain)
    }

    @SuppressLint("NewApi")
    private fun getLongestChainFromDates(dates: List<EntryDate>) {

        var chainLengthMax = 0
        var chainLength = 1

        for (date in dates) {
            val nextDateAsLocalDate =
                LocalDate.of(date.year, date.month, date.day).plusDays(1)
            if (date != dates.last()) {
                val nextDateElement = dates[dates.indexOf(date) + 1]
                val nextDate =
                    LocalDate.of(
                        nextDateElement.year,
                        nextDateElement.month,
                        nextDateElement.day
                    )
                if (nextDateAsLocalDate == nextDate) {
                    chainLength++
                } else {
                    if (chainLengthMax <= chainLength) {
                        chainLengthMax = chainLength
                    }
                    chainLength = 1
                }
            }
        }

        _longestChainLiveData.postValue(chainLengthMax)
    }

    fun initLineChart(lineChart: LineChart, entries: List<Entry>, context: Context) {

        viewModelScope.launch {
            entryRepository.getAll().collectLatest {
                launch {
                    getEntriesForLineChart(it)
                }
            }
        }

        //create dataSet from entries and customizing it
        var dataSet = LineDataSet(entries, "moods")
        dataSet.apply {
            color = Color.RED
            lineWidth = 2f
            highLightColor = R.color.pink
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(context, R.drawable.chart_gradient)
            circleHoleColor = (Color.WHITE)
            setCircleColor(Color.RED);
            valueTextColor = Color.WHITE
            valueTextSize = 0f
        }

        //getting xAxis for customizing
        val xAxis = lineChart.xAxis
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            gridColor = Color.WHITE
            textColor = Color.GRAY
            granularity = 1f
            axisMinimum = 1f
        }

        //getting xAxis for customizing
        val yAxis = lineChart.axisLeft
        yAxis.apply {
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float, axis: AxisBase): String {
                    return _lineChartDates.value?.get(value.toInt()).toString() ?: "1"
                }
            }
            gridColor = Color.WHITE
            textColor = Color.WHITE
            axisLineColor = Color.WHITE
            granularity = 1f
            setAxisMaxValue(5f)
        }

        //converting dataset to line in the chart
        val lineData = LineData(dataSet)

        //adding line to chart and some customizing for chartView
        lineChart.apply {
            data = lineData
            invalidate()
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false
            setPinchZoom(false)
            setTouchEnabled(false)
        }
    }

    private fun getEntriesForLineChart(entries: List<com.iranmobiledev.moodino.data.Entry>) {
        val entriesDaysNumber: MutableList<Int> = mutableListOf()
        val entriesPieChart: MutableList<Entry> = mutableListOf()

        for (entry in entries) {
            println("entry123 ${entry.date!!.day}")
            val x = entries.indexOf(entry) + 1.toFloat()
            val y = when (entry.title) {
                R.string.rad -> 5f
                R.string.good -> 4f
                R.string.meh -> 3f
                R.string.bad -> 2f
                R.string.awful -> 1f
                else -> {
                    3f
                }
            }
            entriesDaysNumber.add(entry.date!!.day)
            entriesPieChart.add(
                Entry(x, y)
            )
        }


        _lineChartDates.postValue(entriesDaysNumber.toList())
        _lineChartEntries.postValue(entriesPieChart.toList())

    }

    @SuppressLint("NewApi")
    private fun getFiveDaysAsWeekDays() {
        var days = arrayListOf<String>()
        val today = LocalDate.now()
        for (i in 0..4) {
            val date = today.minusDays(i.toLong())
            val weekDayName = date.dayOfWeek.name.slice(0..2).toLowerCase()
            val formatedWeekDay = weekDayName.replaceFirstChar { it.uppercase() }
            days.add(formatedWeekDay)
        }

        days.reverse()
        _weekDays.postValue(days)
    }

    private fun getDatesFromEntries(entries: List<com.iranmobiledev.moodino.data.Entry>): List<EntryDate> {
        val dates = mutableListOf<EntryDate>()
        for (entry in entries) {
            dates.add(entry.date!!)
        }
        return dates
    }

    @SuppressLint("NewApi")
    fun getLastFiveDaysStatus(entryDates: List<EntryDate>) {
        val lastFiveDayStatus = mutableListOf<Boolean>()

        for(i in 0..4){
            val date = LocalDate.now().minusDays(i.toLong())

                if (entryDates.contains(EntryDate(date.year,date.monthValue,date.dayOfMonth))){
                    lastFiveDayStatus.add(true)
                }else{
                    lastFiveDayStatus.add(false)
                }
        }

        _lastFiveDaysStatus.postValue(lastFiveDayStatus)
    }

    fun initPieChart(pieChart: PieChart, context: Context) {
        val entries = getEntriesForPieChart()
        //mocked entries for chart
        if (pieChartEntries.isEmpty()) {

        }

        val colors = arrayListOf<Int>()
        for (color in ColorArray.colors) {
            colors.add(color)
        }

        val dataSet = PieDataSet(entries, null)
        dataSet.apply {
            setColors(colors)
        }


        val pieData = PieData(dataSet)
        pieData.apply {
            setDrawValues(true)
            setValueTextSize(0f)
            setValueTextColor(Color.TRANSPARENT)

        }

        pieChart.apply {
            data = pieData
            description.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 65f
            setDrawEntryLabels(false)
            isRotationEnabled = false
            legend.isEnabled = false
            centerText = "16"
            setCenterTextColor(Color.GRAY)
            setCenterTextSize(24f)
            setDrawRoundedSlices(true)
        }

    }

    object Mock {

        val mockEntries = listOf<com.iranmobiledev.moodino.data.Entry>(
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 1)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 2)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 3)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 18)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 19)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 20)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 24)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 25)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 26)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 27)),
        )
    }

    fun getEntriesForPieChart():
            MutableList<PieEntry> {
        return pieChartEntries
    }

    fun setEntriesForPieChart(entriesList: MutableList<PieEntry>) {
        entriesList.forEach {
            pieChartEntries.add(it)
        }
    }


}