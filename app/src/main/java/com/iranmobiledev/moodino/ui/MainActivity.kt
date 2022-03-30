package com.iranmobiledev.moodino.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseActivity
import com.iranmobiledev.moodino.data.BottomNavState
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.databinding.ActivityMainBinding
import com.iranmobiledev.moodino.utlis.MoodinoDateImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import saman.zamani.persiandate.PersianDate

class MainActivity : BaseActivity() {

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

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
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

        val scope = CoroutineScope(Dispatchers.IO)

        val fabMenu = activityMainBinding.fabMenu
        //fix nav background problem
        navView.background = null

        fab.setOnClickListener { it ->
            model.actionFab(fabMenu, it, this, true)
        }

        activityMainBinding.todayButton.setOnClickListener {

            scope.launch (Dispatchers.Main){
                model.actionFab(fabMenu, it, MainActivity())
            }

            bottomNavVisibility(false)
            val bundle = Bundle()
            val persianDate = PersianDate()
            bundle.putParcelable("entryDate", EntryDate(
                persianDate.second,
                persianDate.minute,
                persianDate.hour,
                persianDate.grgDay,
                persianDate.grgMonth,
                persianDate.grgYear
            ))
            navController.navigate(R.id.addEntryFragment, bundle)
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
        bottomNavVisibility(bottomNavState.mustShow)
    }
}

