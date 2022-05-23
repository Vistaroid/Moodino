package com.iranmobiledev.moodino.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.EmojiViewBinding
import com.iranmobiledev.moodino.listener.EmojiClickListener
import com.iranmobiledev.moodino.utlis.Emoji

class EmojiViewGroup(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    private var emojiClickListener: EmojiClickListener? = null
    var selectedView: CustomEmojiView? = null //  View is Selected

    init {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = EmojiViewBinding.inflate(inflater, this, true)
        val attr = context.obtainStyledAttributes(attributeSet, R.styleable.EmojiView)
        val text = attr.getString(R.styleable.EmojiView_text)

        if (!text.isNullOrEmpty()) {
            view.emojiesTitle.visibility = View.VISIBLE
            view.emojiesTitle.text = text
        }
        view.clickListener = OnClickListener {
            if (selectedView != null) {
                selectedView!!.isSelectedEmoji = !selectedView!!.isSelectedEmoji
                selectedView = it as CustomEmojiView
                selectedView!!.isSelectedEmoji = !selectedView!!.isSelectedEmoji
            } else {
                selectedView = it as CustomEmojiView
                selectedView!!.isSelectedEmoji = !selectedView!!.isSelectedEmoji
            }
            emojiClickListener?.onEmojiItemClicked(getEmojiValue(it.id))
        }
    }


    private fun getEmojiValue(emojiId: Int): Int {
        when (emojiId) {
            R.id.radImage -> return 5
            R.id.goodImage -> return 4
            R.id.mehImage -> return 3
            R.id.badImage -> return 2
            R.id.awfulImage -> return 1
        }
        return 3 // default
    }

    fun setEmojiClickListener(listener: EmojiClickListener) {
        emojiClickListener = listener
    }
}