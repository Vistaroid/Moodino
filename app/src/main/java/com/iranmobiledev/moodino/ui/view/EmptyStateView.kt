package com.iranmobiledev.moodino.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.iranmobiledev.moodino.R
import com.iranmobiledev.moodino.databinding.EmptyStateViewBinding
import com.iranmobiledev.moodino.ui.calendar.calendarpager.layoutInflater


class EmptyStateView(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {
    private var view: ViewGroup? = null
    private var binding: EmptyStateViewBinding
    var emojis : EmojiView

    init {
        view = inflate(context, R.layout.empty_state_view, this) as ViewGroup
        binding = EmptyStateViewBinding.inflate(context.layoutInflater)

        emojis = EmojiView(context, null)

        val animationView = binding.emptyStateAnimation
        animationView.setAnimation(R.raw.empty_state_animation)

        val icon = binding.emptyStateIcon
        icon.setImageResource(R.drawable.ic_hand_down)
        icon.rotation = 180f

        attributeSet?.let {
            val attr = context.obtainStyledAttributes(it, R.styleable.EmptyStateView)
            val activeEmoji = attr.getBoolean(R.styleable.EmptyStateView_activeEmoji, false)
            if(activeEmoji)
                view?.findViewById<EmojiView>(R.id.emojisView)?.visibility = View.VISIBLE

            val animation = attr.getResourceId(
                R.styleable.EmptyStateView_animation,
                0
            )
            val contentText = attr.getString(R.styleable.EmptyStateView_contentText)
            val icon = attr.getResourceId(
                R.styleable.EmptyStateView_icon,
                0
            )

            if (animation != 0)
                binding.emptyStateAnimation.setAnimation(animation)
            else
                binding.emptyStateAnimation.setAnimation(R.raw.empty_state_animation)

            if (!contentText.isNullOrEmpty())
                view?.findViewById<TextView>(R.id.emptyStateText)?.text = contentText

            if (icon != 0){
                binding.emptyStateIcon.setImageResource(icon)
            }
        }
    }
}