package com.iranmobiledev.moodino.utlis

import android.content.Context
import com.iranmobiledev.moodino.R

/**
 * this object used
 * for charts
 */

data class Emoji(
    val value: Float,
    val title: String,
    val image: Int,  // drawable id
    val color: Int // color id
)

enum class EmojiType {
    SIMPLE,
    CHILD
}

// value is between 1 to 5
interface EmojiInterface {
    fun getEmoji(value: Float): Emoji
}

private class SimpleEmoji(private val context: Context) : EmojiInterface {
    override fun getEmoji(value: Float): Emoji {
        return when (value) {
            EmojiValue.AWFUL -> Emoji(
                value,
                context.getString(R.string.awful),
                R.drawable.emoji_awful,
                R.color.awful_color
            )
            EmojiValue.BAD -> Emoji(
                value,
                context.getString(R.string.bad),
                R.drawable.emoji_bad,
                R.color.bad_color
            )
            EmojiValue.MEH -> Emoji(
                value,
                context.getString(R.string.meh),
                R.drawable.emoji_meh,
                R.color.meh_color
            )
            EmojiValue.GOOD -> Emoji(
                value,
                context.getString(R.string.good),
                R.drawable.emoji_good,
                R.color.good_color
            )
            EmojiValue.RAD -> Emoji(
                value,
                context.getString(R.string.rad),
                R.drawable.emoji_rad,
                R.color.rad_color
            )
            else -> Emoji(
                value,
                context.getString(R.string.meh),
                R.drawable.emoji_meh,
                R.color.meh_color
            )
        }
    }
}

private class ChildEmoji(private val context: Context) : EmojiInterface {
    override fun getEmoji(value: Float): Emoji {
        TODO("Not yet implemented")
    }
}

object EmojiFactory {
    fun create(context: Context): EmojiInterface {
        return when (SharedPref.create(context)
            .getString(EMOJI_TYPE, EmojiType.SIMPLE.toString())) {
            EmojiType.SIMPLE.toString() -> SimpleEmoji(context)
            EmojiType.CHILD.toString() -> ChildEmoji(context)
            else -> SimpleEmoji(context)
        }
    }
}


object EmojiValue {
    const val AWFUL = 1f
    const val BAD = 2f
    const val MEH = 3f
    const val GOOD = 4f
    const val RAD = 5f
}

//const val AWFUL = R.string.awful
//const val BAD = R.string.bad
//const val MEH = R.string.meh
//const val GOOD = R.string.good
//const val RAD = R.string.rad
