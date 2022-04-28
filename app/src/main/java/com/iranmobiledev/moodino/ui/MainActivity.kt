package com.iranmobiledev.moodino.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseActivity
import com.iranmobiledev.moodino.data.BottomNavState
import com.iranmobiledev.moodino.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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
    private val model: MainActivityViewModel by viewModels()


    private var currentNavController: LiveData<NavController>? = null

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
        initGlobal(this)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
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
            //TODO send date from bundle
            navController.navigate(R.id.addEntryFragment, bundle)
        }

        activityMainBinding.otherDayButton.setOnClickListener{
        }
    }


    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        if (savedInstanceState != null) {
            super.onRestoreInstanceState(savedInstanceState)
        }
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navGraphIds = listOf(R.navigation.nav_graph)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_graph,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
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

