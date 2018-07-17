package com.payot.pos.DI.Components

import android.content.Context
import android.content.SharedPreferences
import com.payot.pos.App
import com.payot.pos.DI.Modules.*
import com.payot.pos.Data.Database.DAO.AppDatabaseDAO
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkModule::class, DatabaseModule::class))
interface AppComponent {
    fun inject(app: App)

    fun context(): Context

    fun sharedPreference(): SharedPreferences

    fun restAPI(): RestAPI

    fun dao(): AppDatabaseDAO
}