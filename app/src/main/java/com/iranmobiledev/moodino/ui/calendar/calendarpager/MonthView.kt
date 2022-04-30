package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.ui.calendar.calendarpager.*
import io.github.persiancalendar.calendar.AbstractDate
import java.security.KeyStore

class MonthView(context: Context, attributeSet: AttributeSet): RecyclerView(context,attributeSet) {

    init {
       setHasFixedSize(true)
       layoutManager= GridLayoutManager(context,7)
    }

    private var daysAdapter: DaysAdapter?= null

    fun initialize(sharedDayViewData: SharedDayViewData, calendarPager: CalendarPager){
        daysAdapter= DaysAdapter(context, sharedDayViewData, calendarPager)
        adapter= daysAdapter
        addCelSpacing()
    }

    // set Entries for one month for sample (ordibehesht)
    fun setEntries(entries: List<Entry>?){
        //fetch entries for per day
        if (!entries.isNullOrEmpty()){
            val distinctList= entries.distinctBy { it.date?.day }
            val sortedList= entries.sortedBy { it.date?.day }
            for(item in distinctList){
                val list= sortedList.filter { it.date?.day == item.date?.day }
                item.date?.day?.let { daysAdapter?.setEntries(it,list) }
            }
        }
    }

    private fun addCelSpacing(){
        addItemDecoration(object : ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

            }
        })
    }

    private var monthName = ""

    fun bind(monthStartJdn: Jdn, monthStartDate: AbstractDate) {
        monthStartDate
        val monthLength = mainCalendar.getMonthLength(monthStartDate.year, monthStartDate.month)
        monthName = language.my.format(monthStartDate.monthName, formatNumber(monthStartDate.year))
        contentDescription = monthName

        daysAdapter?.let {
            val startOfYearJdn = Jdn(mainCalendar, monthStartDate.year, 1, 1)
            it.startingDayOfWeek = monthStartJdn.dayOfWeek
            it.weekOfYearStart = monthStartJdn.getWeekOfYear(startOfYearJdn)
            it.weeksCount = (monthStartJdn + monthLength - 1).getWeekOfYear(startOfYearJdn) -
                    it.weekOfYearStart + 1
            it.days = monthStartJdn.createMonthDaysList(monthLength)
         //   it.initializeMonthEvents()
            it.notifyItemRangeChanged(0, it.itemCount)
        }
    }

    fun selectDay(dayOfMonth: Int) {
        daysAdapter?.selectDay(dayOfMonth)
    }

}