package com.payot.pos.DI.Modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val application: Application) {

    @Singleton
    @Provides
    fun context(): Context = application.applicationContext

    @Singleton
    @Provides
    fun preference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
}