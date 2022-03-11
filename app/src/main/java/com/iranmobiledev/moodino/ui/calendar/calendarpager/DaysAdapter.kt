package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class DaysAdapter(context: Context,private val sharedDayViewData: SharedDayViewData
                ): RecyclerView.Adapter<DaysAdapter.ViewHolder>() {
    var days= emptyList<Jdn>()
    var startingDayOfWeek = 0


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

    inner class ViewHolder(itemView: DayView): RecyclerView.ViewHolder(itemView)
        ,View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Toast.makeText(v?.context, "click On Day", Toast.LENGTH_SHORT).show()
        }


        fun bind(pos: Int){
         val fixedStartingDayOGWeek= 0
        }

        private fun setEmpty(){
            itemView.isVisible= false
        }

    }


}