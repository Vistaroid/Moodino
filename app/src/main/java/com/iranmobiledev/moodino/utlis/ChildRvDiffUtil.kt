package com.iranmobiledev.moodino.utlis

import androidx.recyclerview.widget.DiffUtil
import com.iranmobiledev.moodino.data.Entry
import com.iranmobiledev.moodino.data.RecyclerViewData

class ChildRvDiffUtil(
    private val oldList: List<Entry>,
    private val newList: List<Entry>
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
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}