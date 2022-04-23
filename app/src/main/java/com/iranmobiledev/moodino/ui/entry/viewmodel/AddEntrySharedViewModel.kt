package com.iranmobiledev.moodino.ui.entry.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iranmobiledev.moodino.data.Entry

class AddEntrySharedViewModel: ViewModel() {
    var photo: String = ""
    var entry = Entry()
    var newEntry = MutableLiveData<Entry>()

    fun newEntryAdded() : LiveData<Entry>{
        return newEntry
    }
}