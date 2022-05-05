package com.iranmobiledev.moodino.ui.entry

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
        entryRepository.add(entry)
    }
}