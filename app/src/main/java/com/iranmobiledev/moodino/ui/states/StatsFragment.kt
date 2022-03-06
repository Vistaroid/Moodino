package com.iranmobiledev.moodino.ui.states

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentStatsBinding


class StatsFragment : BaseFragment() {
    private lateinit var binding: FragmentStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init charts
        val lineChart = binding.moodChartCardInclude.moodsLineChart

        val icon = resources.getDrawable(R.drawable.ic_emoji_happy)
//            Drawable.(R.drawable.ic_emoji_very_happy),
//            R.drawable.ic_emoji_happy,
//            R.drawable.ic_emoji_nothing,
//            R.drawable.ic_emoji_sad,
//            R.drawable.ic_emoji_very_sad,
//        )
        val entries = arrayListOf<Entry>()
        entries.add(Entry(1f, 1f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 3f))
        entries.add(Entry(4f, 4f))
        entries.add(Entry(5f, 5f))
        entries.add(Entry(6f, 4f))
        entries.add(Entry(7f, 3f))


        var dataSet = LineDataSet(entries, "moods")
        dataSet.apply {
            color = Color.RED
            lineWidth = 2f
            highLightColor = R.color.pink
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.chart_gradient)
            circleHoleColor = (Color.WHITE)
            setCircleColor(Color.RED);
            valueTextColor = Color.WHITE
            valueTextSize = 0f
        }

        val xAxis = lineChart.xAxis
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            gridColor = Color.WHITE
            granularity = 1f
            axisMinimum = 1f
        }
        val yAxis = lineChart.axisLeft
        var yLabel = arrayListOf(
            "1",
            "2",
            "3",
            "4",
            "5",
        )
        yAxis.apply {
            gridColor = Color.WHITE
            textColor = Color.WHITE
            axisLineColor = Color.WHITE
            granularity = 1f
            setAxisMaxValue(5f)

        }

        val lineData = LineData(dataSet)
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
}