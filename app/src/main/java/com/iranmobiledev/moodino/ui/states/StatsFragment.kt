package com.iranmobiledev.moodino.ui.states

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.BottomNavState
import com.iranmobiledev.moodino.databinding.DaysInARowCardBinding
import com.iranmobiledev.moodino.databinding.FragmentStatsBinding
import com.iranmobiledev.moodino.ui.states.viewmodel.StatsFragmentViewModel
import com.iranmobiledev.moodino.utlis.BottomNavVisibility
import org.greenrobot.eventbus.EventBus


class StatsFragment : BaseFragment() {
    private lateinit var binding: FragmentStatsBinding

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().post(BottomNavState(true))
    }

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


        val model : StatsFragmentViewModel by viewModels()

        val daysInARowCardBinding = binding.daysInRowCardInclude
        model.daysInRowManager(requireContext(),daysInARowCardBinding)

        val lineChart = binding.moodChartCardInclude.moodsLineChart
        model.initializeLineChart(lineChart,requireContext())

        val pieChart = binding.moodCountCardInclude.moodCountPieChart
        model.initializePieChart(pieChart,requireContext())


    }
}