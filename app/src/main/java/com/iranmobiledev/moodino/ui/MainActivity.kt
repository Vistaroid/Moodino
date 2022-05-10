package com.iranmobiledev.moodino.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseActivity
import com.iranmobiledev.moodino.databinding.ActivityMainBinding
import com.iranmobiledev.moodino.ui.calendar.calendarpager.initGlobal
import com.iranmobiledev.moodino.utlis.setupWithNavController
import org.greenrobot.eventbus.EventBus
import saman.zamani.persiandate.PersianDate


class MainActivity : BaseActivity() {

    private val TAG = "mainActivity"

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var fabItems: ArrayList<LinearLayout>
    private val viewModel: MainActivityViewModel by viewModels()
    private var animationDuration: Long = 0
    private var currentNavController: LiveData<NavController>? = null

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGlobal(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        fabItems = arrayListOf(
            binding.yesterdayLinearlayout,
            binding.todayLinearlayout,
            binding.anotherDayLinearlayout
        )
        setContentView(binding.root)
        setupUi()
        onFabClickListener()
        onFabItemsClickListener()

        animationDuration = resources.getInteger(
            android.R.integer.config_shortAnimTime).toLong()
    }

    private fun onFabItemsClickListener() {

        binding.todayButton.setOnClickListener {
            val bundle = Bundle()
            fabItems.forEach {
                it.visibility = View.GONE
            }
            navController.navigate(R.id.addEntryFragment, bundle)
        }

        binding.yesterdayButton.setOnClickListener {}
        binding.anotherDayButton.setOnClickListener {}
    }

    private fun setupUi() {
        navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.background = null
        setFragmentDestinationChangeListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onFabClickListener() {

        fabItems.forEach {
            it.visibility = View.GONE
        }
        binding.fabMenu.setOnClickListener {
            viewModel.actionMenu(fabItems)
            viewModel.actionFab(binding.fabMenu)
            viewModel.crossFade(binding.dimLayout,animationDuration)

            viewModel.isMenuOpen.observe(this){
                binding.dimLayout.apply {
                    setOnTouchListener { view, motionEvent ->
                        return@setOnTouchListener it == true
                    }
                }
            }
        }
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        if (savedInstanceState != null) {
            super.onRestoreInstanceState(savedInstanceState)
        }
        setupBottomNavigationBar()
    }


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

    private fun setFragmentDestinationChangeListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.entriesFragment -> showBottomNav()
                R.id.statsFragment -> showBottomNav()
                R.id.calenderFragment -> showBottomNav()
                R.id.moreFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.fabMenu.show()
        binding.fabMenu.isClickable = true
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.bottomAppBar.performShow()
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.fabMenu.hide()
        binding.fabMenu.isClickable = false
        binding.bottomAppBar.visibility = View.GONE
        binding.bottomAppBar.performHide(true)
        binding.bottomNavigationView.visibility = View.GONE
    }
}

