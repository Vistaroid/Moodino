package com.iranmobiledev.moodino.ui.more.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseActivity
import com.iranmobiledev.moodino.databinding.ActivityActivitiesBinding

class ActivitiesActivity : BaseActivity() {

    lateinit var binding : ActivityActivitiesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activitiesToolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

    }
}