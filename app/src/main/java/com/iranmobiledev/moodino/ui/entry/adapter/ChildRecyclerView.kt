package com.iranmobiledev.moodino.ui.entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.databinding.ItemEntryBinding
import com.iranmobiledev.moodino.listener.EntryEventLister
import com.iranmobiledev.moodino.utlis.*
import org.koin.core.component.KoinComponent



class ChildRecyclerView(
    var entryEventLister: EntryEventLister,
    val entries: MutableList<Entry>,
    private val context: Context
) : RecyclerView.Adapter<ChildRecyclerView.ViewHolder>(), KoinComponent {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemEntryBinding = ItemEntryBinding.bind(itemView)
        private val entryIcon: ImageView = binding.EntryIcon
        private val moreIcon: ImageView = binding.moreIcon
        private val entryTitle : TextView = binding.entryTitle
        private val entryImageContainer : MaterialCardView = binding.imageContainer
        private val entryNote : TextView = binding.entryNote
        private val activityRv : RecyclerView = binding.activityRv
        private val spacer : View = binding.spacer
        @SuppressLint("ResourceType", "SetTextI18n")
        fun bind(entry: Entry, index: Int) {
            if(index == 0)
                spacer.visibility = View.GONE
            itemsVisibility(entry)
            binding.entryItem = entry
            entryIcon.setImageResource(entry.icon)
            moreIcon.setOnClickListener {
                makePopupMenu(entry, it)
            }
            setTitleColor(entry.title)
        }

        private fun itemsVisibility(entry: Entry) {
            if(entry.note.isNotEmpty())
                entryNote.visibility = View.VISIBLE
            if(entry.photoPath.isNotEmpty())
                entryImageContainer.visibility = View.VISIBLE
            if(entry.activities.isNotEmpty())
                activityRv.visibility = View.VISIBLE
        }

        private fun setTitleColor(titleId: Int) {
            when(titleId){
                RAD -> entryTitle.setTextColor(ColorArray.rad)
                GOOD -> entryTitle.setTextColor(ColorArray.good)
                MEH -> entryTitle.setTextColor(ColorArray.meh)
                BAD -> entryTitle.setTextColor(ColorArray.bad)
                AWFUL -> entryTitle.setTextColor(ColorArray.awful)
            }
        }

    }

    private fun makePopupMenu(witchEntry: Entry, view: View){
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            entryEventLister.delete(witchEntry)
        }
        popupMenu.show()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entry, parent, false)
        return ViewHolder(view)
    }
    fun remove(entry: Entry) {
        val entryInList = entries.find {
            it == entry
        }
        entryInList?.let {
            val index = entries.indexOf(entry)
            entries.remove(entry)
            notifyItemRemoved(index)
        }
    }
    fun add(entry: Entry) {
        entries.add(0, entry)
        notifyItemInserted(0)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entries[position], position)
    }
    override fun getItemCount(): Int {
        return entries.size
    }
}