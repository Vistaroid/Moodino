package com.iranmobiledev.moodino.utlis

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.utils.ColorTemplate
import io.github.persiancalendar.calendar.CivilDate
import java.util.*

fun Fragment.isFragmentInBackStack(destinationId : Int) = try {
    findNavController().getBackStackEntry(destinationId)
    true
} catch (e: Exception) {
    false
}

fun NavController.isFragmentInBackStack(destinationId: Int) = try {
    getBackStackEntry(destinationId)
    true
} catch (e: Exception) {
    false
}



