package com.iranmobiledev.moodino.database



import androidx.room.*
import com.iranmobiledev.moodino.data.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    @Insert
    fun add(entry : Entry) : Long

    @Update
    fun update(entry: Entry) : Int

    @Delete
    fun delete(entry : Entry) : Int

    @Query("SELECT * FROM table_entry ORDER BY id DESC")
    fun getAll() : Flow<List<Entry>>

}