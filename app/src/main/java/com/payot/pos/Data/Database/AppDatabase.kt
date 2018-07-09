package com.payot.pos.Data.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO

@Database(entities = arrayOf(Machine::class, Product::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): AppDatabaseDAO
}