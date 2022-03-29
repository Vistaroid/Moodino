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
import com.iranmobiledev.moodino.data.EntryListState
import org.greenrobot.eventbus.EventBus

class EntryContainerAdapter(private val context: Context, private val entriesList : MutableList<EntryList>) : RecyclerView.Adapter<EntryContainerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val entryRecyclerView = itemView.findViewById<RecyclerView>(R.id.entryRv)

        fun bind(entries : List<Entry>){
            entryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            entryRecyclerView.adapter = EntryAdapter(entries)
        }
    }

    fun addEntry(entry: Entry){
        val entryList = findEntryListMatchWithEntry(entry)
        entryList?.let {
            it.entries.add(0,entry)
            entryList.state = EntryListState.UPDATE
            EventBus.getDefault().postSticky(it)
        }
        if(entryList == null){
            val entries = listOf(entry)
            val entryList = EntryList(null, entry.date!! , EntryListState.ADD,
                entries as MutableList<Entry>
            )
            entriesList.add(entryList)
            entryList.state = EntryListState.ADD
            EventBus.getDefault().post(entryList)
        }
        notifyDataSetChanged()
    }

    private fun findEntryListMatchWithEntry(entry: Entry) : EntryList? {
        var entryList: EntryList? = null
        for(i in entriesList){
            if(entry.date?.year == i.date.year &&
               entry.date?.month == i.date.month &&
               entry.date?.day == i.date.day){
                entryList = i
                break
            }

        }
        return entryList
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