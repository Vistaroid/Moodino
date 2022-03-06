package com.iranmobiledev.moodino.ui.entries.adapter

import android.animation.LayoutTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R

class ActivitiesAdapter(private val adapterItemCallback: AdapterItemCallback) : RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val expand : ImageView = itemView.findViewById(R.id.expandIv)
        private val expandLayout : ViewGroup = itemView.findViewById(R.id.expandLayout)
        private val cardViewContent : TextView = itemView.findViewById(R.id.cardViewTv)

        fun bind(){
            expand.setOnClickListener{
                adapterItemCallback.onExpandViewClicked(cardViewContent)
            }
            expandLayout.setOnClickListener{
                adapterItemCallback.onExpandViewClicked(cardViewContent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 9

    interface AdapterItemCallback{
        fun onExpandViewClicked(view : View)
    }
}