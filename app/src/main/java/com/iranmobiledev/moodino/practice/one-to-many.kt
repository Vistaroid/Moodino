package com.iranmobiledev.moodino.practice

import androidx.room.*

@Entity
data class Owner2(
    @PrimaryKey val ownerId: Long,
    val name: String
)

@Entity
data class Dog2(
    @PrimaryKey val dogId: Long,
    val dogOwnerId: Long,
    val name: String
)

data class OwnerAndDogs(
    @Embedded val owner: Owner2,
    @Relation(parentColumn = "ownerId", entityColumn = "dogOwnerId")
    val dogs : List<Dog2>
)

@Dao
interface exampleDao{
    @Transaction
    @Query("SELECT * FROM Owner2")
    fun getUserAndDogs(): List<OwnerAndDogs>
}