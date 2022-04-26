package com.iranmobiledev.moodino.ui.states.viewmodel

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseViewModel
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

class StatsFragmentViewModel(
    private val entryRepository: EntryRepository
    ) : BaseViewModel() {

    val lineChartEntries = arrayListOf<Entry>()
    val pieChartEntries = mutableListOf<PieEntry>()
    var longestChainLiveData :MutableLiveData<Int> = MutableLiveData(0)


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
        for (color in ColorArray.COLORS) {
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

    suspend fun daysInRowManager(context: Context, binding: DaysInARowCardBinding) {

        viewModelScope.launch {
            entryRepository.getEntriesFlow.collectLatest {
                getChain(it)
            }
        }

        val weekDays = getFiveDaysAsWeekDays()
        val daysTextView = arrayListOf<TextView>(
            binding.dayOneTextView,
            binding.dayTwoTextView,
            binding.dayThreeTextView,
            binding.dayFourTextView,
            binding.dayFiveTextView
        )

        //adding weekDays to textView in days in a row card
        daysTextView[0].text = weekDays[0]
        daysTextView[1].text = weekDays[1]
        daysTextView[2].text = weekDays[2]
        daysTextView[3].text = weekDays[3]
        daysTextView[4].text = weekDays[4]

        println("chain123 ${longestChainLiveData.value}")
        binding.daysInRowNumberTextView.text = "${getChainDayInRow()}"
    }
}