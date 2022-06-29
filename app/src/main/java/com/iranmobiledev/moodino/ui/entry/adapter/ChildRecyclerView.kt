package com.iranmobiledev.moodino.ui.entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
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
import com.iranmobiledev.moodino.callback.EntryEventLister
import com.iranmobiledev.moodino.utlis.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import saman.zamani.persiandate.PersianDate


class ChildRecyclerView(
    private var entryEventLister: EntryEventLister,
    var entries: List<Entry>,
    private val context: Context,
    private val language: Int,
) : RecyclerView.Adapter<ChildRecyclerView.ViewHolder>(), KoinComponent {

    private val persianDate = PersianDate()
    private var newEntry: Entry? = null
    private val imageLoader: ImageLoadingService by inject()
    init {
        entries = entries.sortedByDescending { it.time.minutes.toInt() }
        entries = entries.sortedByDescending { it.time.hour.toInt() }
    }

    inner class ViewHolder(private val itemBinding: ItemEntryBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val emojiFactory = EmojiFactory.create(context)

        @SuppressLint("ResourceType", "SetTextI18n")
        fun bind(entry: Entry, index: Int) {
            val emoji = emojiFactory.getEmoji(entry.emojiValue)
            itemBinding.entryItem = entry
            showNewEntry(entry)
            setTime(entry.time)
            setupItemsVisibility(entry, index)
            itemBinding.apply {
                entryViewGroup.setOnClickListener { makePopupMenu(entry, moreIcon) }
                moreIcon.setOnClickListener { makePopupMenu(entry, it) }
                entryTitle.text = emoji.title
                entryTitle.setTextColor(emoji.color)
                EntryIcon.setImageResource(emoji.image)
                if(entry.photoPath.isNotEmpty())
                    imageLoader.load(context,entry.photoPath,entryImage)
            }
            setupSmallActivitiesRv(entry)
        }

        private fun showNewEntry(currentEntry: Entry) {
            newEntry?.let { mNewEntry ->
                if (mNewEntry.emojiValue == currentEntry.emojiValue &&
                    mNewEntry.date == currentEntry.date &&
                    mNewEntry.time == currentEntry.time &&
                    mNewEntry.photoPath == currentEntry.photoPath &&
                    mNewEntry.activities == currentEntry.activities){
                    val alphaAnimation = AlphaAnimation(0f, 1f)
                    alphaAnimation.duration = 4000
                    itemView.animation = alphaAnimation
                    alphaAnimation.start()
                    newEntry = null
                }
            }
        }

        private fun setTime(entryTime: EntryTime) {
            val persianDate = PersianDate()
            persianDate.hour = Integer.parseInt(entryTime.hour)
            persianDate.minute = Integer.parseInt(entryTime.minutes)
            itemBinding.entryTimeTv.text = getTime(persianDate, language = language)
        }

        private fun setupSmallActivitiesRv(entry: Entry) {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.FLEX_START

            itemBinding.apply {
                activityRv.layoutManager = layoutManager
                activityRv.adapter = SmallActivityAdapter(entry.activities, entry.emojiValue)
            }
        }

        private fun setupItemsVisibility(entry: Entry, index: Int) {
            if (index == entries.size - 1)
                itemBinding.spacer.visibility = View.GONE
            if (entries.size != 1)
                itemBinding.entryDate.visibility = View.GONE
            else
                setEntryDate(entry)
            if (entry.note.isNotEmpty())
                itemBinding.entryNote.visibility = View.VISIBLE
            if (entry.photoPath.isNotEmpty())
                itemBinding.imageContainer.visibility = View.VISIBLE
            if (entry.activities.isNotEmpty())
                itemBinding.activityRv.visibility = View.VISIBLE
        }

        private fun setEntryDate(entry: Entry) {
            itemBinding.entryDate.visibility = View.VISIBLE
            val date = EntryDate(
                persianDate.shYear,
                persianDate.shMonth,
                persianDate.shDay
            )
            when (entry.date) {
                date -> itemBinding.entryDate.text = todayStringDate()
                yesterday(persianDate) -> itemBinding.entryDate.text = yesterdayStringDate()
                else -> {
                    entry.date.let {
                        persianDate.shDay = it.day
                        persianDate.shMonth = it.month
                        persianDate.shYear = it.year
                    }
                    itemBinding.entryDate.text = otherDayString(persianDate)
                }
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
            return getDate(pattern = "j F Y", date = persianDate, language = language)
        }

        private fun todayStringDate(): String {
            return getDate(pattern = "j F Y", language = language)
        }

        private fun otherDayString(date: PersianDate, pattern: String = "j F Y"): String {
            return getDate(pattern = pattern, date = date, language = language)
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

    fun newEntry(entry: Entry) {
        newEntry = entry
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemEntryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entries[position], position)
    }

    override fun getItemCount(): Int {
        return entries.size
    }
}