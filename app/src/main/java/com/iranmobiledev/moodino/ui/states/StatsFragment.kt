package com.iranmobiledev.moodino.ui.states

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.databinding.DaysInARowCardBinding
import com.iranmobiledev.moodino.databinding.FragmentStatsBinding
import com.iranmobiledev.moodino.ui.states.viewmodel.StatsFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class StatsFragment : BaseFragment() {

    private lateinit var binding: FragmentStatsBinding
    private val model: StatsFragmentViewModel by viewModel()

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
        initDayInRowCard()
        initLineChartCard()
        initPieChartCard()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initDayInRowCard() {

        model.initDaysInRow()

        model.longestChainLiveData.observe(viewLifecycleOwner){
            binding.longestChainTextView.text = it.toString()
        }

        model.latestChainLiveData.observe(viewLifecycleOwner){
            binding.daysInRowNumberTextView.text = it.toString()
        }

        val daysContainer = arrayListOf(
            binding.fifthDayFrameLayout,
            binding.fourthDayFrameLayout,
            binding.thirdDayFrameLayout,
            binding.secondDayFrameLayout,
            binding.firstDayFrameLayout
        )

        val daysTextView = arrayListOf(
            binding.dayOneTextView,
            binding.dayTwoTextView,
            binding.dayThreeTextView,
            binding.dayFourTextView,
            binding.dayFiveTextView
        )

        val daysIcon = arrayListOf(
            binding.fifthDayIV,
            binding.fourthDayIV,
            binding.thirdDayIV,
            binding.secondDayIV,
            binding.firstDayIV
        )

        model.weekDays.observe(viewLifecycleOwner){ weekDays ->
            for (textView in daysTextView) {
                textView.text= resources.getString(weekDays[daysTextView.indexOf(textView)])
            }
        }


        //change style of week days depend on status of is entry added or not
        model.lastFiveDaysStatus.observe(viewLifecycleOwner){
            daysContainer.forEach { view ->
                val currentStatus = it[daysContainer.indexOf(view)]
                if (currentStatus){
                    view.background = resources.getDrawable(R.drawable.primary_circle_shape)
                    daysIcon[daysContainer.indexOf(view)].apply {
                        setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
                    }
                }else{
                    view.background = resources.getDrawable(R.drawable.circle_shape)
                    daysIcon[daysContainer.indexOf(view)].apply {
                        setImageDrawable(resources.getDrawable(R.drawable.ic_cross))
                    }
                }

            }
        }

    }

    private fun initLineChartCard() {
//
//        model.lineChartEntries.observe(viewLifecycleOwner){
//            model.isEnoughEntries.observe(viewLifecycleOwner){ isEnough ->
//                if (isEnough){
//                    model.initLineChart(binding.moodsLineChart,it,requireContext())
//                }
//            }
//        }
    }

    private fun initPieChartCard() {}
}