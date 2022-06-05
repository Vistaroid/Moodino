package com.iranmobiledev.moodino.ui.entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.ActivityAndCategory
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

    private val activities = MutableLiveData<List<ActivityAndCategory>>()

    init {
        fetchActivities()
    }

    fun fetchActivities(){
        viewModelScope.launch(Dispatchers.IO) {
            activityRepository.getAll().collect{
                activities.postValue(it)
            }
        }
    }

    fun getActivities(): LiveData<List<ActivityAndCategory>>{
        return activities
    }
}