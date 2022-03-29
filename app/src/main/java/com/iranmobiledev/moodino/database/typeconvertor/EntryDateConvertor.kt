package com.iranmobiledev.moodino.database.typeconvertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iranmobiledev.moodino.data.EntryDate

class EntryDateConvertor {
    @TypeConverter
    fun fromEntryDate(entryDate: EntryDate) : String{
        val gson = Gson()
        val type = object : TypeToken<EntryDate>(){}.type
        return gson.toJson(entryDate, type)
    }

    @TypeConverter
    fun toEntryDate(entryDate: String) : EntryDate{
        val gson = Gson()
        val type = object : TypeToken<EntryDate>(){}.type
        return gson.fromJson(entryDate, type)
    }
}