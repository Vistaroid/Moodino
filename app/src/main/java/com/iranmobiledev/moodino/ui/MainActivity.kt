package com.iranmobiledev.moodino.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.ActivityMainBinding
import com.iranmobiledev.moodino.ui.calendar.calendarpager.initGlobal

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGlobal(this)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //setup navigation controller
        val navView: BottomNavigationView = activityMainBinding.bottomNavigationView
        val navController = findNavController(R.id.fragmentContainerView)
        navView.setupWithNavController(navController)

        val model: MainActivityViewModel by viewModels()

        //fab 3 buttons to be animate
        val fab: FloatingActionButton = activityMainBinding.fab
        val view1 = activityMainBinding.yesterday
        val view2 = activityMainBinding.today
        val view3 = activityMainBinding.otherDays

        //fix nav background problem
        navView.background = null

        fab.setOnClickListener { it ->
            val views = listOf(view1, view2, view3)
            model.actionFab(views,it,this)
        }
    }
}

