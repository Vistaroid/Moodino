package com.iranmobiledev.moodino.ui.entries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.ActivityList
import com.iranmobiledev.moodino.data.EntryList
import com.iranmobiledev.moodino.database.ActivitiesDao
import com.iranmobiledev.moodino.database.EntryListDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EntryViewModel(private val entryListDao: EntryListDao, private val activitiesDao: ActivitiesDao) : BaseViewModel() {

    private val mutableActivitiesLiveData = MutableLiveData<List<ActivityList>>()

    fun addEntry(entryList: EntryList) {
<<<<<<< Updated upstream
            entryListDao.add(entryList)

=======
           entryListDao.add(entryList)
>>>>>>> Stashed changes
    }
    fun deleteEntry(entryList: EntryList){
            entryListDao.delete(entryList)
    }
    fun updateEntry(entryList: EntryList){
<<<<<<< Updated upstream
            entryListDao.update(entryList)
=======
        entryListDao.update(entryList)
>>>>>>> Stashed changes
    }
    fun getEntries() : List<EntryList>{
        var entriesList = ArrayList<EntryList>()
        entriesList = entryListDao.getAll() as ArrayList<EntryList>
        return entriesList
    }

    fun getActivities() : List<ActivityList> {
        var activitiesList = ArrayList<ActivityList>()
        viewModelScope.launch (Dispatchers.IO){
            activitiesList = activitiesDao.getAll() as ArrayList<ActivityList>
            mutableActivitiesLiveData.postValue(activitiesList)
        }
        return activitiesList
    }

    fun addActivity(activities : ActivityList) {
        viewModelScope.launch(Dispatchers.IO){
            activitiesDao.add(activities)
        }
    }

    fun activitiesLiveData() : LiveData<List<ActivityList>> {
        return mutableActivitiesLiveData
    }
}