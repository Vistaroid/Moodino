package com.iranmobiledev.moodino.utlis

import com.iranmobiledev.moodino.data.EntryDate
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import saman.zamani.persiandate.PersianDate

@RunWith(JUnit4::class)
class PersianDateTomorrowTest {

    @Test
    fun simpleTomorrowTest() {
        val persianDate = PersianDate()
        persianDate.shDay = 2
        assertEquals(EntryDate(persianDate.shYear,persianDate.shMonth,3),persianDate.tomorrow())
    }

    @Test
    fun whenDayIsEqualWithLastDayOfMonth(){
        val persianDate = PersianDate()
        persianDate.shDay = persianDate.monthDays
        persianDate.shMonth = 5
        assertEquals(EntryDate(persianDate.shYear, 6,1), persianDate.tomorrow())
    }

    @Test
    fun whenDayIsLastDayOfMonthAndMonthIsLastMonthOfYear(){
        val persianDate = PersianDate()
        persianDate.shMonth = 12
        persianDate.shDay = persianDate.monthDays
        assertEquals(EntryDate(persianDate.shYear+1,1,1),persianDate.tomorrow())
    }
}