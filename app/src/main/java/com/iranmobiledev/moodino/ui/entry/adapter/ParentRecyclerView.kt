package com.iranmobiledev.moodino.ui.entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.RecyclerViewData
import com.iranmobiledev.moodino.databinding.ItemEntryContainerBinding
import com.iranmobiledev.moodino.listener.EntryEventLister
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat


class EntryContainerAdapter : RecyclerView.Adapter<EntryContainerAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var entryEventListener: EntryEventLister
    private lateinit var data: MutableList<RecyclerViewData>
    private var language: Int = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemEntryContainerBinding = ItemEntryContainerBinding.bind(itemView)
        private val entryListDate = binding.entriesDateTitle
        private val entryRecyclerView = binding.entryRv
        private val entriesLable: ViewGroup = binding.entriesLable
        private val lableCircle: ImageView = binding.lableCircle
        private val entriesDateTitle: TextView = binding.entriesDateTitle

        @SuppressLint("SetTextI18n")
        fun bind(data: RecyclerViewData) {
            if (data.entries.isNotEmpty()) {
                setupUi(data)
                setupNestedAdapter(data)
            }
            if (data.entries.size == 1)
                entriesLable.visibility = View.GONE
        }

        private fun setupNestedAdapter(data: RecyclerViewData) {
            entryRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            entryRecyclerView.itemAnimator = null
            val adapter =
                ChildRecyclerView(
                    entryEventListener,
                    data.entries as MutableList<Entry>,
                    context,
                    language
                )
            entryRecyclerView.adapter = adapter
            data.adapter = adapter
        }

        private fun setupUi(data: RecyclerViewData) {
            val result = setupLableColor(data)
            drawLable(result)
            val persianDate = PersianDate()
            persianDate.shMonth = data.entries[0].date?.month!!
            persianDate.shDay = data.entries[0].date?.day!!
            entryListDate.text = PersianDateFormat.format(
                persianDate,
                "j F",
                PersianDateFormat.PersianDateNumberCharacter.FARSI
            )
        }

        private fun drawLable(result: Int) {
            when (result) {
                1 -> {
                    setLableColor(R.color.red_light)
                    setLableCircleColor(R.color.red_dark)
                    entriesDateTitle.setTextColor(ContextCompat.getColor(context,R.color.red_dark))
                }
                2 -> {
                    setLableColor(R.color.orange_light)
                    setLableCircleColor(R.color.orange_dark)
                    entriesDateTitle.setTextColor(ContextCompat.getColor(context,R.color.orange_dark))
                }
                3 -> {
                    setLableColor(R.color.blue_light)
                    setLableCircleColor(R.color.blue_dark)
                    entriesDateTitle.setTextColor(ContextCompat.getColor(context,R.color.blue_dark))
                }
                4 -> {
                    setLableColor(R.color.green_light)
                    setLableCircleColor(R.color.green_dark)
                    entriesDateTitle.setTextColor(ContextCompat.getColor(context,R.color.green_dark))
                }
                5 -> {
                    setLableColor(R.color.turquoise_light)
                    setLableCircleColor(R.color.turquoise_dark)
                    entriesDateTitle.setTextColor(ContextCompat.getColor(context,R.color.turquoise_dark))
                }
            }
        }

        private fun setLableColor(@ColorRes color: Int){
            entriesLable.backgroundTintList =
                context.resources.getColorStateList(color, context.theme)
        }
        private fun setLableCircleColor(@ColorRes color: Int){
            lableCircle.setColorFilter(
                ContextCompat.getColor(context, color),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }

        private fun setupLableColor(data: RecyclerViewData): Int {
            var sum = 0
            data.entries.forEach {
                sum += it.type
            }
            return sum / data.entries.size
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
        if (findData == null) {
            data.add(0, RecyclerViewData(mutableListOf(entry)))
            notifyItemInserted(0)
        }
    }

    fun removeItem(entry: Entry) {
        val foundData = data.find {
            it.entries.contains(entry)
        }
        foundData?.adapter?.remove(entry)
        if (foundData?.entries?.isEmpty() == true) {
            val index = data.indexOf(foundData)
            notifyItemRemoved(index)
        }
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
        return data.size
    }

    fun create(
        context: Context,
        entryEventListener: EntryEventLister,
        data: List<RecyclerViewData>,
        language: Int
    ) {
        this.context = context
        this.entryEventListener = entryEventListener
        this.data = data as MutableList<RecyclerViewData>
        this.language = language
    }
}

