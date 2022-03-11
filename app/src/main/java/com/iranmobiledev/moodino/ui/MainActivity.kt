package com.iranmobiledev.moodino.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.add
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.data.BottomNavState
import com.iranmobiledev.moodino.databinding.ActivityMainBinding
import com.iranmobiledev.moodino.ui.entries.AddEntryFragment
import com.iranmobiledev.moodino.utlis.DateContainer
import com.iranmobiledev.moodino.utlis.DateContainerImpl
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var todayIv: Button
    private lateinit var yesterdayIv: Button
    private lateinit var otherDayIv: Button
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar
    private val model: MainActivityViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initViews()


        //setup navigation controller
        val navView: BottomNavigationView = activityMainBinding.bottomNavigationView
        navController = findNavController(R.id.fragmentContainerView)
        navView.setupWithNavController(navController)


        //fab 3 buttons to be animate

        val fabMenu = activityMainBinding.fabMenu

        //fix nav background problem
        navView.background = null

        fab.setOnClickListener { it ->
            model.actionFab(fabMenu, it, this)
        }

        activityMainBinding.todayButton.setOnClickListener {
            model.actionFab(fabMenu, it, this)
            bottomNavVisibility(false)

            val dateContainer = DateContainerImpl()
            navController.navigate(R.id.addEntryFragment, Bundle().apply {
                putString("date", dateContainer.today())
                putString("time", dateContainer.time())
            })
        }

    }

    private fun initViews() {
        todayIv = activityMainBinding.todayButton
        yesterdayIv = activityMainBinding.yesterdayButton
        otherDayIv = activityMainBinding.otherDayButton
        fab = activityMainBinding.fab
        bottomAppBar = activityMainBinding.bottomAppBar
    }

    private fun bottomNavVisibility(mustShow: Boolean) {
        if (mustShow) {
            fab.show()
            fab.isClickable = true
            bottomAppBar.visibility = View.VISIBLE
            bottomAppBar.performShow()
        } else {
            fab.hide()
            fab.isClickable = false
            bottomAppBar.visibility = View.GONE
            bottomAppBar.performHide(true)
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun mustBottomNavShow(bottomNavState: BottomNavState) {
        model.rotateFab(fab, this)
        bottomNavVisibility(bottomNavState.mustShow)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}

