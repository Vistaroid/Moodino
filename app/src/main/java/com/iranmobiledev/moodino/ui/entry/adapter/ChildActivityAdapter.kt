package com.iranmobiledev.moodino.ui.entry.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.callback.ActivityItemCallback
import com.iranmobiledev.moodino.ui.view.ActivityView
import com.iranmobiledev.moodino.utlis.ImageLoadingService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChildActivityAdapter(
    var activities: MutableList<Activity>,
    private val context: Context,
    private val clickObserver: ActivityItemCallback,
    private val activitiesShouldSelect: MutableList<Activity>
) : RecyclerView.Adapter<ChildActivityAdapter.ViewHolder>(), KoinComponent {

    private val imageLoader: ImageLoadingService by inject()

    inner class ViewHolder(selfView: ActivityView) : RecyclerView.ViewHolder(selfView.getRoot()) {

        private val mSelfView = selfView
        private val binding = selfView.binding

        fun bind(activity: Activity) {
            val icon = context.resources.getIdentifier(activity.iconName,"drawable", context.packageName)
            imageLoader.load(context, icon, binding.activityIcon)
            binding.activityTitle.text = activity.title

            activitiesShouldSelect.find { it == activity }
                .also { it?.let { mSelfView.clickedOn() } }

            itemView.setOnClickListener {
                mSelfView.clickedOn()
                clickObserver.onActivityItemClicked(activity, mSelfView.mSelected)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivityView(parent.context, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activities[position])
    }

    override fun getItemCount(): Int = activities.size
}