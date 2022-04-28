package com.iranmobiledev.moodino.ui.entry


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.repository.activity.ActivityRepository
import com.iranmobiledev.moodino.repository.entry.EntryRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EntryViewModel(
    private val entryRepository: EntryRepository,
    private val activityRepository: ActivityRepository
) : BaseViewModel() {
    private val entriesLiveData = MutableLiveData<List<List<Entry>>>()


    init {
        fetchEntries()
    }

    private fun fetchEntries() {
        viewModelScope.launch(Dispatchers.IO) {
            entryRepository.getAll().collect{
                entriesLiveData.postValue(makeListFromEntries(it as MutableList<Entry>))
                println("size123 of lsit is ${it.size}")
            }

        }
    }
    private fun makeListFromEntries(entries: MutableList<Entry>): List<List<Entry>> {
        val listOfEntries = ArrayList<ArrayList<Entry>>()
        entries.forEach { entry ->
            val filteredList = entries.filter { it.date == entry.date }
            if (!listOfEntries.contains(filteredList))
                listOfEntries.add(0, filteredList as ArrayList<Entry>)
        }
        return listOfEntries
    }
    fun deleteEntry(entry: Entry) {
        viewModelScope.launch(Dispatchers.IO + CoroutineName("deleteCoroutine")) {
            entryRepository.delete(entry)
        }
    }

    fun updateEntry(entry: Entry) {
        entryRepository.update(entry)
    }

    fun getEntries(): LiveData<List<List<Entry>>> {
        return entriesLiveData
    }

    fun addActivity(activity: Activity): Long {
        return activityRepository.add(activity)
    }

}