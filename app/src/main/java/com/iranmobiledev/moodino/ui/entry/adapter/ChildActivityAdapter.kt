package com.iranmobiledev.moodino.ui.entry.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.AlignSelf
import com.google.android.flexbox.FlexboxLayoutManager
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.databinding.ItemActivityBinding
import com.iranmobiledev.moodino.listener.ActivityItemCallback
import com.iranmobiledev.moodino.ui.view.ActivityView
import com.iranmobiledev.moodino.utlis.ImageLoadingService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChildActivityAdapter(
    private var activities: List<Activity>,
    private val context: Context,
    private val clickObserver: ActivityItemCallback
) : RecyclerView.Adapter<ChildActivityAdapter.ViewHolder>() , KoinComponent{

    private val imageLoader : ImageLoadingService by inject()

    inner class ViewHolder(selfView: ActivityView) : RecyclerView.ViewHolder(selfView.getRoot()) {

        private val mSelfView = selfView
        private val binding = selfView.binding

        fun bind(activity: Activity) {
            imageLoader.load(context, activity.image, binding.activityIcon)
            binding.activityTitle.text = activity.title
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