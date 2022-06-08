package com.iranmobiledev.moodino.utlis

import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.isTomorrow
import com.iranmobiledev.moodino.ui.states.viewmodel.StatsFragmentViewModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@RunWith(JUnit4::class)
class DayInARowTest{


    @Test
    fun longestChainTest1(){
        assertEquals(3, getLongestChainFromDates(getFakeDates()))
    }
    @Test
    fun longestChainTest2(){
        assertEquals(6, getLongestChainFromDates(getFakeDates2()))
    }
    @Test
    fun longestChainTest3(){
        assertEquals(4, getLongestChainFromDates(getFakeDates3()))
    }
    @Test
    fun longestChainTest4(){
        assertEquals(2, getLongestChainFromDates(getFakeDates4()))
    }
    private fun getLongestChainFromDates(datesList: List<EntryDate>): Int {
        var counter = 1
        var previousChainCount = 0
        datesList.forEachIndexed { index, entryDate ->
            if(entryDate == datesList.last())
                return@forEachIndexed
            if(entryDate.isTomorrow(datesList[index+1])){
                counter += 1
                if(counter > previousChainCount)
                    previousChainCount = counter
            }
            else{
                if(counter > previousChainCount)
                    previousChainCount = counter
                counter = 1
            }
        }
        return previousChainCount
    }
}

fun getFakeDates() : List<EntryDate>{
    return listOf(
        EntryDate(1401,1,1),
        EntryDate(1401,1,2),
        EntryDate(1401,1,3),
    )
}
fun getFakeDates2() : List<EntryDate>{
    return listOf(
        EntryDate(1401,1,1),
        EntryDate(1401,1,2),
        EntryDate(1401,1,3),
        EntryDate(1401,1,4),
        EntryDate(1401,1,5),
        EntryDate(1401,1,6),
    )
}
fun getFakeDates3() : List<EntryDate>{
    return listOf(
        EntryDate(1401,2,1),
        EntryDate(1401,2,2),
        EntryDate(1401,2,3),
        EntryDate(1401,2,4),
        EntryDate(1401,1,5),
        EntryDate(1401,1,6),
    )
}

fun getFakeDates4() : List<EntryDate>{
    return listOf(
        EntryDate(1401,2,1),
        EntryDate(1401,3,2),
        EntryDate(1401,2,3),
        EntryDate(1401,2,4),
        EntryDate(1401,1,5),
        EntryDate(1401,1,6),
    )
}