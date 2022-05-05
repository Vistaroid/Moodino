package com.iranmobiledev.moodino.repository.entry

import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.database.EntryDao
import kotlinx.coroutines.flow.Flow

class EntryRepositoryImpl(private val entryDao: EntryDao) : EntryRepository {
    override fun add(entry: Entry): Long {
        return entryDao.add(entry)
    }

    override fun update(entry: Entry): Int {
        return entryDao.update(entry)
    }

    override fun delete(entry: Entry): Int {
        return entryDao.delete(entry)
    }

    override fun getAll(): Flow<List<Entry>> {
        return entryDao.getAll()
    }
}