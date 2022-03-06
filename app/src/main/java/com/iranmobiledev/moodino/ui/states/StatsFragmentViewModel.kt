package com.iranmobiledev.moodino.ui.states

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseViewModel

class StatsFragmentViewModel : BaseViewModel() {

    private val lineChartEntries = arrayListOf<Entry>()

    fun initializeLineChart(lineChart: LineChart, context: Context) {

        //mocked entries for chart
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
}