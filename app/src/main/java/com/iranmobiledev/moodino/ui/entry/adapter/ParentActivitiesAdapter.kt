package com.iranmobiledev.moodino.ui.entry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.ActivityAndCategory
import com.iranmobiledev.moodino.data.Category
import com.iranmobiledev.moodino.ui.view.ActivityGroupView
import jdk.javadoc.internal.doclets.toolkit.util.DocPath.parent


class ParentActivitiesAdapter(private val activities : List<ActivityAndCategory>) : RecyclerView.Adapter<ParentActivitiesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

//        private val binding = ItemActivityGroupViewBinding.bind(itemView)
        private val rv = itemView.findViewById<RecyclerView>(R.id.activityGroupRv)
        private val title = itemView.findViewById<TextView>(R.id.title)

        fun bind(activities: List<Activity>, category: Category){
//            binding.activityGroupRv.adapter = ChildActivityAdapter(activities, itemView.context)
//            binding.title.text = category.title
            rv.adapter = ChildActivityAdapter(activities, itemView.context)
            title.text = category.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ActivityGroupView(parent.context, null)
        val layoutParams = view.layoutParams
        layoutParams.width =
            parent.width / 4 - layoutParams.leftMargin - layoutParams.rightMargin
        view.layoutParams = layoutParams

        return ViewHolder(ActivityGroupView(parent.context, null).getRoot())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activities[position].activities, activities[position].category)
    }

    override fun getItemCount(): Int = activities.size
}