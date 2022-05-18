package com.iranmobiledev.moodino.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId : Long?,
    val title: String
)