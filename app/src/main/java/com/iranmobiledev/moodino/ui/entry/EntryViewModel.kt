package com.iranmobiledev.moodino.ui.entry


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.RecyclerViewData
import com.iranmobiledev.moodino.repository.activity.ActivityRepository
import com.iranmobiledev.moodino.repository.entry.EntryRepository
import kotlinx.coroutines.launch

class EntryViewModel(
    private val entryRepository: EntryRepository,
    private val activityRepository: ActivityRepository
) : BaseViewModel() {

    private val entries = MutableLiveData<List<RecyclerViewData>>()

    init {
        fetchEntries()
    }

    private fun fetchEntries() {
        viewModelScope.launch {
            entryRepository.getAll().collect{
                entries.value = makeListFromEntries(it as MutableList<Entry>, mutableListOf())
            }
        }
    }
    fun update(entry: Entry){
        entryRepository.update(entry)
    }
    fun addEntry(entry: Entry) {
        entryRepository.add(entry)
    }
    private fun makeListFromEntries(entries: MutableList<Entry>, sortedList : MutableList<RecyclerViewData>): List<RecyclerViewData> {
        if(entries.size == 0)
            return sortedList
        val filtered = entries.filter { it.date == entries[0].date }
        val notFiltered = entries.filterNot { it.date == entries[0].date }
        sortedList.add(0, RecyclerViewData(entries = filtered, date = filtered[0].date))

        return makeListFromEntries(notFiltered as MutableList<Entry>, sortedList)
    }

    fun deleteEntry(entry: Entry) {
        entryRepository.delete(entry)
    }
    fun getEntries() : LiveData<List<RecyclerViewData>> {
        return entries
    }
}