package com.iranmobiledev.moodino.viewmodel

import android.animation.ObjectAnimator
import android.util.Log
import android.widget.Button
import androidx.lifecycle.LiveData
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.TAG
import com.iranmobiledev.moodino.base.BaseViewModel

class MainActivityViewModel : BaseViewModel() {

    var extended = false

    fun animateFabViews(views: List<Button>) {
        views.forEach {

            //animate to top
            if (it.id == R.id.icon1) {
                when (extended) {
                    false -> {
                        extended = true
                        extendFab(it, extended, 0f, -310f)
                    }
                    true -> {
                        extended = false
                        closeFab(it, extended, 0f, 80f)
                    }
                }
            }
            //animate to top right
            else if (it.id == R.id.icon2) {
                when (extended) {
                    false -> {
                        closeFab(it, extended, 0f, 80f)
                    }
                    true -> {
                        extendFab(it, extended, 200f, -210f)
                    }
                }
            }
            //animate to top left
            else if (it.id == R.id.icon3) {
                when (extended) {
                    false -> {
                        closeFab(it, extended, 0f, 80f)
                    }
                    true -> {
                        extendFab(it, extended, -200f, -210f)
                    }
                }
            }
        }
    }


    fun extendFab(view: Button, extended: Boolean, x: Float, y: Float) {
        ObjectAnimator.ofFloat(view, "translationX", x).apply {
            duration = 220
            Log.d(TAG, "extendFab extend : $extended")
            start()
        }

        ObjectAnimator.ofFloat(view, "translationY", y).apply {
            duration = 220
            start()
        }
    }

    fun closeFab(view: Button, extended: Boolean, x: Float, y: Float) {
        ObjectAnimator.ofFloat(view, "translationX", x).apply {
            duration = 220
            Log.d(TAG, "extendFab close : $extended")
            start()
        }

        ObjectAnimator.ofFloat(view, "translationY", y).apply {
            duration = 220
            start()
        }
    }

}