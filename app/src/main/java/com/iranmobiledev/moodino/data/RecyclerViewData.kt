package com.iranmobiledev.moodino.data

import com.iranmobiledev.moodino.ui.entry.adapter.ChildRecyclerView
import java.util.*


data class RecyclerViewData(
    val id: String = UUID.randomUUID().toString(),
    var entries : List<Entry>,
    var adapter : ChildRecyclerView? = null,
    val date: EntryDate
)