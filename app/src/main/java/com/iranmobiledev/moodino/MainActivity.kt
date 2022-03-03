package com.iranmobiledev.moodino

import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iranmobiledev.moodino.databinding.ActivityMainBinding
import com.iranmobiledev.moodino.viewmodel.MainActivityViewModel
import kotlinx.coroutines.NonCancellable.start

val TAG = "activitymaintag"
private var extended = false

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val navView: BottomNavigationView = activityMainBinding.bottomNavigationView
        val fab: FloatingActionButton = activityMainBinding.fab

        val model: MainActivityViewModel by viewModels()

        //fab 3 buttons to be animate
        val view1 = activityMainBinding.icon1
        val view2 = activityMainBinding.icon2
        val view3 = activityMainBinding.icon3

        //fix nav background problem
        navView.background = null

        fab.setOnClickListener {
            val views = listOf<Button>(view1, view2, view3)
            model.animateFabViews(views)
        }
    }
}

