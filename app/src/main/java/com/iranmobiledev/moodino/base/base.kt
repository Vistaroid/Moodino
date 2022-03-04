package com.iranmobiledev.moodino.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

/**
 * All fragments should extend this base fragment
 */
open class BaseFragment : Fragment(), BaseView{

}
/**
 * All View Models should extend this view model
 */
open class BaseViewModel : ViewModel(){

}

interface BaseView{

}
