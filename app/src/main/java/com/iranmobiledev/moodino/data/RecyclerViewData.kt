package com.iranmobiledev.moodino.data

import com.iranmobiledev.moodino.ui.entry.adapter.ChildRecyclerView


data class RecyclerViewData(
    var entries : List<Entry>,
    var adapter : ChildRecyclerView? = null,
)