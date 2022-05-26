package com.iranmobiledev.moodino.data

import com.iranmobiledev.moodino.ui.entry.adapter.ChildRecyclerView
import com.iranmobiledev.moodino.ui.entry.adapter.ENTRY_DEFAULT
import java.util.*


data class RecyclerViewData(
    val id: String = UUID.randomUUID().toString(),
    var entries : List<Entry>,
    var viewType : Int = ENTRY_DEFAULT,
    var adapter : ChildRecyclerView? = null,
    val date: EntryDate
)