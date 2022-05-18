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

class SmallActivityAdapter(private val activities: List<Activity>,private val emojiValue: Int) : RecyclerView.Adapter<SmallActivityAdapter.ViewHolder>() , KoinComponent{

    private val imageLoader : ImageLoadingService by inject()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding : ItemActivitySmallBinding = ItemActivitySmallBinding.bind(itemView)

        fun bind(activity: Activity) {
            binding.smallActivityTitle.text = activity.title
            imageLoader.load(itemView.context,activity.image,binding.icon)
            ImageViewCompat.setImageTintList(binding.icon, ColorStateList.valueOf(getEmoji(itemView.context,emojiValue).color));
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_small, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activities[position])
    }

    override fun getItemCount(): Int = activities.size
}