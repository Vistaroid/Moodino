package com.iranmobiledev.moodino.ui.entry.viewmodel

import androidx.lifecycle.viewModelScope
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.repository.activity.ActivityRepository
import com.iranmobiledev.moodino.repository.entry.EntryRepository
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EntryDetailViewModel(
    private val entryRepository: EntryRepository,
    private val activityRepository: ActivityRepository
) : BaseViewModel() {

    fun addEntry(entry: Entry) {
        val job = viewModelScope.launch(Dispatchers.IO + CoroutineName("addEntryCoroutine")) {
            entryRepository.add(entry)
        }
        runBlocking {
            job.join()
        }
    }

    fun getActivities(): List<List<Activity>> {
        return sortActivities(activityRepository.getAll())
    }

    private fun sortActivities(activities : List<Activity>) : List<List<Activity>>{
        val newActivities = ArrayList<List<Activity>>()
        return newActivities
    }
}