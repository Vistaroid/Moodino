package com.iranmobiledev.moodino.ui.entries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.repository.activity.ActivityRepository
import com.iranmobiledev.moodino.repository.entry.EntryRepository


class EntryViewModel(
    private val entryRepository: EntryRepository,
    private val activityRepository: ActivityRepository
) : BaseViewModel() {

    val listOfEntries = ArrayList<List<Entry>>()

    fun deleteEntry(entry: Entry) {
        entryRepository.delete(entry)
    }

    fun updateEntry(entry: Entry) {
        entryRepository.update(entry)
    }

    fun getEntries(): List<List<Entry>> {
        Log.i("FirstEnter", "entriesContainerRvImpl: $firstEnter")
        return if (firstEnter) makeListFromEntries(entryRepository.getAll() as MutableList<Entry>) else listOfEntries
    }

    private fun makeListFromEntries(entries: MutableList<Entry>): List<List<Entry>> {
        entries.forEach { entry ->
            val filteredList = entries.filter { it.date == entry.date }
            if (!listOfEntries.contains(filteredList))
                listOfEntries.add(0, filteredList)
        }
        return listOfEntries
    }

    fun addActivity(activity: Activity): Long {
        return activityRepository.add(activity)
    }

}