package com.iranmobiledev.moodino.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.base.BaseViewModel
import com.iranmobiledev.moodino.utlis.dp


class MainActivityViewModel() : BaseViewModel() {

    var extended = false

    fun actionFab(menuFab: LinearLayout, view: View, context: MainActivity) {
        rotateFab(view, context)
//        showMenuFab(context, menuFab)

        if (extended) {
            hideMenuFab(context, menuFab)
        } else {
            showMenuFab(context,menuFab)
        }

    }

    private fun showMenuFab(context: MainActivity, menuFab: LinearLayout) {

        extendFab(menuFab, true, 40f, -200f)

        //convert dp to pixel
        val scale: Float = context.resources.displayMetrics.density
        val widthToDp = (290f * scale + 0.5f)

        //increase width animated
        val anim = ValueAnimator.ofInt(menuFab.measuredWidth, widthToDp.toInt())
        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = menuFab.layoutParams
            layoutParams.width = value
            menuFab.layoutParams = layoutParams
        }
        anim.duration = 500
        anim.start()

        extended = true
    }

    private fun hideMenuFab(context: MainActivity, menuFab: LinearLayout) {
            closeFab(menuFab, true, 0f, 0f)

            //increase width animated
            val anim = ValueAnimator.ofInt(menuFab.measuredWidth, 0)
            anim.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Int
                val layoutParams: ViewGroup.LayoutParams = menuFab.layoutParams
                layoutParams.width = value
                menuFab.layoutParams = layoutParams
            }
            anim.duration = 50
            anim.start()

        extended = false
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
}