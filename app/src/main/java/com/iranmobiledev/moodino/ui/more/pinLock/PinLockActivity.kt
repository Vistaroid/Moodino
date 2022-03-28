package com.iranmobiledev.moodino.ui.more.pinLock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.iranmobiledev.moodino.base.BaseActivity
import com.iranmobiledev.moodino.databinding.ActivityPinLockBinding

class PinLockActivity : BaseActivity() {

    lateinit var binding : ActivityPinLockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinLockBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.pinLockToolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }
}