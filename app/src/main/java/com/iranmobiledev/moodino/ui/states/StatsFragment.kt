package com.iranmobiledev.moodino.ui.states

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.FragmentStatsBinding
import com.iranmobiledev.moodino.ui.calendar.calendarpager.monthName
import com.iranmobiledev.moodino.ui.calendar.toolbar.ChangeCurrentMonth
import com.iranmobiledev.moodino.ui.more.MoreViewModel
import com.iranmobiledev.moodino.ui.states.customView.YearViewHelper
import com.iranmobiledev.moodino.ui.states.viewmodel.StatsFragmentViewModel
import com.iranmobiledev.moodino.utlis.*
import io.github.persiancalendar.calendar.AbstractDate
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class StatsFragment : BaseFragment(), ChangeCurrentMonth {

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
        binding.mainToolbar.initialize(this)
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
        model.getEntries()
        initDayInRowCard()
        initLineChartCard()
        initPieChartCard()
        initYearInPixelCard()
    }

    private fun initYearInPixelCard() {
        val yearView = binding.yearView
        yearView.yearViewHelper = YearViewHelper(requireContext())
        model.initYearView(yearView)
        initMoodCountView()
    }

    private fun initMoodCountView() {
        model.entries.observe(viewLifecycleOwner) {
            binding.moodCountViewStats.setEntries(it, true)
        }
    }

    private fun initPieChartCard() {
        setupMoodsCount()

        val colors = arrayListOf<Int>(
            ColorArray.rad,
            ColorArray.good,
            ColorArray.meh,
            ColorArray.bad,
            ColorArray.awful
        )

        val pieChart = binding.moodCountPieChart

        val moodsCount = mutableListOf(
            PieEntry(0f, ""),
            PieEntry(0f, ""),
            PieEntry(0f, ""),
            PieEntry(0f, ""),
            PieEntry(0f, "")
        )

        model.entries.observe(viewLifecycleOwner) { entries ->
            var countSum = 0
            if (!entries.isNullOrEmpty()) {
                val distinctList = entries.distinctBy { it.emojiValue }
                distinctList.forEach { item ->
                    val list = entries.filter { it.emojiValue == item.emojiValue }
                    countSum += list.size
                    when (item.emojiValue) {
                        EmojiValue.RAD -> moodsCount[0] = PieEntry(list.size.toFloat(), "")
                        EmojiValue.GOOD -> moodsCount[1] = PieEntry(list.size.toFloat(), "")
                        EmojiValue.MEH -> moodsCount[2] = PieEntry(list.size.toFloat(), "")
                        EmojiValue.BAD -> moodsCount[3] = PieEntry(list.size.toFloat(), "")
                        EmojiValue.AWFUL -> moodsCount[4] = PieEntry(list.size.toFloat(), "")
                    }
                }
            }

            val dataSet = PieDataSet(moodsCount, null)
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
                holeRadius = 48f
                setDrawEntryLabels(false)
                legend.isEnabled = false
                centerText = countSum.toString()
                setHoleColor(Color.TRANSPARENT)
                setCenterTextColor(Color.GRAY)
                setCenterTextSize(24f)
                notifyDataSetChanged()
                invalidate()
            }
        }
    }

    private fun setupMoodsCount() {
        model.entries.observe(viewLifecycleOwner) { entries ->
            if (!entries.isNullOrEmpty()) {
                val distinctList = entries.distinctBy { it.emojiValue }
                distinctList.forEach { item ->
                    val list = entries.filter { it.emojiValue == item.emojiValue }
                    when (item.emojiValue) {
                        EmojiValue.RAD -> binding.moodCountVeryHappy.view?.findViewById<TextView>(R.id.moodCountTextView)?.text =
                            list.size.toString()
                        EmojiValue.GOOD -> binding.moodCountHappy.view?.findViewById<TextView>(R.id.moodCountTextView)?.text =
                            list.size.toString()
                        EmojiValue.MEH -> binding.moodCountNothing.view?.findViewById<TextView>(R.id.moodCountTextView)?.text =
                            list.size.toString()
                        EmojiValue.BAD -> binding.moodCountBad.view?.findViewById<TextView>(R.id.moodCountTextView)?.text =
                            list.size.toString()
                        EmojiValue.AWFUL -> binding.moodCountVeryBad.view?.findViewById<TextView>(R.id.moodCountTextView)?.text =
                            list.size.toString()
                    }
                }
            }
        }
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

    private fun initLineChartCard() {
        model.initLineChart()
        model.lineChartEntries.observe(viewLifecycleOwner) {
            Log.d(TAG, "initLineChartCard: $it")
            val dataSet = setupLineChart(it)
            customizeLineChart(dataSet)
            binding.moodsLineChart.apply {
                notifyDataSetChanged()
                invalidate()
            }
        }
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
                        setImageDrawable(resources.getDrawable(R.drawable.ic_tick))
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

    private fun setupLineChart(entries: List<Entry>): LineDataSet {
        var dataSet = LineDataSet(entries, "moods")
        var data = LineData(dataSet)
        binding.moodsLineChart.data = data
        return dataSet
    }

    private fun customizeLineChart(dataSet: LineDataSet) {
        dataSet.apply {
            color = ColorArray.rad
            lineWidth = 2f
            highLightColor = R.color.primary
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.chart_gradient)
            circleHoleColor = (Color.WHITE)
            setCircleColor(ColorArray.rad);
            valueTextColor = Color.WHITE
            valueTextSize = 1f
        }

        binding.moodsLineChart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false
            setPinchZoom(false)
            setTouchEnabled(true)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                gridColor = Color.TRANSPARENT
                textColor = Color.GRAY
                valueFormatter = ChartValueFormatter()
                granularity = 1f
                setDrawAxisLine(false)
            }

            axisLeft.apply {
                setDrawLabels(false)
                gridColor = Color.TRANSPARENT
                textColor = Color.WHITE
                axisLineColor = Color.WHITE
                granularity = 1f
                setAxisMaxValue(5f)
            }
        }
    }

    override fun changeCurrentMonth(date: AbstractDate, isClickOnToolbarItem: Boolean) {
        Log.d(TAG, "changeCurrentMonth: ${date.monthName}")
        model.selectedDateLiveData.postValue(date)
    }
}
