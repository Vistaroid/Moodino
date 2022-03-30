package com.iranmobiledev.moodino.ui.entries.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.databinding.ItemEntryBinding

class EntryAdapter(private val entries : List<Entry>) : RecyclerView.Adapter<EntryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding : ItemEntryBinding = ItemEntryBinding.bind(itemView)

        private val entryIcon : ImageView = binding.EntryIcon
        private val entryTitle : TextView = binding.entryTitle
        private val moreIcon : ImageView = binding.moreIcon
        private val entryImage : ImageView = binding.entryImage
        private val entryNote : TextView = binding.entryNote

        fun bind(entry: Entry){
            //TODO check entry title and then set entry icon (hint : use switch)
            entryIcon.setImageResource(R.drawable.ic_emoji_very_happy)
            entryTitle.text = entry.title

            entry.photo?.let { entryImage.setImageURI(Uri.parse(it)) }
            entryNote.text = entry.note
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    override fun getItemCount(): Int = entries.size
}