package com.iranmobiledev.moodino.repository.entry

import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.repository.entry.source.EntryLocalDataSource
import kotlinx.coroutines.flow.Flow

class EntryRepositoryImpl(private val localDataSource: EntryLocalDataSource) : EntryRepository {
    override suspend fun add(entry: Entry): Long {
        return localDataSource.add(entry)
    }

    override fun update(entry: Entry): Int {
        return localDataSource.update(entry)
    }

    override suspend fun delete(entry: Entry): Int {
        return localDataSource.delete(entry)
    }

    override suspend fun getAll(): List<Entry> {
        return localDataSource.getAll()
    }
}