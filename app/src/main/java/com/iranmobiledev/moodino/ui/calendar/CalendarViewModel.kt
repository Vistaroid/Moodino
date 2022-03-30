package com.iranmobiledev.moodino.ui.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.iranmobiledev.moodino.ui.calendar.calendarpager.Jdn
import com.iranmobiledev.moodino.ui.calendar.calendarpager.mainCalendar
import io.github.persiancalendar.calendar.AbstractDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalendarViewModel(application: Application): AndroidViewModel(application) {

    // State
    private val _selectedDay = MutableStateFlow(Jdn.today())
    private val _selectedMonth = MutableStateFlow(
        mainCalendar.getMonthStartFromMonthsDistance(selectedDay, 0)
    )

    // Values
    val selectedDay: Jdn get() = _selectedDay.value
    val selectedMonth: StateFlow<AbstractDate> get() = _selectedMonth

    // Commands
    fun changeSelectedDay(jdn: Jdn) {
        _selectedDay.value = jdn
    }

    fun changeSelectedMonth(selectedMonth: AbstractDate) {
        _selectedMonth.value = selectedMonth
    }

}