package com.iranmobiledev.moodino.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.sql.Timestamp

@Entity(tableName = "table_entries")
data class EntryList(
    @PrimaryKey(autoGenerate = true)
    val id : Int?,
    //todo change date datatype
    val date : String,
    val entries : List<Entry>
)