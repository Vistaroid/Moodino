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


class EntryViewModel(
    private val entryRepository: EntryRepository,
    private val activityRepository: ActivityRepository
) : BaseViewModel() {

    fun deleteEntry(entry: Entry) {
        viewModelScope.launch(Dispatchers.IO + CoroutineName("deleteCoroutine")){
            entryRepository.delete(entry)
        }
    }

    fun updateEntry(entry: Entry) {
        entryRepository.update(entry)
    }

    fun getEntries(): List<List<Entry>> {
        var entries = mutableListOf<Entry>()
        val job = viewModelScope.launch (Dispatchers.IO){
            entries = entryRepository.getAll() as MutableList<Entry>
        }
        runBlocking { job.join() }
        return makeListFromEntries(entries)
    }


    private fun makeListFromEntries(entries: MutableList<Entry>): List<List<Entry>> {
        val listOfEntries = ArrayList<ArrayList<Entry>>()
        entries.forEach { entry ->
            val filteredList = entries.filter { it.date ==  entry.date}
            if(!listOfEntries.contains(filteredList))
                listOfEntries.add(0, filteredList as ArrayList<Entry>)
        }
        return listOfEntries
    }

    fun addActivity(activity: Activity): Long {
        return activityRepository.add(activity)
    }

}