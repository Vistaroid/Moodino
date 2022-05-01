package com.iranmobiledev.moodino.ui.entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.listener.EmptyStateListener
import com.iranmobiledev.moodino.listener.EntryEventLister
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class EntryContainerAdapter(
    private var context: Context,
    private var entries: MutableList<MutableList<Entry>>,
    private var entryEventListener: EntryEventLister,
    private var emptyStateEventListener: EmptyStateListener
) : RecyclerView.Adapter<EntryContainerAdapter.ViewHolder>() {

    private val entryAdapters: MutableList<EntryAdapter> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val entryListDate = itemView.findViewById<TextView>(R.id.entryListDate)
        private val entryRecyclerView = itemView.findViewById<RecyclerView>(R.id.entryRv)

        @SuppressLint("SetTextI18n")
        fun bind(entries: List<Entry>) {
            val persianDate = PersianDate()
            persianDate.shMonth = Integer.parseInt(entries[0].date?.month!!)
            entryListDate.text = PersianDateFormat.format(
                persianDate,
                "j F",
                PersianDateFormat.PersianDateNumberCharacter.FARSI
            )

            entryRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            entryRecyclerView.itemAnimator = null

            var repeated = false
            entryAdapters.forEach {
                if (it.entries[0].date == entries[0].date) {
                    entryRecyclerView.adapter = it
                    repeated = true
                }
            }
            if (!repeated)
                makeNewAdapter(entries as MutableList<Entry>)
        }

        private fun makeNewAdapter(entries: MutableList<Entry>) {
            val entryAdapter = EntryAdapter(
                entryEventListener,
                entries, context
            )
            entryRecyclerView.adapter = entryAdapter
            entryAdapters.add(entryAdapter)
        }
    }

    fun setEntries(entries: List<List<Entry>>) {
        this.entries = entries as MutableList<MutableList<Entry>>
    }

    fun addEntry(entry: Entry) {
        var found = false
        entryAdapters.forEach {
            if (it.entries[0].date == entry.date) {
                found = true
                it.add(entry)
            }
        }
        if (!found || entryAdapters.size == 0) {
            entryAdapters.add(0, EntryAdapter(entryEventListener, mutableListOf(entry), context))
            notifyItemInserted(0)
        }
    }

    fun removeItem(entry: Entry) {
        entryAdapters.forEach {
            if (it.entries.contains(entry)) {
                it.remove(entry)
            }
            checkItemsToBeNotEmpty()
        }
    }

    private fun checkItemsToBeNotEmpty() {

        entries.forEachIndexed { index, list ->
            if (list.size == 0) {
                entries.removeAt(index)
                notifyItemRemoved(index)
            }

            entryAdapters.forEachIndexed { index, entryAdapter ->
                if (entryAdapter.entries.size == 0)
                    entryAdapters.removeAt(index)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entry_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    override fun getItemCount(): Int {
        emptyStateEventListener.emptyStateVisibility(entries.size == 0)
        return entries.size
    }

    fun create(
        context: Context,
        entriesList: MutableList<MutableList<Entry>>,
        entryEventListener: EntryEventLister,
        emptyStateEventListener: EmptyStateListener
    ) {
        this.context = context
        entries = entriesList
        this.emptyStateEventListener = emptyStateEventListener
        this.entryEventListener = entryEventListener
    }
}

