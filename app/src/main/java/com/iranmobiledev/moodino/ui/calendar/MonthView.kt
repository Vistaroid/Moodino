package com.iranmobiledev.moodino.ui.calendar

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.ui.calendar.calendarpager.DaysAdapter
import com.iranmobiledev.moodino.ui.calendar.calendarpager.SharedDayViewData

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

}