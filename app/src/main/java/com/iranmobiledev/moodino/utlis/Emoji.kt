package com.iranmobiledev.moodino.utlis

import android.content.Context
import android.graphics.Color
import com.iranmobiledev.moodino.R

enum class EmojiType{
    SIMPLE,
    CHILD
}

interface EmojiInterface{
    fun getEmoji(value: Int): Emoji
}

data class Emoji(
    val value: Int,
    val title: String,
    val image: Int,  // drawable id
    val color: Int // color id
)

private class SimpleEmoji(private val context: Context): EmojiInterface{
    override fun getEmoji(value: Int): Emoji {
        return when (value) {
            EmojiValue.AWFUL -> Emoji(value, context.getString(R.string.awful), R.drawable.emoji_awful, context.resources.getColor(R.color.awful_color,context.theme)
            )
            EmojiValue.BAD -> Emoji(value, context.getString(R.string.bad), R.drawable.emoji_bad, context.resources.getColor(R.color.bad_color,context.theme)
            )
            EmojiValue.MEH -> Emoji(value, context.getString(R.string.meh), R.drawable.emoji_meh, context.resources.getColor(R.color.meh_color,context.theme)
            )
            EmojiValue.GOOD -> Emoji(value, context.getString(R.string.good), R.drawable.emoji_good, context.resources.getColor(R.color.good_color,context.theme)
            )
            EmojiValue.RAD -> Emoji(value, context.getString(R.string.rad), R.drawable.emoji_rad, context.resources.getColor(R.color.rad_color,context.theme)
            )
            else -> Emoji(value, context.getString(R.string.meh), R.drawable.emoji_meh, context.resources.getColor(R.color.meh_color,context.theme)
            )
        }
    }
}

private class ChildEmoji(private val context: Context): EmojiInterface{
    override fun getEmoji(value: Int): Emoji {
        TODO("Not yet implemented")
    }
}

object EmojiFactory{
    fun create(context: Context): EmojiInterface{
        return when (SharedPref.getStringPref(context, EMOJI_TYPE, EmojiType.SIMPLE.toString())) {
            EmojiType.SIMPLE.toString() -> SimpleEmoji(context)
            EmojiType.CHILD.toString() -> ChildEmoji(context)
            else -> SimpleEmoji(context)
        }
    }
}

fun getEmoji(context: Context,emojiValue: Int): Emoji{
    return EmojiFactory.create(context).getEmoji(emojiValue)
}

object EmojiValue{
    const val AWFUL = 1
    const val BAD = 2
    const val MEH = 3
    const val GOOD = 4
    const val RAD  = 5
}