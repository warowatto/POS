package com.payot.pos.DI.Components

import com.payot.pos.Activity.PaymentActivity
import com.payot.pos.DI.Modules.PaymentModule
import com.payot.pos.DI.PerActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(PaymentModule::class))
interface PaymentComponent {

    fun inject(activity: PaymentActivity)
}