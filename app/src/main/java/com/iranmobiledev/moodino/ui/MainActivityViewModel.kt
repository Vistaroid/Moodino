package com.iranmobiledev.moodino.ui

import android.util.Log
import android.view.View
import android.view.animation.*
import android.widget.LinearLayout
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.lifecycle.MutableLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.data.RecyclerViewData


class MainActivityViewModel() : BaseViewModel() {

    val TAG = "mainActivityViewModel"
    var isMenuOpen = MutableLiveData(false)

    fun actionMenu(
        menuItems: ArrayList<LinearLayout>,
        fab: FloatingActionButton,
        dimLayout: LinearLayout,
        animationDuration: Long
    ) {
        if (isMenuOpen.value == false) {
            openMenu(menuItems)
            actionFab(fab)
            crossFade(dimLayout, animationDuration)
        } else {
            closeMenu(menuItems)
            actionFab(fab)
            crossFade(dimLayout, animationDuration)
        }
    }

    private fun closeMenu(views: ArrayList<LinearLayout>) {
        isMenuOpen.postValue(false)
        views.forEach {
            it.animate()
                .translationX(0f)
                .translationY(0f)
                .setInterpolator(LinearInterpolator())
                .withEndAction {
                    it.visibility = View.GONE
                }
                .start()

        }
    }

    private fun openMenu(views: ArrayList<LinearLayout>) {
        isMenuOpen.postValue(true)
        views.forEach {

            it.visibility = View.VISIBLE

            //vertical animation
            val springAnim = SpringAnimation(it, SpringAnimation.TRANSLATION_Y)
            val springForce = SpringForce()

            springForce.finalPosition = when (it) {
                views[0], views[2] -> -160f
                views[1] -> -265f
                else -> {
                    160f
                }
            }
            springAnim.spring = springForce
            springForce.stiffness = SpringForce.STIFFNESS_LOW
            springForce.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            springAnim.start()

            //horizontal animation
            when (it) {
                views[0] -> {
                    it.translationX = 0f
                    it.animate()
                        .translationX(-210f)
                        .setInterpolator(LinearInterpolator())
                        .start()
                }

                views[2] -> {
                    it.translationX = 0f
                    it.animate()
                        .translationX(210f)
                        .setInterpolator(LinearInterpolator())
                        .start()
                }
            }
        }
    }

    private fun actionFab(fab: FloatingActionButton) {
        val springAnim = SpringAnimation(fab, SpringAnimation.ROTATION)
        val springForce = SpringForce()
        if (isMenuOpen.value == false) {
            springForce.finalPosition = 180f
            springAnim.spring = springForce
            springForce.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            springForce.stiffness = SpringForce.STIFFNESS_VERY_LOW
            springAnim.start()
        } else {
            springForce.finalPosition = 0f
            springAnim.spring = springForce
            springForce.stiffness = SpringForce.STIFFNESS_VERY_LOW
            springForce.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            springAnim.start()
        }
    }

    private fun crossFade(dimLayout: LinearLayout, animationDuration: Long) {
        Log.d(TAG, "crossFade: value ${isMenuOpen.value}")
        when (isMenuOpen.value) {
            false -> {
                dimLayout.apply {
                    alpha = 0f
                    visibility = View.VISIBLE
                    animate()
                        .alpha(1f)
                        .duration = animationDuration
                }
            }

            true -> {
                dimLayout.apply {
                    alpha = 1f
                    visibility = View.VISIBLE
                    animate()
                        .alpha(0f)
                        .duration = animationDuration
                }
            }
        }
    }


}