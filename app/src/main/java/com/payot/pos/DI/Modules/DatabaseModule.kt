package com.payot.pos.DI.Modules

import android.arch.persistence.room.Room
import android.content.Context
import com.payot.pos.Data.Database.AppDatabase
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun dao(context: Context): AppDatabaseDAO {
        return Room.databaseBuilder(context, AppDatabase::class.java, "product-database").allowMainThreadQueries()
                .build().dao()
    }
}