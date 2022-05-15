package com.iranmobiledev.moodino.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.EmojiViewBinding
import com.iranmobiledev.moodino.listener.EmojiClickListener

class EmojiView(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    private var emojiClickListener: EmojiClickListener? = null

    var radItem : ViewGroup
    var goodItem : ViewGroup
    var mehItem : ViewGroup
    var badItem : ViewGroup
    var awfulItem : ViewGroup

    init {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = EmojiViewBinding.inflate(inflater, this, true)
        val attr = context.obtainStyledAttributes(attributeSet, R.styleable.EmojiView)
        val text = attr.getString(R.styleable.EmojiView_text)

        radItem = view.radItem
        goodItem = view.goodItem
        mehItem = view.mehItem
        badItem = view.badItem
        awfulItem = view.awfulItem

        if(!text.isNullOrEmpty()){
            view.emojiesTitle.visibility = View.VISIBLE
            view.emojiesTitle.text = text
        }
        view.clickListener = OnClickListener {
            emojiClickListener?.onEmojiItemClicked(it.id)
        }
    }

    fun setEmptyStateOnClickListener(listener: EmojiClickListener) {
        emojiClickListener = listener
    }
}