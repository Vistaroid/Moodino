package com.iranmobiledev.moodino.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.EmojiViewGroupBinding
import com.iranmobiledev.moodino.listener.EmojiClickListener
import com.iranmobiledev.moodino.utlis.EmojiValue


class EmojiViewGroup(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    private var emojiClickListener: EmojiClickListener? = null
    private var selectedView: CustomEmojiView? = null //  View is Selected
    private var binding: EmojiViewGroupBinding
    init {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = EmojiViewGroupBinding.inflate(inflater, this, true)
        val attr = context.obtainStyledAttributes(attributeSet, R.styleable.EmojiViewGroup)
        val text = attr.getString(R.styleable.EmojiViewGroup_text)

        if (!text.isNullOrEmpty()) {
            binding.emojiesTitle.visibility = View.VISIBLE
            binding.emojiesTitle.text = text
        }
        binding.clickListener = OnClickListener {
            selectEmojiView(it as CustomEmojiView)
            emojiClickListener?.onEmojiItemClicked(getEmojiValue(it.id))
        }
        attr.recycle()
    }

    private fun selectEmojiView(view: CustomEmojiView){
        if (selectedView != null) {
            selectedView!!.isSelectedEmoji = !selectedView!!.isSelectedEmoji
            selectedView = view
            selectedView!!.isSelectedEmoji = !selectedView!!.isSelectedEmoji
        } else {
            selectedView = view
            selectedView!!.isSelectedEmoji = !selectedView!!.isSelectedEmoji
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

    fun setSelectedEmojiView(emojiValue: Int){
        when (emojiValue) {
            EmojiValue.RAD -> selectEmojiView(binding.radImage)
            EmojiValue.GOOD -> selectEmojiView(binding.goodImage)
            EmojiValue.MEH -> selectEmojiView(binding.mehImage)
            EmojiValue.BAD -> selectEmojiView(binding.badImage)
            EmojiValue.AWFUL -> selectEmojiView(binding.awfulImage)
        }
    }

    fun setEmojiClickListener(listener: EmojiClickListener) {
        emojiClickListener = listener
    }
}