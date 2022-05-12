package com.iranmobiledev.moodino.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.databinding.FragmentCalendarBinding
import com.iranmobiledev.moodino.ui.calendar.calendarpager.Jdn
import com.iranmobiledev.moodino.ui.calendar.toolbar.MainToolbarItemClickListener
import com.iranmobiledev.moodino.utlis.*
import io.github.persiancalendar.calendar.AbstractDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CalendarFragment : BaseFragment(), MainToolbarItemClickListener {
    private lateinit var binding: FragmentCalendarBinding
    private val viewModel: CalendarViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarPager.also {
            it.onDayClicked = { jdn -> bringDate(jdn, monthChange = false) }

            it.setSelectedDay(
                Jdn(viewModel.selectedMonth.value),
                highlight = false,
                smoothScroll = false
            )

            it.onMonthSelected = { viewModel.changeSelectedMonth(it.selectedMonth) }
        }

        viewModel.selectedMonth
            .onEach { updateToolbar(binding, it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        if (viewModel.selectedDay != Jdn.today()) {
            bringDate(viewModel.selectedDay, monthChange = false, smoothScroll = false)
        } else {
            bringDate(Jdn.today(), monthChange = false, highlight = false)
        }

        binding.mainToolbar.initialize(this)

        viewModel.fetchEntries()

        val emoji= EmojiFactory.create(requireContext()).getEmoji(1f)
            emoji.image
            emoji.title
            emoji.image
            emoji.color
    }

    private fun bringDate(
        jdn: Jdn,
        highlight: Boolean = true,
        monthChange: Boolean = true,
        smoothScroll: Boolean = true
    ) {
        binding.calendarPager.setSelectedDay(jdn, highlight, monthChange, smoothScroll)
        viewModel.changeSelectedDay(jdn)

    }

    private fun updateToolbar(binding: FragmentCalendarBinding, date: AbstractDate) {
        viewModel.entries.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(200)
                binding.calendarPager.setEntries(it)
                binding.moodCountView.setEntries(it)
            }
        }
    }

    override fun clickOnAdBtn() {
        Toast.makeText(context, "show ad", Toast.LENGTH_SHORT).show()
    }

    override fun clickOnPreviousBtn() {
        binding.calendarPager.clickOnPreviousMonth()
    }

    override fun clickOnCurrentMonthBtn() {

    }

    override fun clickOnNextMonthBtn() {
        binding.calendarPager.clickOnNextMonth()
    }

    override fun clickOnSearchBtn() {
        Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
    }

}