package com.payot.pos.DI.Components

import com.payot.pos.DI.Modules.ApplicationModule
import com.payot.pos.DI.Modules.DeviceModule
import com.payot.pos.DI.Modules.NetworkModule
import com.payot.pos.DI.Modules.PaymentModule
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class, NetworkModule::class, PaymentModule::class))
interface AppComponent {

}