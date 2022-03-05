package com.iranmobiledev.moodino.ui.states

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBindings
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentEntriesBinding
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
        val lineChart = binding.moodsLineChart

        val entries = arrayListOf<Entry>()
        entries.add(Entry(0f, 1f))
        entries.add(Entry(1f, 3f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 2f))
        entries.add(Entry(5f, 4f))
        entries.add(Entry(6f, 5f))
        entries.add(Entry(8f, 3f))


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
        yAxis.apply {
            gridColor = R.color.gray
            axisLineColor = Color.WHITE
            granularity = 1f
            textColor = Color.WHITE
            setDrawZeroLine(false)
        }

        val lineData = LineData(dataSet)
        lineChart.apply {
            data = lineData
            invalidate()
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false
        }
    }
}