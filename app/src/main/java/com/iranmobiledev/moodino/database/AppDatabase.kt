package com.iranmobiledev.moodino.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iranmobiledev.moodino.data.EntryList
import com.iranmobiledev.moodino.database.typeconvertor.ActivityTypeConvertor
import com.iranmobiledev.moodino.database.typeconvertor.EntryTypeConvertor


@Database(version = 1, exportSchema = false, entities = [EntryList::class])
@TypeConverters(EntryTypeConvertor::class, ActivityTypeConvertor::class)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        @JvmStatic
        private var database : AppDatabase? = null

        @JvmStatic
        fun getAppDatabase(context: Context) : AppDatabase{
            if(database == null)
                database = Room.databaseBuilder(context, AppDatabase::class.java, "name").build()
            return database as AppDatabase
        }
    }

    abstract val getEntryListDao : EntryListDao
    abstract val getActivityDao : ActivitiesDao

}