package com.iranmobiledev.moodino

import android.app.Application
import android.content.SharedPreferences
import com.iranmobiledev.moodino.data.activities1
import com.iranmobiledev.moodino.data.activities2
import com.iranmobiledev.moodino.data.activities3
import com.iranmobiledev.moodino.database.AppDatabase
import com.iranmobiledev.moodino.ui.entries.EntryViewModel
import com.iranmobiledev.moodino.utlis.MoodinoSharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() , KoinComponent{
    override fun onCreate() {
        super.onCreate()

        val database = AppDatabase.getAppDatabase(this)

        val modules = module {
            viewModel { EntryViewModel(database.getEntryListDao, database.getActivityDao) }
        }
        startKoin {
            androidContext(this@App)
            modules(modules)
        }

        val sharedPref : SharedPreferences = MoodinoSharedPreferences.create(this)
        val firstEnter = sharedPref.getBoolean("first_enter", false)
        if(!firstEnter)
            makeDefaultActivities()

    }

    private fun makeDefaultActivities() {
        val viewModel : EntryViewModel = get()
        viewModel.addActivity(activities1())
        viewModel.addActivity(activities2())
        viewModel.addActivity(activities3())
    }
}