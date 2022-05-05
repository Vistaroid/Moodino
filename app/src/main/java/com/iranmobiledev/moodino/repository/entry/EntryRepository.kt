package com.iranmobiledev.moodino.repository.entry

import androidx.lifecycle.LiveData
import com.iranmobiledev.moodino.data.Entry
import kotlinx.coroutines.flow.Flow

interface EntryRepository {
    fun add(entry: Entry): Long
    fun update(entry: Entry): Int
    fun delete(entry: Entry): Int
    fun getAll(): Flow<List<Entry>>
}