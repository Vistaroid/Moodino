package com.iranmobiledev.moodino.ui.states

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.DaysInARowCardBinding
import com.iranmobiledev.moodino.databinding.FragmentStatsBinding
import com.iranmobiledev.moodino.ui.states.viewmodel.StatsFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class StatsFragment : BaseFragment() {

    val TAG = "fragmentStats"

    private lateinit var binding: FragmentStatsBinding
    private val model: StatsFragmentViewModel by viewModel()
    private lateinit var daysContainer: ArrayList<FrameLayout>
    private lateinit var daysTextView: ArrayList<TextView>
    private lateinit var daysIcon: ArrayList<ImageView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        daysContainer = arrayListOf(
            binding.fifthDayFrameLayout,
            binding.fourthDayFrameLayout,
            binding.thirdDayFrameLayout,
            binding.secondDayFrameLayout,
            binding.firstDayFrameLayout
        )
        daysTextView = arrayListOf(
            binding.dayOneTextView,
            binding.dayTwoTextView,
            binding.dayThreeTextView,
            binding.dayFourTextView,
            binding.dayFiveTextView
        )
        daysIcon = arrayListOf(
            binding.fifthDayIV,
            binding.fourthDayIV,
            binding.thirdDayIV,
            binding.secondDayIV,
            binding.firstDayIV
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDayInRowCard()
        initLineChartCard()
    }

    private fun initDayInRowCard() {

        model.initDaysInRow()

        model.longestChainLiveData.observe(viewLifecycleOwner) {
            binding.longestChainTextView.text = ": $it"
        }
        model.latestChainLiveData.observe(viewLifecycleOwner) {
            binding.daysInRowNumberTextView.text = it.toString()
        }

        setupWeekDays()
        setupDaysStatus()
    }

    private fun setupWeekDays() {
        model.weekDays.observe(viewLifecycleOwner) { weekDays ->
            for (textView in daysTextView) {
                textView.text = resources.getString(weekDays[daysTextView.indexOf(textView)])
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupDaysStatus() {
        model.lastFiveDaysStatus.observe(viewLifecycleOwner) {
            daysContainer.forEach { view ->
                val currentStatus = it[daysContainer.indexOf(view)]
                if (currentStatus) {
                    view.background = resources.getDrawable(R.drawable.primary_circle_shape)
                    daysIcon[daysContainer.indexOf(view)].apply {
                        setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
                    }
                } else {
                    view.background = resources.getDrawable(R.drawable.circle_shape)
                    daysIcon[daysContainer.indexOf(view)].apply {
                        setImageDrawable(resources.getDrawable(R.drawable.ic_cross))
                    }
                }

            }
        }
    }

    private fun initLineChartCard() {
        model.lineChartEntries.observe(viewLifecycleOwner) {
            model.initLineChart(it, requireContext())
        }

        customizeLineChart()
    }

    private fun customizeLineChart() {
        binding.moodsLineChart.apply {
            data = lineData
            invalidate()
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false
            setPinchZoom(true)
            setTouchEnabled(true)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                gridColor = Color.WHITE
                textColor = Color.GRAY
                setDrawAxisLine(false)
            }

            axisLeft.apply {
                gridColor = Color.WHITE
                textColor = Color.WHITE
                axisLineColor = Color.WHITE
                granularity = 1f
                setAxisMaxValue(5f)
            }
        }
    }
}
