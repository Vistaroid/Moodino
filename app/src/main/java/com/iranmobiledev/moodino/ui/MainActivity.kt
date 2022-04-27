package com.iranmobiledev.moodino.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainActivityViewModel by viewModels()

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
        setupClicks()
    }

    private fun setupUi() {
        navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.background = null
    }

    private fun setupClicks() {
        binding.fab.setOnClickListener { it ->
            viewModel.actionFab(binding.fabMenu, it, this, true)
        }

        binding.todayButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.actionFab(binding.fabMenu, it, MainActivity())
            }
            bottomNavVisibility(false)
            val bundle = Bundle()
            val persianDate = PersianDate()
            //TODO send date from bundle
            navController.navigate(R.id.addEntryFragment, bundle)
        }

        binding.otherDayButton.setOnClickListener{
        }
    }

    private fun bottomNavVisibility(mustShow: Boolean) {
        val bottomAppBar = binding.bottomAppBar
        val fab = binding.fab
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

