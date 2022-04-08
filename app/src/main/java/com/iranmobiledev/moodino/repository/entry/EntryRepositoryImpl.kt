package com.iranmobiledev.moodino.repository.entry

import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.repository.entry.source.EntryLocalDataSource

class EntryRepositoryImpl(private val localDataSource: EntryLocalDataSource) : EntryRepository {
    override fun add(entry: Entry): Long {
        return localDataSource.add(entry)
    }

    override fun update(entry: Entry): Int {
        return localDataSource.update(entry)
    }

    override fun delete(entry: Entry): Int {
        return localDataSource.delete(entry)
    }

    override fun getAll(): List<Entry> {
        return localDataSource.getAll()
    }
}