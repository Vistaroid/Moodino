package com.iranmobiledev.moodino.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.EmojiViewBinding
import com.iranmobiledev.moodino.listener.EmptyStateOnClickListener

class EmojiView(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    private var emptyStateOnClickListener: EmptyStateOnClickListener? = null

    var radItem: ViewGroup
    var goodItem: ViewGroup
    var mehItem: ViewGroup
    var badItem: ViewGroup
    var awfulItem: ViewGroup

    var radEmoji: ImageView
    var goodEmoji: ImageView
    var mehEmoji: ImageView
    var badEmoji: ImageView
    var awfulEmoji: ImageView
    private var selectedItem: View? = null
    private val view: EmojiViewBinding

    init {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = EmojiViewBinding.inflate(inflater, this, true)
        val attr = context.obtainStyledAttributes(attributeSet, R.styleable.EmojiView)
        val text = attr.getString(R.styleable.EmojiView_text)
        val selectable = attr.getBoolean(R.styleable.EmojiView_isSelectable, false)

        radItem = view.radItem
        goodItem = view.goodItem
        mehItem = view.mehItem
        badItem = view.badItem
        awfulItem = view.awfulItem

        radEmoji = view.radImage
        goodEmoji = view.goodImage
        mehEmoji = view.mehImage
        badEmoji = view.badImage
        awfulEmoji = view.awfulImage

        if (!text.isNullOrEmpty()) {
            view.emojiesTitle.visibility = View.VISIBLE
            view.emojiesTitle.text = text
        }
        view.clickListener = OnClickListener {
            if (selectable) {
                selectItem(it)
                selectedItem?.let { item -> unSelectItem(item) }
                selectedItem = it
            }
            emptyStateOnClickListener?.onEmptyStateItemClicked(it.id)
        }
    }

    fun setEmptyStateOnClickListener(listener: EmptyStateOnClickListener) {
        emptyStateOnClickListener = listener
    }

    private fun selectItem(item: View) {
        when (item.id) {
            R.id.radItem -> {
                radEmoji.setColorFilter(resources.getColor(R.color.white, context?.theme))
                radEmoji.setBackgroundResource(R.drawable.rad_emoji_back)
            }
            R.id.goodItem -> {
                goodEmoji.setColorFilter(resources.getColor(R.color.white, context?.theme))
                goodEmoji.setBackgroundResource(R.drawable.good_emoji_back)
            }
            R.id.mehItem -> {
                mehEmoji.setColorFilter(resources.getColor(R.color.white, context?.theme))
                mehEmoji.setBackgroundResource(R.drawable.meh_emoji_back)
            }
            R.id.badItem -> {
                badEmoji.setColorFilter(resources.getColor(R.color.white, context?.theme))
                badEmoji.setBackgroundColor(R.drawable.bad_emoji_back)
            }
            R.id.awfulItem -> {
                awfulEmoji.setColorFilter(resources.getColor(R.color.white, context?.theme))
                awfulEmoji.setBackgroundResource(R.drawable.awful_emoji_back)
            }
        }
    }

    private fun unSelectItem(item: View) {
        when (item.id) {
            R.id.radItem -> {
                radEmoji.setColorFilter(resources.getColor(R.color.rad_color, context?.theme))
                radEmoji.setBackgroundResource(R.drawable.emoji_back_white)
            }
            R.id.goodItem -> {
                goodEmoji.setColorFilter(resources.getColor(R.color.good_color, context?.theme))
                goodEmoji.setBackgroundResource(R.drawable.emoji_back_white)
            }
            R.id.mehItem -> {
                mehEmoji.setColorFilter(resources.getColor(R.color.meh_color, context?.theme))
                mehEmoji.setBackgroundResource(R.drawable.emoji_back_white)
            }
            R.id.badItem -> {
                badEmoji.setColorFilter(resources.getColor(R.color.bad_color, context?.theme))
                badEmoji.setBackgroundResource(R.drawable.emoji_back_white)
            }
            R.id.awfulItem -> {
                badEmoji.setColorFilter(resources.getColor(R.color.awful_color, context?.theme))
                badEmoji.setBackgroundResource(R.drawable.emoji_back_white)
            }
        }
    }
}