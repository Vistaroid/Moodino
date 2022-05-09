package com.iranmobiledev.moodino.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.utlis.MoodinoDialog

/**
 * All activities should extend this base activity
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {
    override val mRootView: ViewGroup?
        get() = window.findViewById(android.R.id.content) as ViewGroup
    override val viewContext: Context
        get() = this
}

/**
 * All fragments should extend this base fragment
 */
abstract class BaseFragment : Fragment(), BaseView {
    override val mRootView: ViewGroup?
        get() = view as ViewGroup
    override val viewContext: Context
        get() = requireContext()

}

/**
 * All View Models should extend this view model
 */
open class BaseViewModel : ViewModel() {

}

interface BaseView {

    val mRootView: ViewGroup?
    val viewContext: Context

    /**
     * @return instance of MoodinoDialog
     * @author MohammadJavad Khoshneshin
     * @param mainText top text of dialog
     * @param subText text under the main text
     * @param icon dialog icon
     * you can set nothing to each them and their view will being gone
     * */
    fun makeDialog(
        @StringRes mainText: Int = -1,
        @StringRes subText: Int = -1,
        @DrawableRes icon: Int = 0,
        @StringRes deleteText: Int = -1,
        @StringRes cancelText: Int = -1,
    ): MoodinoDialog {
        return MoodinoDialog(mainText, subText, icon, deleteText, cancelText)
    }
}
