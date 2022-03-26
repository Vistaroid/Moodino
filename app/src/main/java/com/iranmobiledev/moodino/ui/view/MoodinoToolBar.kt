package com.iranmobiledev.moodino.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.iranmobiledev.moodino.R

class MoodinoToolBar(context: Context, attributeSet: AttributeSet?) : FrameLayout(context , attributeSet) {

    var view: View? = null

    var onBackButtonClickListener : View.OnClickListener ?= null
        set(value) {
            field = value
            view?.findViewById<LinearLayout>(R.id.btn_toolbar_back)?.setOnClickListener(onBackButtonClickListener)
        }


    init {
        view = inflate(context, R.layout.view_toolbar, this)

        if (attributeSet != null) {

            val attr = context.obtainStyledAttributes(attributeSet, R.styleable.ToolBar)
            val title = attr.getString(R.styleable.ToolBar_ToolBar_title)

            if (title!!.isNotEmpty())
                view?.findViewById<TextView>(R.id.tv_toolbar_title)?.text = title

            attr.recycle()
        }
    }
}