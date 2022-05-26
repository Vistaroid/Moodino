package com.iranmobiledev.moodino.ui.entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.card.MaterialCardView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.EntryTime
import com.iranmobiledev.moodino.databinding.ItemEntryBinding
import com.iranmobiledev.moodino.listener.EntryEventLister
import com.iranmobiledev.moodino.utlis.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import saman.zamani.persiandate.PersianDate
import java.util.regex.Pattern


class ChildRecyclerView(
    private var entryEventLister: EntryEventLister,
    var entries: MutableList<Entry>,
    private val context: Context,
    private val language: Int
) : RecyclerView.Adapter<ChildRecyclerView.ViewHolder>(), KoinComponent {

    private val persianDate = PersianDate()
    private val imageLoader: ImageLoadingService by inject()

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
        private val entryViewGroup: ViewGroup = binding.entryViewGroup
        private val entryTime: TextView = binding.entryTimeTv

        @SuppressLint("ResourceType", "SetTextI18n")
        fun bind(entry: Entry, index: Int) {
            val emoji = emojiFactory.getEmoji(entry.emojiValue)
            setTime(entry.time)
            binding.entryItem = entry
            itemsVisibility(entry, index)
            binding.EntryIcon.setImageResource(emoji.image)
            entryViewGroup.setOnClickListener { makePopupMenu(entry, moreIcon) }
            moreIcon.setOnClickListener { makePopupMenu(entry, it) }
            entryTitle.text = emoji.title
            entryTitle.setTextColor(emoji.color)
            binding.activityRv.setOnClickListener { makePopupMenu(entry, it) }
            setupSmallActivitiesRv(entry)
        }

        private fun setTime(entryTime: EntryTime) {
            val persianDate = PersianDate()
            persianDate.hour = Integer.parseInt(entryTime.hour)
            persianDate.minute = Integer.parseInt(entryTime.minutes)
            this.entryTime.text = getTime(persianDate)
        }

        private fun setupSmallActivitiesRv(entry: Entry) {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.FLEX_START
            binding.activityRv.layoutManager = layoutManager
            binding.activityRv.adapter = SmallActivityAdapter(entry.activities, entry.emojiValue)
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
            val date = EntryDate(
                persianDate.shYear,
                persianDate.shMonth,
                persianDate.shDay
            )
            if (entry.date == date)
                entryDate.text = todayStringDate()
            else if (entry.date == yesterday(persianDate))
                entryDate.text = yesterdayStringDate()
            else {
                entry.date.let {
                    persianDate.shDay = it.day
                    persianDate.shMonth = it.month
                    persianDate.shYear = it.year
                }
                entryDate.text = otherDayString(persianDate)
            }
        }

        private fun yesterday(persianDate: PersianDate): EntryDate {
            return EntryDate(
                persianDate.shYear,
                persianDate.shMonth,
                persianDate.shDay - 1
            )
        }

        private fun yesterdayStringDate(): String {
            persianDate.shDay = persianDate.shDay - 1
            return persianDateFormat(pattern = "j F", date = persianDate)
        }

        private fun todayStringDate(): String {
            return persianDateFormat(pattern = "j F")
        }

        private fun otherDayString(date: PersianDate, pattern: String = "j F"): String {
            return persianDateFormat(pattern = pattern, date = date)
        }
    }

    private fun makePopupMenu(witchEntry: Entry, view: View) {
        val moodinoPopup = HalinoPopupMenu(view, R.layout.popup_view)
        val contentView = moodinoPopup.getContentView()
        val popupWindow = moodinoPopup.getPopupWindow()

        popupWindow.isOutsideTouchable = true
        popupWindow.elevation = 15f
        popupWindow.isFocusable = true
        popupWindow.animationStyle = R.anim.popup_window
        popupWindow.showAsDropDown(view)

        val deleteTv = contentView.findViewById<TextView>(R.id.deleteTv)
        val editTv = contentView.findViewById<TextView>(R.id.editTv)
        deleteTv.setOnClickListener {
            entryEventLister.delete(witchEntry)
            popupWindow.dismiss()
        }
        editTv.setOnClickListener {
            entryEventLister.update(witchEntry)
            popupWindow.dismiss()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entries[position], position)
    }

    override fun getItemCount(): Int {
        return entries.size
    }
}