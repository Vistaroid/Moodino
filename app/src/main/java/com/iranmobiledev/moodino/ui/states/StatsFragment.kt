package com.iranmobiledev.moodino.ui.states

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< Updated upstream
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.iranmobiledev.moodino.R
=======
>>>>>>> Stashed changes
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.BottomNavState
import com.iranmobiledev.moodino.databinding.DaysInARowCardBinding
import com.iranmobiledev.moodino.databinding.FragmentStatsBinding
import com.iranmobiledev.moodino.ui.states.viewmodel.StatsFragmentViewModel
import org.greenrobot.eventbus.EventBus
import org.koin.androidx.viewmodel.ext.android.viewModel


class StatsFragment : BaseFragment() {
    private lateinit var binding: FragmentStatsBinding
    private val model : StatsFragmentViewModel by viewModel()
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
<<<<<<< Updated upstream

        binding.yearInPixels.apply{
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                DaysInYearComposable()
            }
        }

=======
>>>>>>> Stashed changes
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

    private fun initLineChartCard() {
    }

<<<<<<< Updated upstream
        val scope = CoroutineScope(Dispatchers.IO)

        val daysInARowCardBinding = binding.daysInRowCardInclude
        scope.launch {
            model.daysInRowManager(requireContext(),daysInARowCardBinding)
        }

       model.isEnoughEntries.observe(viewLifecycleOwner){
           if (it){
               val lineChart = binding.moodChartCardInclude.moodsLineChart
               model.initializeLineChart(lineChart,requireContext())

               val pieChart = binding.moodCountCardInclude.moodCountPieChart
               model.initializePieChart(pieChart,requireContext())
=======
    private fun initDayInRowCard() {
    }
>>>>>>> Stashed changes

               model.longestChainLiveData.observe(viewLifecycleOwner){
                   binding.daysInRowCardInclude.longestChainTextView.text = it.toString()
               }

               model.latestChainLiveData.observe(viewLifecycleOwner){
                   binding.daysInRowCardInclude.daysInRowNumberTextView.text = it.toString()
               }

               model.lastFiveDaysStatus.observe(viewLifecycleOwner){

                   it.forEach { bool ->
                       println("chain2223 $bool")
                   }


                   if (it[2]){
                       binding.daysInRowCardInclude.thirdDayFramLayout.background = resources.getDrawable(R.drawable.primary_circle_shape)
                       binding.daysInRowCardInclude.thirdDayIV.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
                       binding.daysInRowCardInclude.thirdDayIV.setColorFilter(resources.getColor(R.color.white))
                   }else{
                       binding.daysInRowCardInclude.thirdDayFramLayout.background = resources.getDrawable(R.drawable.circle_shape)
                       binding.daysInRowCardInclude.thirdDayIV.setImageDrawable(resources.getDrawable(R.drawable.ic_cross))
                   }

                   if (it[0]){
                       binding.daysInRowCardInclude.fifthDayFramLayout.background = resources.getDrawable(R.drawable.primary_circle_shape)
                       binding.daysInRowCardInclude.fifthDayIV.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
                       binding.daysInRowCardInclude.fifthDayIV.setColorFilter(resources.getColor(R.color.white))
                   }else{
                       binding.daysInRowCardInclude.fifthDayFramLayout.background = resources.getDrawable(R.drawable.circle_shape)
                       binding.daysInRowCardInclude.fifthDayIV.setImageDrawable(resources.getDrawable(R.drawable.ic_cross))
                   }

                   if (it[1]){
                       binding.daysInRowCardInclude.fourthDayFramLayout.background = resources.getDrawable(R.drawable.primary_circle_shape)
                       binding.daysInRowCardInclude.fourthDayIV.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
                       binding.daysInRowCardInclude.fourthDayIV.setColorFilter(resources.getColor(R.color.white))
                   }else{
                       binding.daysInRowCardInclude.fourthDayFramLayout.background = resources.getDrawable(R.drawable.circle_shape)
                       binding.daysInRowCardInclude.fourthDayIV.setImageDrawable(resources.getDrawable(R.drawable.ic_cross))
                   }


                   if (it[3]){
                       binding.daysInRowCardInclude.secondDayFramLayout.background = resources.getDrawable(R.drawable.primary_circle_shape)
                       binding.daysInRowCardInclude.secondDayIV.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
                       binding.daysInRowCardInclude.secondDayIV.setColorFilter(resources.getColor(R.color.white))
                   }else{
                       binding.daysInRowCardInclude.secondDayFramLayout.background = resources.getDrawable(R.drawable.circle_shape)
                       binding.daysInRowCardInclude.secondDayIV.setImageDrawable(resources.getDrawable(R.drawable.ic_cross))
                   }

                   if (it[4]){
                       binding.daysInRowCardInclude.firstDayFramLayout.background = resources.getDrawable(R.drawable.primary_circle_shape)
                       binding.daysInRowCardInclude.firstDayIV.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
                       binding.daysInRowCardInclude.firstDayIV.setColorFilter(resources.getColor(R.color.white))
                   }else{
                       binding.daysInRowCardInclude.firstDayFramLayout.background = resources.getDrawable(R.drawable.circle_shape)
                       binding.daysInRowCardInclude.firstDayIV.setImageDrawable(resources.getDrawable(R.drawable.ic_cross))
                   }
               }
           }else{
               Toast.makeText(requireContext(),"not enough entries",Toast.LENGTH_LONG).show()
           }
       }
    }
}