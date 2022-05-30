package com.iranmobiledev.moodino.utlis

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.iranmobiledev.moodino.data.RecyclerViewData


class MyDiffUtil(
    private val oldList: List<RecyclerViewData>,
    private val newList: List<RecyclerViewData>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].entries != newList[newItemPosition].entries -> false
            oldList[oldItemPosition].adapter != newList[newItemPosition].adapter -> false
            else -> true
        }
    }
}