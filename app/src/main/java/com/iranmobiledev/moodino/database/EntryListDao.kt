package com.iranmobiledev.moodino.database



import androidx.room.*
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryList

@Dao
interface EntryListDao {

    @Insert
    fun add(entries : EntryList) : Long

    @Update
    fun update(entries: EntryList) : Int

    @Delete
    fun delete(entries: EntryList) : Int

    @Query("SELECT * FROM table_entries")
    fun getAll() : List<EntryList>

}