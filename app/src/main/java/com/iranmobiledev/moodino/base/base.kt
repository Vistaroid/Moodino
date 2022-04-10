package com.iranmobiledev.moodino.base

import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.iranmobiledev.moodino.listener.DialogEventListener
import com.iranmobiledev.moodino.utlis.MoodinoDialog

open class BaseActivity : AppCompatActivity() {

}

/**
 * All fragments should extend this base fragment
 */
open class BaseFragment : Fragment(), BaseView {
}

/**
 * All View Models should extend this view model
 */
open class BaseViewModel : ViewModel() {

}

interface BaseView {
    /**
     * @return instance of MoodinoDialog
     * @author MohammadJavad Khoshneshin
     * @param mainText top text of dialog
     * @param subText text under the main text
     * @param icon dialog icon
     * you can set nothing to each them and their view will being gone
     * */
    fun makeDialog(
        mainText: String = "",
        subText: String = "",
        @DrawableRes icon: Int = 0,
        deleteText: String = "",
        cancelText: String = "",
    ): MoodinoDialog {
        return MoodinoDialog(mainText, subText, icon, deleteText, cancelText)
    }
}
