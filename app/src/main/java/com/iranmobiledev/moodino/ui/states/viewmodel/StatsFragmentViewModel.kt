package com.iranmobiledev.moodino.ui.states.viewmodel

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.utlis.ColorArray

class StatsFragmentViewModel : BaseViewModel() {

    private val lineChartEntries = arrayListOf<Entry>()
    private val pieChartEntries = mutableListOf<PieEntry>()

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
        for (color in ColorArray.COLORS){
            colors.add(color)
        }

        val dataSet = PieDataSet(entries,null)
        dataSet.apply {
            setColors(colors)
        }


        val pieData = PieData(dataSet)
        pieData.apply {
            setDrawValues(true)
            setValueTextSize(0f)
            setValueTextColor(Color.TRANSPARENT)

        }

        pieChart.apply{
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
                    Entry(1f, 1f),
                    Entry(2f, 2f),
                    Entry(3f, 3f),
                    Entry(4f, 4f),
                    Entry(5f, 5f),
                    Entry(6f, 4f),
                    Entry(7f, 3f)
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

    private fun getEntriesForLineChart(): ArrayList<Entry> {
        return lineChartEntries
    }

    private fun setEntriesForLineChart(entriesList: ArrayList<Entry>) {
        entriesList.forEach {
            lineChartEntries.add(it)
        }
    }

    private fun getEntriesForPieChart():
            MutableList<PieEntry> {
        return pieChartEntries
    }

    private fun setEntriesForPieChart(entriesList: MutableList<PieEntry>) {
        entriesList.forEach {
            pieChartEntries.add(it)
        }
    }
}