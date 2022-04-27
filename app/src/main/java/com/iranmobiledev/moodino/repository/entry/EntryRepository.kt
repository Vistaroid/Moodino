package com.iranmobiledev.moodino.repository.entry

import com.iranmobiledev.moodino.data.Entry
import kotlinx.coroutines.flow.Flow

interface EntryRepository {
    suspend fun add(entry : Entry) : Long
    fun update(entry: Entry) : Int
    suspend fun delete(entry: Entry) : Int
    suspend fun getAll() : Flow<List<Entry>>
}