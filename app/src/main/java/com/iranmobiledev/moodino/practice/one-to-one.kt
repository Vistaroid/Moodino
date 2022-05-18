package com.iranmobiledev.moodino.practice

import androidx.room.*

@Entity
data class Owner(
    @PrimaryKey val ownerId: Long,
    val name: String
)

@Entity
data class Dog(
    @PrimaryKey val dogId: Long,
    val dogOwnerId: Long,
    val name: String
)

data class OwnerAndDog(
    @Embedded val owner: Owner,
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "dogOwnerId"
    )
    val dog: Dog
)

@Dao
interface userDao{
    @Transaction
    @Query("SELECT * FROM Owner")
    fun getOwnerAndDog() : List<OwnerAndDog>
}