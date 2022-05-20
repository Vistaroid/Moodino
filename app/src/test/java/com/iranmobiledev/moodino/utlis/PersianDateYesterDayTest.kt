package com.iranmobiledev.moodino.utlis

import com.iranmobiledev.moodino.data.EntryDate
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import saman.zamani.persiandate.PersianDate

@RunWith(JUnit4::class)
class PersianDateYesterDayTest : TestCase() {

    @Test
    fun simpleTestYesterDay() {
        val persianDate = PersianDate()
        assertEquals(EntryDate(persianDate.shYear, 2, 29), persianDate.yesterDay())
    }

    @Test
    fun testYesterDayWhenDayIs1() {
        val persianDate = PersianDate()
        persianDate.shDay = 1
        persianDate.shMonth = 4
        assertEquals(EntryDate(persianDate.shYear, 3, 31), persianDate.yesterDay())
    }

    @Test
    fun testYesterDayWhenDayAndMonthIs1() {
        val persianDateExpected = PersianDate()
        val persianDateActual = PersianDate()

        persianDateExpected.shMonth = 12
        persianDateExpected.shDay = persianDateExpected.monthDays
        persianDateExpected.shYear -= 1

        persianDateActual.shDay = 1
        persianDateActual.shMonth = 1

        assertEquals(
            EntryDate(
                persianDateExpected.shYear,
                persianDateExpected.shMonth,
                persianDateExpected.shDay
            ),
            persianDateActual.yesterDay()
        )
    }

}