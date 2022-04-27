package com.iranmobiledev.moodino.repository.entry.source

import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.database.EntryDao
import kotlinx.coroutines.flow.Flow

class EntryLocalDataSource(private val entryDao: EntryDao) : EntryDataSource {
    override suspend fun add(entry: Entry): Long {
        return entryDao.add(entry)
    }

    override fun update(entry: Entry): Int {
        return entryDao.update(entry)
    }

    override suspend fun delete(entry: Entry): Int {
        return entryDao.delete(entry)
    }

    override suspend fun getAll(): Flow<List<Entry>> {
        return entryDao.getAll()
    }
}