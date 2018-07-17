package com.payot.pos.Data.Database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Machine(
        @PrimaryKey(autoGenerate = true)
        var id: Int?,

        val name: String,

        val mac: String,

        val type: String,

        val info: String)