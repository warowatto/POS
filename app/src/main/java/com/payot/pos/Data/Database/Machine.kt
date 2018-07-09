package com.payot.pos.Data.Database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Machine(
        @PrimaryKey(autoGenerate = true)
        val id: Int,

        val name: String,

        val mac: String)