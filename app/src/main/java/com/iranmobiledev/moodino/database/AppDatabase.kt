package com.iranmobiledev.moodino.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.database.typeconvertor.*


@Database(version = 1, exportSchema = false, entities = [Entry::class, Activity::class])
@TypeConverters(EntryTypeConvertor::class, ActivityTypeConvertor::class, EntryDateConvertor::class, EntryTimeConvertor::class)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        @JvmStatic
        private var database : AppDatabase? = null

        @JvmStatic
        fun getAppDatabase(context: Context) : AppDatabase{
            if(database == null)
                database = Room.databaseBuilder(context, AppDatabase::class.java, "name")
                    .allowMainThreadQueries().build()
            return database as AppDatabase
        }
    }

    abstract val getEntryDao : EntryDao
    abstract val getActivityDao : ActivitiesDao
}