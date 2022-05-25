package com.iranmobiledev.moodino.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.nsd.NsdManager
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate

import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iranmobiledev.moodino.NavGraphDirections
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseActivity
import com.iranmobiledev.moodino.data.EntryDate
import com.iranmobiledev.moodino.data.EntryTime
import com.iranmobiledev.moodino.databinding.ActivityMainBinding
import com.iranmobiledev.moodino.listener.DatePickerDialogEventListener
import com.iranmobiledev.moodino.ui.calendar.calendarpager.initGlobal
import com.iranmobiledev.moodino.ui.entry.AddEntryFragmentDirections
import com.iranmobiledev.moodino.ui.entry.EntriesFragmentDirections
import com.iranmobiledev.moodino.ui.more.MoreViewModel
import com.iranmobiledev.moodino.utlis.*
import com.iranmobiledev.moodino.utlis.dialog.getPersianDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject
import saman.zamani.persiandate.PersianDate
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(), DatePickerDialogEventListener {

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
        initRtl()
        setupUi()
        onFabClickListener()
        onFabItemsClickListener()

        animationDuration = resources.getInteger(
            android.R.integer.config_shortAnimTime
        ).toLong()
    }

    private fun initRtl() {
        val viewModel : MoreViewModel by inject()
        val currentLanguage = if (viewModel.getLanguage() == 1) "persian" else "english"

        if (currentLanguage == "persian"){
            binding.apply {
                yesterdayButton.rotation = -180f
            }
        }
    }

    private fun onFabItemsClickListener() {
        binding.todayButton.setOnClickListener {
            viewModel.actionMenu(fabItems, binding.fabMenu, binding.dimLayout, animationDuration)
            val action = getTodayAction()
            navController.navigate(action)
        }

        binding.yesterdayButton.setOnClickListener {
            viewModel.actionMenu(fabItems, binding.fabMenu, binding.dimLayout, animationDuration)
            val action = getYesterdayAction()
            navController.navigate(action)
        }
        binding.anotherDayButton.setOnClickListener {
            viewModel.actionMenu(fabItems, binding.fabMenu, binding.dimLayout, animationDuration)
            setupDatePickerDialog()
        }
    }

    private fun setupDatePickerDialog() {
        val persianDate = PersianDate()
        getPersianDialog(this, this, persianDate).show()
    }

    private fun getYesterdayAction(): NavDirections {
        val persianDate = PersianDate()
        val yesterday = persianDate.addDay(-1)
        val time = EntryTime(persianDate.hour.toString(), persianDate.minute.toString())
        val date = EntryDate(yesterday.shYear, yesterday.shMonth, yesterday.shDay)
        return  NavGraphDirections.actionGlobalMain(date, time)
    }

    private fun getTodayAction(): NavDirections {
        val persianDate = PersianDate()
        val time = EntryTime(persianDate.hour.toString(), persianDate.minute.toString())
        val date = EntryDate(persianDate.shYear, persianDate.shMonth, persianDate.shDay)
        return  NavGraphDirections.actionGlobalMain(date, time)
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
            viewModel.actionMenu(fabItems, binding.fabMenu, binding.dimLayout, animationDuration)
            viewModel.isMenuOpen.observe(this@MainActivity) { isOpen ->
                binding.dimLayout.apply {
                    setOnTouchListener { _, _ ->
                        if (isOpen) viewModel.actionMenu(
                            fabItems,
                            binding.fabMenu,
                            binding.dimLayout,
                            animationDuration
                        )
                        return@setOnTouchListener isOpen == true
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
            containerId = R.id.navGraph,
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

    override fun attachBaseContext(newBase: Context?) {
        val shared = newBase?.getSharedPreferences("sharedPref", MODE_PRIVATE)
        val lan = when (shared?.getInt(LANGUAGE, ENGLISH)) {
            ENGLISH -> "en"
            PERSIAN -> "fa"
            else -> "en"
        }
        super.attachBaseContext(MyContextWrapper.wrap(newBase, Locale(lan).toString()))
    }


    object SetThem {
        fun themeApp(theme: Int) {
            when(theme){
                SYSTEM_DEFAULT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    override fun onDateSelected(persianPickerDate: PersianPickerDate) {
        val persianDate = PersianDate()
        val action = NavGraphDirections.actionGlobalMain(
            EntryDate(
                persianPickerDate.persianYear,
                persianPickerDate.persianMonth,
                persianPickerDate.persianDay
            ),
            EntryTime(persianDate.hour.toString(), persianDate.minute.toString())
        )

        navController.navigate(action)
    }


}

