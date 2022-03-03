package com.iranmobiledev.moodino.viewmodel

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.LinearLayout
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.TAG
import com.iranmobiledev.moodino.base.BaseViewModel

class MainActivityViewModel() : BaseViewModel() {

    var extended = false

    fun animateFabViews(views: List<LinearLayout>) {
        extended = !extended
        views.forEach {
            when (extended) {
                true -> {
                    if (it.id == R.id.icon1) extendFab(it, extended, -200f, -210f)
                    if (it.id == R.id.icon2) extendFab(it, extended, 0f, -310f)
                    if (it.id == R.id.icon3) extendFab(it, extended, 200f, -210f)
                }
                false -> {
                    if (it.id == R.id.icon1) closeFab(it, extended, 0f, 0f)
                    if (it.id == R.id.icon2) closeFab(it, extended, 0f, 0f)
                    if (it.id == R.id.icon3) closeFab(it, extended, 0f, 0f)
                }
            }
        }
    }

    private fun extendFab(view: LinearLayout, extended: Boolean, x: Float, y: Float) {
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

    private fun closeFab(view: LinearLayout, extended: Boolean, x: Float, y: Float) {
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

    fun rotateFab(view: View?, context: Context) {

        if (!extended) {
            val rotateFab = AnimationUtils.loadAnimation(
                context,
                R.anim.rotate_fab_animation
            )
            rotateFab.fillAfter = true
            view?.startAnimation(rotateFab)
        } else {
            val unrotateFab = AnimationUtils.loadAnimation(
                context,
                R.anim.unrotate_fab_animation
            )
            unrotateFab.fillAfter = true
            view?.startAnimation(unrotateFab)
        }

    }
}