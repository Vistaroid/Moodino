package com.iranmobiledev.moodino.ui.entries

import androidx.lifecycle.viewModelScope
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.ActivityList
import com.iranmobiledev.moodino.data.EntryList
import com.iranmobiledev.moodino.database.ActivitiesDao
import com.iranmobiledev.moodino.database.EntryListDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryViewModel(private val entryListDao: EntryListDao, private val activitiesDao: ActivitiesDao) : BaseViewModel() {

    fun addEntry(entryList: EntryList) {
        viewModelScope.launch (Dispatchers.IO){
            entryListDao.add(entryList)
        }
    }
    fun deleteEntry(entryList: EntryList){
        viewModelScope.launch (Dispatchers.IO){
            entryListDao.delete(entryList)
        }
    }
    fun updateEntry(entryList: EntryList){
        viewModelScope.launch (Dispatchers.IO){
            entryListDao.update(entryList)
        }
    }
    fun getEntries() : List<EntryList>{
        var entriesList = ArrayList<EntryList>()
        viewModelScope.launch (Dispatchers.IO){
            entriesList = entryListDao.getAll() as ArrayList<EntryList>
        }
        return entriesList
    }

    fun getActivities() : List<ActivityList> {
        var activitiesList = ArrayList<ActivityList>()
        viewModelScope.launch (Dispatchers.IO){
            activitiesList = activitiesDao.getAll() as ArrayList<ActivityList>
        }
        return activitiesList
    }
}