package com.iranmobiledev.moodino.ui.entry.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.databinding.ItemActivitySmallBinding
import com.iranmobiledev.moodino.utlis.ImageLoadingService
import com.iranmobiledev.moodino.utlis.getEmoji
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SmallActivityAdapter(private val activities: List<Activity>, private val emojiValue: Int) :
    RecyclerView.Adapter<SmallActivityAdapter.ViewHolder>(), KoinComponent {

    private val imageLoader: ImageLoadingService by inject()

    inner class ViewHolder(private val itemBinding: ItemActivitySmallBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(activity: Activity, index: Int) {
            setupUi(activity)
            itemBinding.apply {
                ImageViewCompat.setImageTintList(
                    this.icon,
                    ColorStateList.valueOf(getEmoji(itemView.context, emojiValue).color)
                );
                if (index == activities.size - 1)
                    circle.visibility = View.GONE
            }
        }
        private fun setupUi(activity: Activity) {
            itemBinding.apply {
                val icon = itemBinding.root.context.resources.getIdentifier(
                    activity.iconName,
                    "drawable",
                    itemView.context.packageName
                )
                val title = itemBinding.root.context.resources.getIdentifier(
                    activity.title,
                    "string",
                    itemView.context.packageName
                )
                smallActivityTitle.text = itemView.context.getString(title)
                imageLoader.load(itemView.context, icon, this.icon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemActivitySmallBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activities[position], position)
    }

    override fun getItemCount(): Int = activities.size
}