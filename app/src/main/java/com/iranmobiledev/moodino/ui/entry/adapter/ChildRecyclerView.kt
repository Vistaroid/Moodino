package com.iranmobiledev.moodino.ui.entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.iranmobiledev.moodino.utlis.HalinoPopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.databinding.ItemEntryBinding
import com.iranmobiledev.moodino.listener.EntryEventLister
import com.iranmobiledev.moodino.utlis.*
import org.koin.core.component.KoinComponent
import saman.zamani.persiandate.PersianDate

class ChildRecyclerView(
    private var entryEventLister: EntryEventLister,
    val entries: MutableList<Entry>,
    private val context: Context,
    private val language: Int
) : RecyclerView.Adapter<ChildRecyclerView.ViewHolder>(), KoinComponent {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val emojiFactory = EmojiFactory.create(context)
        private val binding: ItemEntryBinding = ItemEntryBinding.bind(itemView)
        private val entryIcon: ImageView = binding.EntryIcon
        private val moreIcon: ImageView = binding.moreIcon
        private val entryTitle: TextView = binding.entryTitle
        private val entryImageContainer: MaterialCardView = binding.imageContainer
        private val entryNote: TextView = binding.entryNote
        private val activityRv: RecyclerView = binding.activityRv

        private val spacer: View = binding.spacer
        private val entryDate: TextView = binding.entryDate
        private val entryViewGroup : ViewGroup = binding.entryViewGroup

        @SuppressLint("ResourceType", "SetTextI18n")
        fun bind(entry: Entry, index: Int) {
            val emoji = emojiFactory.getEmoji(entry.emojiValue.toFloat())
            binding.entryItem = entry
            itemsVisibility(entry, index)
            entryIcon.setImageResource(emoji.image)
            entryViewGroup.setOnClickListener { makePopupMenu(entry, moreIcon) }
            moreIcon.setOnClickListener { makePopupMenu(entry, it) }
            entryTitle.text = emoji.title
            entryTitle.setTextColor(emoji.color)
        }

        private fun itemsVisibility(entry: Entry, index: Int) {
            if (index == entries.size - 1)
                spacer.visibility = View.GONE
            if (entries.size != 1)
                entryDate.visibility = View.GONE
            else
                setEntryDate(entry)
            if (entry.note.isNotEmpty())
                entryNote.visibility = View.VISIBLE
            if (entry.photoPath.isNotEmpty())
                entryImageContainer.visibility = View.VISIBLE
            if (entry.activities.isNotEmpty())
                activityRv.visibility = View.VISIBLE
        }

        private fun setEntryDate(entry: Entry) {
            entryDate.visibility = View.VISIBLE
            val persianDate = PersianDateObj.persianDate
            val date = EntryDate(
                persianDate.shYear,
                persianDate.shMonth,
                persianDate.shDay
            )
            if (entry.date == date)
                entryDate.text = todayStringDate(language)
            else if (entry.date == yesterday(PersianDateObj.persianDate))
                entryDate.text = yesterdayStringDate(language)
        }

        private fun yesterday(persianDate: PersianDate): EntryDate {
            return EntryDate(
                persianDate.shYear,
                persianDate.shMonth,
                persianDate.shDay - 1
            )
        }
        private fun yesterdayStringDate(language: Int): String {
            val persianDate = PersianDateObj.persianDate
            persianDate.shDay = persianDate.shDay-1
            return persianDateFormat(language,pattern = "j F", date = persianDate)
        }
        private fun todayStringDate(language: Int): String {
            return persianDateFormat(language, pattern = "j F")
        }

    }

    private fun makePopupMenu(witchEntry: Entry, view: View) {
        val moodinoPopup = HalinoPopupMenu(view,R.layout.popup_view)
        val contentView = moodinoPopup.getContentView()
        val popupWindow = moodinoPopup.getPopupWindow()

        popupWindow.isOutsideTouchable = true
        popupWindow.elevation = 15f
        popupWindow.isFocusable = true
        popupWindow.animationStyle = R.anim.popup_window
        popupWindow.showAsDropDown(view)

        val deleteTv = contentView.findViewById<TextView>(R.id.deleteTv)
        deleteTv.setOnClickListener {
            entryEventLister.delete(witchEntry)
            popupWindow.dismiss()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entry, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(entry: Entry) {
        val entryInList = entries.find {
            it == entry
        }
        entryInList?.let {
            val index = entries.indexOf(entry)
            entries.remove(entry)
            notifyItemRemoved(index)
        }
        if(entries.size == 1 || entries.size == 0)
            notifyDataSetChanged()
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