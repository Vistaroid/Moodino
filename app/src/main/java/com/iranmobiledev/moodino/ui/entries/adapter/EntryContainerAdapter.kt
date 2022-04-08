package com.iranmobiledev.moodino.ui.entries.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class EntryContainerAdapter(
    private val context: Context,
    private val entriesList: MutableList<List<Entry>>
) : RecyclerView.Adapter<EntryContainerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val entryListDate = itemView.findViewById<TextView>(R.id.entryListDate)
        private val entryRecyclerView = itemView.findViewById<RecyclerView>(R.id.entryRv)
        @SuppressLint("SetTextI18n")
        fun bind(entries: List<Entry>) {

            val persianDate = PersianDate()
            persianDate.grgMonth = entries[0].date?.month!!
            entryListDate.text = PersianDateFormat.format(persianDate, "j F", PersianDateFormat.PersianDateNumberCharacter.ENGLISH)

            entryRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            entryRecyclerView.adapter = EntryAdapter(entries, context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entry_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entriesList[position])
    }

    override fun getItemCount(): Int {
        return entriesList.size
    }

}

