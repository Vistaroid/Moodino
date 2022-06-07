package com.iranmobiledev.moodino.ui.entry.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.ActivityAndCategory
import com.iranmobiledev.moodino.data.Category
import com.iranmobiledev.moodino.callback.ActivityItemCallback
import com.iranmobiledev.moodino.ui.view.ActivityGroupView


class ParentActivitiesAdapter(
    private val activities: List<ActivityAndCategory>,
    private val clickObserver: ActivityItemCallback,
    private val activitiesShouldSelect: MutableList<Activity>,
) : RecyclerView.Adapter<ParentActivitiesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rv = itemView.findViewById<RecyclerView>(R.id.activityGroupRv)
        private val title = itemView.findViewById<TextView>(R.id.title)
        fun bind(data: ActivityAndCategory, category: Category) {
            val adapter = ChildActivityAdapter(
                data.activities as MutableList<Activity>,
                itemView.context,
                clickObserver,
                activitiesShouldSelect
            )
            rv.adapter = adapter
            val titleText = itemView.context.resources.getIdentifier(category.title,"string",itemView.context.packageName)
            title.text = itemView.context.getString(titleText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ActivityGroupView(parent.context, null).getRoot()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activities[position], activities[position].category)
    }
    override fun getItemCount(): Int = activities.size
}