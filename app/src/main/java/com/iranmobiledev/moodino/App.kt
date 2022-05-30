package com.iranmobiledev.moodino

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.data.Category
import com.iranmobiledev.moodino.database.AppDatabase
import com.iranmobiledev.moodino.repository.activity.ActivityRepository
import com.iranmobiledev.moodino.repository.activity.ActivityRepositoryImpl
import com.iranmobiledev.moodino.repository.activity.source.ActivityLocalDataSource
import com.iranmobiledev.moodino.repository.entry.EntryRepository
import com.iranmobiledev.moodino.repository.entry.EntryRepositoryImpl
import com.iranmobiledev.moodino.ui.calendar.CalendarViewModel
import com.iranmobiledev.moodino.repository.more.source.MoreRepository
import com.iranmobiledev.moodino.repository.more.source.MoreRepositoryImpl
import com.iranmobiledev.moodino.ui.MainActivityViewModel
import com.iranmobiledev.moodino.ui.entry.EntryDetailViewModel
import com.iranmobiledev.moodino.ui.entry.EntryViewModel
import com.iranmobiledev.moodino.ui.more.pinLock.PinLockViewModel
import com.iranmobiledev.moodino.ui.entry.adapter.EntryContainerAdapter
import com.iranmobiledev.moodino.ui.more.MoreViewModel

import com.iranmobiledev.moodino.ui.more.reminder.ReminderViewModel
import com.iranmobiledev.moodino.ui.splashScreen.SplashViewModel
import com.iranmobiledev.moodino.ui.states.viewmodel.StatsFragmentViewModel
import com.iranmobiledev.moodino.utlis.*
import com.iranmobiledev.moodino.utlis.GlideImageLoader
import com.iranmobiledev.moodino.utlis.ImageLoadingService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.module


const val NOTIFICATION_ID = "MOODINO"

class App : Application() , KoinComponent{
    override fun onCreate() {
        super.onCreate()

        val notifiManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelNotifi = NotificationChannel(NOTIFICATION_ID , "notification sms" , NotificationManager.IMPORTANCE_HIGH)
            notifiManager.createNotificationChannel(channelNotifi)
        }

        val database = AppDatabase.getAppDatabase(this)

        val modules = module {
            viewModel { MainActivityViewModel() }
            viewModel { EntryViewModel(get(), get()) }
            viewModel { StatsFragmentViewModel(get()) }
            viewModel { EntryDetailViewModel(get(), get()) }
            viewModel { CalendarViewModel(get()) }
            viewModel { PinLockViewModel(get()) }
            viewModel { MoreViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { ReminderViewModel(get()) }

            factory <EntryRepository> { EntryRepositoryImpl(database.getEntryDao) }
            factory <ActivityRepository> { ActivityRepositoryImpl(ActivityLocalDataSource(database.getCategoryDao)) }
            factory <MoreRepository> { MoreRepositoryImpl(get()) }
            single <ImageLoadingService>{ GlideImageLoader() }
            single { applicationContext.getSharedPreferences("sharedPref", MODE_PRIVATE) }
        }

        startKoin {
            androidContext(this@App)
            modules(modules)
        }

        val sharedPref: SharedPreferences = get()
        //TODO should change here

        val firstEnter = sharedPref.getBoolean(FIRST_ENTER, true)
        if (firstEnter)
            makeActivities()

        when(sharedPref.getInt(MODE_THEME , SYSTEM_DEFAULT)){
            SYSTEM_DEFAULT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun makeActivities() {
        val dataBase = AppDatabase.getAppDatabase(this)
        val categoryDao = dataBase.getCategoryDao
        val activityDao = dataBase.getActivityDao
        val categories = mutableListOf(Category(null,getString(R.string.daily)))

        categories.forEach {
            val id = categoryDao.add(it)
            activityDao.addActivity(Activity(null,id,resources.getResourceEntryName(R.drawable.ic_friends),getString(R.string.friends)))
            activityDao.addActivity(Activity(null,id,resources.getResourceEntryName(R.drawable.ic_date), getString(R.string.mDate)))
            activityDao.addActivity(Activity(null,id,resources.getResourceEntryName(R.drawable.ic_gaming), getString(R.string.gaming)))
            activityDao.addActivity(Activity(null,id,resources.getResourceEntryName(R.drawable.ic_movie), getString(R.string.movie)))
            activityDao.addActivity(Activity(null,id,resources.getResourceEntryName(R.drawable.ic_reading), getString(R.string.reading)))
            activityDao.addActivity(Activity(null,id,resources.getResourceEntryName(R.drawable.ic_relax), getString(R.string.relax)))
            activityDao.addActivity(Activity(null,id,resources.getResourceEntryName(R.drawable.ic_shopping), getString(R.string.shopping)))
            activityDao.addActivity(Activity(null,id,resources.getResourceEntryName(R.drawable.ic_sleep), getString(R.string.sleep)))
            activityDao.addActivity(Activity(null,id,resources.getResourceEntryName(R.drawable.ic_cleaning), getString(R.string.cleaning)))
            activityDao.addActivity(Activity(null,id,resources.getResourceEntryName(R.drawable.ic_brain), getString(R.string.mLearn)))
        }
    }
}