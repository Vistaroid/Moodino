package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DaysAdapter(context: Context,private val sharedDayViewData: SharedDayViewData
                  ,calendarPager: CalendarPager ): RecyclerView.Adapter<DaysAdapter.ViewHolder>() {
    var days= emptyList<Jdn>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh= ViewHolder(DayView(parent.context).also {
            it.layoutParams= sharedDayViewData.layout
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(itemView: DayView): RecyclerView.ViewHolder(itemView)
        ,View.OnClickListener,View.OnLongClickListener{
        init {

        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        override fun onLongClick(v: View?): Boolean {
            TODO("Not yet implemented")
        }

    }


}