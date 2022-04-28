package com.iranmobiledev.moodino.ui.states

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.BottomNavState
import com.iranmobiledev.moodino.databinding.DaysInARowCardBinding
import com.iranmobiledev.moodino.databinding.FragmentStatsBinding
import com.iranmobiledev.moodino.ui.states.viewmodel.StatsFragmentViewModel
import org.greenrobot.eventbus.EventBus
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

    private fun initPieChartCard() {
        model.initDaysInRow(DaysInARowCardBinding.inflate(layoutInflater))
    }

    private fun initLineChartCard() {}


    private fun initDayInRowCard() {}

}