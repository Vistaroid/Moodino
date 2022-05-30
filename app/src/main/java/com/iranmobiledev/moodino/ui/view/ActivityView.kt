package com.iranmobiledev.moodino.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.ItemActivityBinding

class ActivityView(context: Context, attributeSet: AttributeSet?) : LinearLayout(context, attributeSet) , View.OnClickListener{
    val binding : ItemActivityBinding
    var shouldSelect : Boolean = true
    var mSelected : Boolean = false
    private var text = ""

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemActivityBinding.inflate(inflater, this, false)

        attributeSet?.let {
            val attr = context.obtainStyledAttributes(it, R.styleable.ActivityView)
            shouldSelect = attr.getBoolean(R.styleable.ActivityView_selectedItem, true)
            text = attr.getString(R.styleable.ActivityView_activity_text).toString()
            setContentText(text)
            if(shouldSelect)
                setSelected()
            else
                setUnselected()
        }
        binding.activityViewGroup.setOnClickListener(this)
    }


    fun setContentText(text: String){
        binding.activityTitle.text = text
    }

    fun setSelected(){
        binding.activityBackground.setBackgroundResource(R.drawable.activity_bg)
        binding.activityIcon.isSelectedEmoji = true
        shouldSelect = false
        mSelected = true
    }

    fun setUnselected(){
        binding.activityBackground.setBackgroundResource(R.drawable.unselect_activity_bg)
        binding.activityIcon.isSelectedEmoji = false
        shouldSelect = true
        mSelected = false
    }

    fun clickedOn(){
        onClick(this)
    }

    override fun onClick(v: View?) {
        if(shouldSelect){
            setSelected()
        }
        else{
            setUnselected()
        }
    }

    fun getRoot() : View{
        return binding.root
    }
}