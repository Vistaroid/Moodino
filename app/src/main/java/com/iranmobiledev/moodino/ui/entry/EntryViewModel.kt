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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryViewModel(
    private val entryRepository: EntryRepository,
    private val activityRepository: ActivityRepository
) : BaseViewModel() {

    private val entries = MutableLiveData<List<RecyclerViewData>>()

    init {
        fetchEntries()
    }

    fun fetchEntries() {
        viewModelScope.launch (Dispatchers.IO){
             entryRepository.getAll().collectLatest{
                entries.postValue(makeListFromEntries(it as MutableList<Entry>, mutableListOf()))
            }
        }
    }
    fun update(entry: Entry){
        viewModelScope.launch (Dispatchers.IO){
            entryRepository.update(entry)
        }
    }
    fun addEntry(entry: Entry) {
        viewModelScope.launch (Dispatchers.IO){
            entryRepository.add(entry)
        }
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