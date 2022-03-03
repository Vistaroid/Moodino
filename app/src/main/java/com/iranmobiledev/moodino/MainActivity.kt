package com.iranmobiledev.moodino

import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.children
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

        fab.setOnClickListener { it ->
            val views = listOf<LinearLayout>(view1, view2, view3)
            model.rotateFab(it, this)
            model.animateFabViews(views)

            if (model.extended) {
                views.forEach {
                    it.getChildAt(1).setVisibility(View.VISIBLE)
                }
            } else {
                views.forEach {
                    it.getChildAt(1).setVisibility(View.GONE)
                }
            }

            activityMainBinding.root.children.forEach { child ->
                if (child.elevation != 10f) {
                    Log.d(TAG, "onCreate: no")
                }
            }
        }
    }
}

