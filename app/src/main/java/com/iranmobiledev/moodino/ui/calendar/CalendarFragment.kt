package com.iranmobiledev.moodino.ui.calendar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.iranmobiledev.moodino.base.BaseFragment
import com.iranmobiledev.moodino.data.BottomNavState
import com.iranmobiledev.moodino.databinding.FragmentCalendarBinding
import com.iranmobiledev.moodino.databinding.FragmentEntriesBinding
import com.iranmobiledev.moodino.utlis.BottomNavVisibility
import org.greenrobot.eventbus.EventBus
import com.iranmobiledev.moodino.ui.calendar.calendarpager.Jdn
import com.iranmobiledev.moodino.ui.calendar.calendarpager.mainCalendar
import com.iranmobiledev.moodino.ui.calendar.calendarpager.monthName
import com.iranmobiledev.moodino.ui.calendar.toolbar.MainToolbarItemClickListener
import io.github.persiancalendar.calendar.AbstractDate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class CalendarFragment : BaseFragment(),MainToolbarItemClickListener {
    private lateinit var binding : FragmentCalendarBinding
    private val viewModel by viewModels<CalendarViewModel>()

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().post(BottomNavState(true))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        BottomNavVisibility.currentFragment.value = this.id
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarPager.also {
           it.onDayClicked= { jdn -> bringDate(jdn, monthChange= false)}
         //  it.onDayLongClicked= ::
           it.setSelectedDay(Jdn(viewModel.selectedMonth.value), highlight = false, smoothScroll = false)

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

//        binding.appBar.let { appBar ->
//            appBar.toolbar.setupMenuNavigation()
//            appBar.root.hideToolbarBottomShadow()
//        }

        binding.mainToolbar.initialize(this)

    }

    private fun bringDate(jdn: Jdn, highlight: Boolean= true, monthChange: Boolean= true
                          , smoothScroll: Boolean= true){
        binding.calendarPager.setSelectedDay(jdn,highlight,monthChange,smoothScroll)

        val isToday= Jdn.today() == jdn
        viewModel.changeSelectedDay(jdn)

        // a11y
//        if (isTalkBackEnabled && !isToday && monthChange) Snackbar.make(
//            binding.root ?: return,
//            getA11yDaySummary(
//                context ?: return, jdn, false, EventsStore.empty(),
//                withZodiac = true, withOtherCalendars = true, withTitle = true
//            ),
//            Snackbar.LENGTH_SHORT
//        ).show()

    }

    private fun updateToolbar(binding: FragmentCalendarBinding, date: AbstractDate) {
          binding.mainToolbar.setMonth(date)
   //     binding.mainToolbar.title.text= date.monthName  + " "+ date.year
//        val toolbar = binding.appBar.toolbar
//        val secondaryCalendar = secondaryCalendar
//        if (secondaryCalendar == null) {
//            toolbar.title = date.monthName
//            toolbar.subtitle = formatNumber(date.year)
//        } else {
//            toolbar.title = language.my.format(date.monthName, formatNumber(date.year))
//            toolbar.subtitle = monthFormatForSecondaryCalendar(date, secondaryCalendar)
//        }
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