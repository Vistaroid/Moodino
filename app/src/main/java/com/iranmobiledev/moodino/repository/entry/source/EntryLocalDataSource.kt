package com.iranmobiledev.moodino.repository.entry.source

import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.database.EntryDao

class EntryLocalDataSource(private val entryDao: EntryDao) : EntryDataSource {
    override fun add(entry: Entry): Long {
        return entryDao.add(entry)
    }

    override fun update(entry: Entry): Int {
        return entryDao.update(entry)
    }

    override fun delete(entry: Entry): Int {
        return entryDao.delete(entry)
    }

    override fun getAll(): List<Entry> {
        return entryDao.getAll()
    }
}