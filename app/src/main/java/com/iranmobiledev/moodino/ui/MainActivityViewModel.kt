package com.iranmobiledev.moodino.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseViewModel

class MainActivityViewModel() : BaseViewModel() {

    private var extended = false

    fun actionFab(views: List<LinearLayout>, view: View, context: MainActivity) {
        rotateFab(view, context)
        animateFabViews(views)

        if (extended) {
            views.forEach {
                it.getChildAt(1).visibility = View.VISIBLE
            }
        } else {
            views.forEach {
                it.getChildAt(1).visibility = View.GONE
            }
        }
    }

    private fun animateFabViews(views: List<LinearLayout>) {
        extended = !extended
        views.forEach {
            when (extended) {
                true -> {
                    if (it.id == R.id.yesterday) extendFab(it, extended, -200f, -210f)
                    if (it.id == R.id.today) extendFab(it, extended, 0f, -310f)
                    if (it.id == R.id.other_days) extendFab(it, extended, 200f, -210f)
                }
                false -> {
                    if (it.id == R.id.yesterday) closeFab(it, extended, 0f, 0f)
                    if (it.id == R.id.today) closeFab(it, extended, 0f, 0f)
                    if (it.id == R.id.other_days) closeFab(it, extended, 0f, 0f)
                }
            }
        }
    }

    private fun extendFab(view: LinearLayout, extended: Boolean, x: Float, y: Float) {
        ObjectAnimator.ofFloat(view, "translationX", x).apply {
            duration = 220

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
            start()
        }

        ObjectAnimator.ofFloat(view, "translationY", y).apply {
            duration = 220
            start()
        }
    }

    private fun rotateFab(view: View?, context: Context) {
        if (!extended) {
            val rotateFab = AnimationUtils.loadAnimation(
                context,
                R.anim.rotate_fab_animation
            )
            rotateFab.fillAfter = true
            view?.startAnimation(rotateFab)
        } else {
            val unRotateFab = AnimationUtils.loadAnimation(
                context,
                R.anim.unrotate_fab_animation
            )
            unRotateFab.fillAfter = true
            view?.startAnimation(unRotateFab)
        }

    }
}