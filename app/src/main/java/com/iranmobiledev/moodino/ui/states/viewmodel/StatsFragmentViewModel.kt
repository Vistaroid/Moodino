package com.iranmobiledev.moodino.ui.states.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.databinding.DaysInARowCardBinding
import com.iranmobiledev.moodino.repository.entry.EntryRepository
import com.iranmobiledev.moodino.utlis.ColorArray
import com.iranmobiledev.moodino.utlis.Moods.AWFUL
import com.iranmobiledev.moodino.utlis.Moods.BAD
import com.iranmobiledev.moodino.utlis.Moods.GOOD
import com.iranmobiledev.moodino.utlis.Moods.MEH
import com.iranmobiledev.moodino.utlis.Moods.RAD
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class StatsFragmentViewModel(
    private val entryRepository: EntryRepository
) : BaseViewModel() {


    private val lineChartEntries = arrayListOf<Entry>()
    private val pieChartEntries = mutableListOf<PieEntry>()
    private val _isEnoughEntries: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private val _longestChainLiveData: MutableLiveData<Int> = MutableLiveData(0)
    private val _latestChainLiveData: MutableLiveData<Int> = MutableLiveData(0)
    private val _lastFiveDaysStatus: MutableLiveData<List<Boolean>> =
        MutableLiveData(listOf(false, false, false, false, false))

    val isEnoughEntries: LiveData<Boolean>
        get() = _isEnoughEntries
    val longestChainLiveData: LiveData<Int>
        get() = _longestChainLiveData
    val latestChainLiveData: LiveData<Int>
        get() = _latestChainLiveData
    val lastFiveDaysStatus: LiveData<List<Boolean>>
        get() = _lastFiveDaysStatus


    fun initDaysInRow(binding: DaysInARowCardBinding) {
        val daysTextView = arrayListOf<TextView>(
            binding.dayOneTextView,
            binding.dayTwoTextView,
            binding.dayThreeTextView,
            binding.dayFourTextView,
            binding.dayFiveTextView
        )

        viewModelScope.launch {
            entryRepository.getAll().collectLatest {

                launch {
                    val weekDays = getFiveDaysAsWeekDays()
                    //adding weekDays to textView in days in a row card
                    for (textView in daysTextView) {
                        textView.text = weekDays[daysTextView.indexOf(textView)]
                    }
                }
                launch {
                    entryRepository.getAll().collectLatest {
                        val dates = getDatesFromEntries(it)
                        getLongestChainFromDates(dates)
                    }
                }
            }
        }
    }

    private fun getFiveDaysAsWeekDays(): ArrayList<String> {
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

    private fun getDatesFromEntries(entries: List<com.iranmobiledev.moodino.data.Entry>): List<EntryDate> {
        val dates = mutableListOf<EntryDate>()
        for (entry in entries) {
            dates.add(entry.date!!)
        }
        return dates
    }

    @SuppressLint("NewApi")
    private fun getLongestChainFromDates(dates: List<EntryDate>) {

        var chainLengthMax = 0
        var latestChainLength = 1

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
                    latestChainLength++
                } else {
                    if (chainLengthMax <= latestChainLength) {
                        chainLengthMax = latestChainLength
                    }
                    latestChainLength = 1
                }
            }
        }

        _longestChainLiveData.postValue(chainLengthMax)
        _latestChainLiveData.postValue(latestChainLength)
    }

    @SuppressLint("NewApi")
    fun getLastFiveDaysStatus(entries: List<com.iranmobiledev.moodino.data.Entry>) {

        val lastFiveDayStatus = mutableListOf<Boolean>()
        val today = LocalDate.now()

        for (i in 0..4) {
            val index = i + 1
            val entry = entries[entries.size - index]
            val entryAsLocalDate =
                LocalDate.of(entry.date!!.year, entry.date!!.month, entry.date!!.day)
            val localDate = today.minusDays(i.toLong())

            if (entryAsLocalDate == localDate) lastFiveDayStatus.add(true) else lastFiveDayStatus.add(
                false
            )
        }

        _lastFiveDaysStatus.postValue(lastFiveDayStatus.toList())
    }

    fun initializePieChart(pieChart: PieChart, context: Context) {
        val entries = getEntriesForPieChart()
        //mocked entries for chart
        if (pieChartEntries.isEmpty()) {
            setEntriesForPieChart(
                arrayListOf<PieEntry>(
                    PieEntry(0.1f, ""),
                    PieEntry(0.3f, ""),
                    PieEntry(0.15f, ""),
                    PieEntry(0.4f, ""),
                    PieEntry(0.5f, ""),
                )
            )
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

    fun initializeLineChart(lineChart: LineChart, context: Context) {

        //mocked entries for chart
        if (lineChartEntries.isEmpty()) {
            setEntriesForLineChart(
                arrayListOf(
                    Entry(1f, AWFUL),
                    Entry(2f, BAD),
                    Entry(3f, MEH),
                    Entry(4f, GOOD),
                    Entry(5f, AWFUL),
                    Entry(6f, RAD),
                    Entry(7f, GOOD)
                )
            )
        }

        val entries = getEntriesForLineChart()
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

    object Mock {

        val mockEntries = listOf<com.iranmobiledev.moodino.data.Entry>(
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 21)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 22)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 23)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 24)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 25)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 20)),
            com.iranmobiledev.moodino.data.Entry(date = EntryDate(2022, 4, 27)),
        )
    }

    fun getEntriesForLineChart(): ArrayList<com.github.mikephil.charting.data.Entry> {
        return lineChartEntries
    }

    fun getEntriesForPieChart():
            MutableList<PieEntry> {
        return pieChartEntries
    }


    fun setEntriesForLineChart(entriesList: ArrayList<com.github.mikephil.charting.data.Entry>) {
        entriesList.forEach {
            lineChartEntries.add(it)
        }
    }


    fun setEntriesForPieChart(entriesList: MutableList<PieEntry>) {
        entriesList.forEach {
            pieChartEntries.add(it)
        }
    }
}