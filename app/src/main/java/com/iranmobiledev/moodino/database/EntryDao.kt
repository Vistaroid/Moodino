package com.iranmobiledev.moodino.database



import androidx.room.*
import com.iranmobiledev.moodino.data.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    @Insert
    suspend fun add(entry : Entry) : Long

    @Update
    fun update(entry: Entry) : Int

    @Delete
    fun delete(entry : Entry) : Int

    @Query("SELECT * FROM table_entry")
    suspend fun getAll() : List<Entry>

}