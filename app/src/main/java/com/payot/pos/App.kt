package com.payot.pos

import android.app.Application
import com.payot.pos.DI.Components.AppComponent
import com.payot.pos.DI.Components.DaggerAppComponent
import com.payot.pos.DI.Modules.ApplicationModule
import com.payot.pos.DI.Modules.DatabaseModule
import com.payot.pos.DI.Modules.NetworkModule
import com.payot.pos.DI.Modules.RestAPI
import com.payot.pos.Data.User
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var api: RestAPI

    companion object {
        lateinit var component: AppComponent
        var user: User? = null
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .databaseModule(DatabaseModule())
                .networkModule(NetworkModule())
                .build()

        component.inject(this)

        val hasApp = packageManager.getLaunchIntentForPackage("kr.infinix.hpay.appposw") != null

        println(hasApp)
    }
}