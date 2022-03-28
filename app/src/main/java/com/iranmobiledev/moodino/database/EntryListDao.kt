package com.iranmobiledev.moodino.database



import androidx.room.*
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.EntryList

@Dao
interface EntryListDao {

    @Insert
    suspend fun add(entries : EntryList) : Long

    @Update
    suspend fun update(entries: EntryList) : Int

    @Delete
    suspend fun delete(entries: EntryList) : Int

    @Query("SELECT * FROM table_entries")
    suspend fun getAll() : List<EntryList>

}