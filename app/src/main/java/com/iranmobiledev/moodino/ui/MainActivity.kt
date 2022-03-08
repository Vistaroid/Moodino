package com.iranmobiledev.moodino.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //setup navigation controller
        val navView: BottomNavigationView = activityMainBinding.bottomNavigationView
        val navController = findNavController(R.id.fragmentContainerView)
        navView.setupWithNavController(navController)

        val model: MainActivityViewModel by viewModels()

        //fab 3 buttons to be animate
        val fab: FloatingActionButton = activityMainBinding.fab
        val fabMenuView = activityMainBinding.fabMenu

        //fix nav background problem
        navView.background = null

        fab.setOnClickListener { it ->
            model.actionFab(fabMenuView,it,this)
        }

    }
}

