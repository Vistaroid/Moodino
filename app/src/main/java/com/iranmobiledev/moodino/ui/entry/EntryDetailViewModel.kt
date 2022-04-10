package com.iranmobiledev.moodino.ui.entry

import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.repository.activity.ActivityRepository
import com.iranmobiledev.moodino.repository.entry.EntryRepository

class EntryDetailViewModel(
    private val entryRepository: EntryRepository,
    private val activityRepository: ActivityRepository
) : BaseViewModel() {

    fun addEntry(entry: Entry): Long {
        return entryRepository.add(entry)
    }

    fun getActivities(): List<List<Activity>> {
        return sortActivities(activityRepository.getAll())
    }

    private fun sortActivities(activities : List<Activity>) : List<List<Activity>>{
        val newActivities = ArrayList<List<Activity>>()
        return newActivities
    }
}