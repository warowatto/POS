package com.payot.pos.Data.Database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(
//        foreignKeys =
//        arrayOf(
//                ForeignKey(
//                        entity = Machine::class,
//                        parentColumns = arrayOf("id"),
//                        childColumns = arrayOf("machineId"),
//                        onDelete = ForeignKey.CASCADE
//                )
//        )
)
data class Product(
        @PrimaryKey(autoGenerate = true)
        val id: Int,

        val machineId: Int,

        val name: String,

        val price: Int)