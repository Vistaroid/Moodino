package com.iranmobiledev.moodino.ui.entries.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryList

class EntryContainerAdapter(private val context: Context, private val entriesList : List<EntryList>) : RecyclerView.Adapter<EntryContainerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val entryRecyclerView = itemView.findViewById<RecyclerView>(R.id.entryRv)

        fun bind(entries : List<Entry>){
            entryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            entryRecyclerView.adapter = EntryAdapter(entries)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entry_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entriesList[position].entries)
    }

    override fun getItemCount(): Int = entriesList.size
}