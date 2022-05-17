package com.iranmobiledev.moodino.ui.entry.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.databinding.ItemActivityBinding
import com.iranmobiledev.moodino.ui.view.ActivityView
import com.iranmobiledev.moodino.utlis.ImageLoadingService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChildActivityAdapter(
    private var activities: List<Activity>,
    private val context: Context
) : RecyclerView.Adapter<ChildActivityAdapter.ViewHolder>() , KoinComponent{

    private val imageLoader : ImageLoadingService by inject()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(activity: Activity) {
            imageLoader.load(itemView.context, activity.image, itemView.findViewById(R.id.activityIcon))
            itemView.findViewById<TextView>(R.id.activityTitle).text = activity.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivityView(parent.context, null).getRoot())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activities[position])
    }

    override fun getItemCount(): Int = activities.size
}