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
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.databinding.ItemEntryBinding
import com.iranmobiledev.moodino.listener.EntryEventLister
import org.koin.core.component.KoinComponent


class EntryAdapter(
    private val entryEventLister: EntryEventLister,
    val entries: MutableList<Entry>,
    private val context: Context
) : RecyclerView.Adapter<EntryAdapter.ViewHolder>(), KoinComponent {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemEntryBinding = ItemEntryBinding.bind(itemView)
        private val entryIcon: ImageView = binding.EntryIcon
        private val moreIcon: ImageView = binding.moreIcon
        private val entryImage: ImageView = binding.entryImage
        private val entryTimeTv: TextView = binding.entryTimeTv


        @SuppressLint("ResourceType", "SetTextI18n")
        fun bind(entry: Entry) {
            println("color is adapter: ${entry.titleColor}")
            entryTimeTv.text = "${entry.time?.hour}:${entry.time?.minutes}"
            binding.entryItem = entry
            entryIcon.setImageResource(entry.icon)
            moreIcon.setOnClickListener {
                val popupMenu = PopupMenu(context, it)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    entryEventLister.delete(entry)
                }
                popupMenu.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entry, parent, false)
        return ViewHolder(view)
    }

    fun remove(entry: Entry) {
        val index = entries.indexOf(entry)
        entries.remove(entry)
        notifyItemRemoved(index)
    }

    fun add(entry: Entry) {
        entries.add(0, entry)
        notifyItemInserted(0)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    override fun getItemCount(): Int = entries.size
}