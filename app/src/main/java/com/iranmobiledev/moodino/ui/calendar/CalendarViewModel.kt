package com.iranmobiledev.moodino.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.repository.entry.EntryRepository
import com.iranmobiledev.moodino.ui.calendar.calendarpager.Jdn
import com.iranmobiledev.moodino.ui.calendar.calendarpager.mainCalendar
import io.github.persiancalendar.calendar.AbstractDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CalendarViewModel(private val entryRepository: EntryRepository): BaseViewModel() {


    private val entries= MutableLiveData<List<Entry>>()

    init {
        fetchEntries()
    }

    private fun fetchEntries(){
        viewModelScope.launch(Dispatchers.IO) {
            entryRepository.getAll().collect{
                entries.postValue(it)
            }
        }

    }

    fun getEntries(): LiveData<List<Entry>>{
        return entries
    }

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