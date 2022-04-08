package com.iranmobiledev.moodino.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.view.marginBottom
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseActivity
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import saman.zamani.persiandate.PersianDate
import com.iranmobiledev.moodino.ui.calendar.calendarpager.initGlobal

class MainActivity : BaseActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var todayIv: Button
    private lateinit var yesterdayIv: Button
    private lateinit var otherDayIv: Button
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var fragmentContainer: View

    private val model: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGlobal(this)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initViews()

        //setup navigation controller
        val navView: BottomNavigationView = activityMainBinding.bottomNavigationView
        navController = findNavController(R.id.fragmentContainer)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (
                destination.id != R.id.entriesFragment &&
                destination.id != R.id.statsFragment &&
                destination.id != R.id.calenderFragment &&
                destination.id != R.id.moreFragment
            ){
                bottomAppBar.performHide(false)
                fab.hide()
            }else {
                bottomAppBar.performShow(false)
                fab.show()
            }
        }

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
        fragmentContainer = findViewById(R.id.fragmentContainer)
    }
}

