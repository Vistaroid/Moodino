package com.iranmobiledev.moodino.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.sql.Timestamp

@Entity(tableName = "table_entries")
data class EntryList(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    val date : EntryDate,
    val state : EntryListState,
    val entries : MutableList<Entry>
)