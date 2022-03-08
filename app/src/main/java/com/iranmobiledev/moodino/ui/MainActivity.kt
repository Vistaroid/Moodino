package com.iranmobiledev.moodino.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.Activity
import com.iranmobiledev.moodino.databinding.ActivityMainBinding
import com.iranmobiledev.moodino.ui.entries.AddEntryFragment


class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var todayIv : Button
    private lateinit var yesterdayIv : Button
    private lateinit var otherDayIv : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initViews()

        val bottomAppBar = activityMainBinding.bottomAppBar

        //setup navigation controller
        val navView: BottomNavigationView = activityMainBinding.bottomNavigationView
        navController = findNavController(R.id.fragmentContainerView)
        navView.setupWithNavController(navController)

        val model: MainActivityViewModel by viewModels()

        //fab 3 buttons to be animate
        val fab: FloatingActionButton = activityMainBinding.fab
        val fabMenu = activityMainBinding.fabMenu


        //fix nav background problem
        navView.background = null

        fab.setOnClickListener { it ->
            model.actionFab(fabMenu ,it,this)
        }

        activityMainBinding.todayButton.setOnClickListener{
            model.actionFab(fabMenu, it, this)

            fab.hide()
            bottomAppBar.visibility = View.GONE
            bottomAppBar.performHide(true)
        }

    }

    private fun initViews(){
        todayIv = activityMainBinding.todayButton
        yesterdayIv = activityMainBinding.yesterdayButton
        otherDayIv = activityMainBinding.otherDayButton
    }
}

