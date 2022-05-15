package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.ui.calendar.CalendarFragmentDirections

class DaysAdapter(private val context: Context,private val sharedDayViewData: SharedDayViewData,
               private val calendarPager: CalendarPager?): RecyclerView.Adapter<DaysAdapter.ViewHolder>() {
    var days= emptyList<Jdn>()
    var startingDayOfWeek = 0
    var weekOfYearStart: Int = 0
    var weeksCount: Int = 0
    private var entries: List<Entry>?= null

    private var selectedDay = -1

    internal fun selectDay(dayOfMonth: Int) {
        val prevDay = selectedDay
        selectedDay = -1
        notifyItemChanged(prevDay)

        if (dayOfMonth == -1) return

        selectedDay = dayOfMonth + 6 + applyWeekStartOffsetToWeekDay(startingDayOfWeek)

        if (isShowWeekOfYearEnabled) selectedDay += selectedDay / 7 + 1

        notifyItemChanged(selectedDay)
    }

    // set entries for one day for sample 1401/2/10
    @SuppressLint("NotifyDataSetChanged")
    fun setEntries(entries: List<Entry>?){
        this.entries= entries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder= ViewHolder(DayView(parent.context).also {
            it.layoutParams= sharedDayViewData.layoutParams
            it.sharedDayViewData= sharedDayViewData
        })
       return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 7 * 7
    }

    private val todayJdn = Jdn.today()

    inner class ViewHolder(itemView: DayView): RecyclerView.ViewHolder(itemView)
        ,View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
         //   itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val itemDayView = (v as? DayView) ?: return
            val jdn = itemDayView.jdn ?: return
            calendarPager?.let { it.onDayClicked(jdn) }
            selectDay(itemDayView.dayOfMonth)
            v.findNavController().navigate(CalendarFragmentDirections.actionCalenderFragmentToDayEntriesFragment(day = itemDayView.dayOfMonth))
            //Toast.makeText(v.context, jdn.dayOfWeekName + " " + itemDayView.dayOfMonth , Toast.LENGTH_SHORT).show()
        }

//        override fun onLongClick(v: View): Boolean {
//            onClick(v)
//            val jdn = (v as? DayView).debugAssertNotNull?.jdn ?: return false
//            calendarPager?.let { it.onDayLongClicked(jdn) }
//            return false
//        }


        fun bind(pos: Int){
            var position = pos
            val dayView = (itemView as? DayView) ?: return
//            if (isShowWeekOfYearEnabled) {
//                if (position % 8 == 0) {
//                    val row = position / 8
//                    if (row in 1..weeksCount) {
//                        val weekNumber = formatNumber(weekOfYearStart + row - 1)
//                        dayView.setWeekNumber(weekNumber)
//                        dayView.contentDescription = if (isTalkBackEnabled)
//                            context.getString(R.string.nth_week_of_year, weekNumber)
//                        else weekNumber
//
//                        dayView.isVisible = true
//                    } else
//                        setEmpty()
//                    return
//                }
//
//                position = position - position / 8 - 1
//            }
            val fixedStartingDayOfWeek = applyWeekStartOffsetToWeekDay(startingDayOfWeek)
            if (days.size < position - 6 - fixedStartingDayOfWeek) {
                setEmpty()
            } else if (position < 7) {
                val weekDayPosition = revertWeekStartOffsetFromWeekDay(position)
                dayView.setInitialOfWeekDay(getInitialOfWeekDay(weekDayPosition))
                dayView.contentDescription = context
                    .getString(R.string.week_days_name_column, getWeekDayName(weekDayPosition))

                dayView.isVisible = true
                dayView.setBackgroundResource(0)
            } else {
                if (position - 7 - fixedStartingDayOfWeek >= 0) {
                    val day = days[position - 7 - fixedStartingDayOfWeek]
                  //  val events = eventsRepository?.getEvents(day, monthDeviceEvents) ?: listOf()

                    val isToday = day == todayJdn

                    val dayOfMonth = position - 6 - fixedStartingDayOfWeek
                    dayView.setDayOfMonthItem(
                        isToday,
                        pos == selectedDay,
                      //  events.any { it !is CalendarEvent.DeviceCalendarEvent },
                      //  events.any { it is CalendarEvent.DeviceCalendarEvent },
                     //   events.any { it.isHoliday },
                        day,
                        dayOfMonth,
                    //    getShiftWorkTitle(day, true)
                    )

//                    dayView.contentDescription = if (isTalkBackEnabled) getA11yDaySummary(
//                        context, day, isToday, EventsStore.empty(),
//                        withZodiac = isToday, withOtherCalendars = false, withTitle = true
//                    ) else dayOfMonth.toString()
                    dayView.entries= entries
                    dayView.isVisible = true
                } else {
                    setEmpty()
                }
            }

        }

        private fun setEmpty(){
            itemView.isVisible= false
        }

    }
}