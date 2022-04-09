package com.iranmobiledev.moodino.ui.entries.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.Entry

class ActivityContainerAdapter(
    private var activities: List<List<Activity>>,
    private val context: Context
) : RecyclerView.Adapter<ActivityContainerAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val activitiesRv = itemView.findViewById<RecyclerView>(R.id.activitiesContainerRv)
        private val expand: ImageView = itemView.findViewById(R.id.expandIv)
        private val expandLayout: ViewGroup = itemView.findViewById(R.id.expandLayout)

        fun bind(activities: List<Activity>) {
            activitiesRv.layoutManager = GridLayoutManager(context, 5,RecyclerView.VERTICAL, false)
            activitiesRv.adapter = ActivitiesAdapter(activities)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activities[position])
    }

    override fun getItemCount(): Int = activities.size

}