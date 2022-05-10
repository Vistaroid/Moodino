package com.iranmobiledev.moodino.utlis

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.annotation.LayoutRes

class HalinoPopupMenu(private val anchor : View, @LayoutRes contentView : Int) {
    private val inflater = anchor.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val contentView = inflater.inflate(contentView, null)
    private val popupWindow = PopupWindow(
        this.contentView,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    fun getContentView() : View{
        return contentView
    }
    fun getPopupWindow() : PopupWindow{
        return popupWindow
    }
    fun show(){
        popupWindow.showAsDropDown(anchor)
    }
}