package com.iranmobiledev.moodino.ui.entry.adapter

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
import com.iranmobiledev.moodino.data.RecyclerViewData
import com.iranmobiledev.moodino.listener.EmptyStateListener
import com.iranmobiledev.moodino.listener.EntryEventLister
import com.iranmobiledev.moodino.utlis.EmptyStateEnum
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat


class EntryContainerAdapter : RecyclerView.Adapter<EntryContainerAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var entryEventListener: EntryEventLister
    private lateinit var emptyStateEventListener: EmptyStateListener
    private lateinit var data: MutableList<RecyclerViewData>

    private var emptyStateEnum = EmptyStateEnum.INVISIBLE

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val entryListDate = itemView.findViewById<TextView>(R.id.entriesDateTitle)
        private val entryRecyclerView = itemView.findViewById<RecyclerView>(R.id.entryRv)

        @SuppressLint("SetTextI18n")
        fun bind(data: RecyclerViewData) {
            if (data.entries.isNotEmpty()) {
                setupUi(data)
                setupNestedAdapter(data)
            }
        }

        private fun setupNestedAdapter(data: RecyclerViewData) {
            entryRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            entryRecyclerView.itemAnimator = null
            val adapter =
                ChildRecyclerView(entryEventListener, data.entries as MutableList<Entry>, context)
            entryRecyclerView.adapter = adapter
            data.adapter = adapter
        }

        private fun setupUi(data: RecyclerViewData) {
            val persianDate = PersianDate()
            persianDate.shMonth = data.entries[0].date?.month!!
            persianDate.shDay = data.entries[0].date?.day!!
            entryListDate.text = PersianDateFormat.format(
                persianDate,
                "j F",
                PersianDateFormat.PersianDateNumberCharacter.FARSI
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<RecyclerViewData>) {
        this.data = data as MutableList<RecyclerViewData>
        notifyDataSetChanged()
    }

    fun addEntry(entry: Entry) {
        val findData = data.find {
            it.entries[0].date == entry.date
        }
        findData?.adapter?.add(entry)
        if(findData == null){
            data.add(0, RecyclerViewData(mutableListOf(entry)))
            notifyItemInserted(0)
        }
    }

    fun removeItem(entry: Entry) {
        val findData = data.find {
            it.entries.contains(entry)
        }
        findData?.adapter?.remove(entry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entry_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        val size = data.size

        if (size == 0 && emptyStateEnum == EmptyStateEnum.INVISIBLE) {
            emptyStateEnum = EmptyStateEnum.VISIBLE
            emptyStateEventListener.emptyStateVisibility(true)
        } else if (size != 0 && emptyStateEnum == EmptyStateEnum.VISIBLE) {
            emptyStateEnum = EmptyStateEnum.INVISIBLE
            emptyStateEventListener.emptyStateVisibility(false)
        }

        return data.size
    }

    fun create(
        context: Context,
        entryEventListener: EntryEventLister,
        emptyStateEventListener: EmptyStateListener,
        data: List<RecyclerViewData>
    ) {
        this.context = context
        this.entryEventListener = entryEventListener
        this.emptyStateEventListener = emptyStateEventListener
        this.data = data as MutableList<RecyclerViewData>
    }
}

