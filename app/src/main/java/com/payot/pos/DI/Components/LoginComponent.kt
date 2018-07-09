package com.payot.pos.DI.Components

import com.payot.pos.Activity.LoginActivity
import com.payot.pos.DI.PerActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class))
interface LoginComponent {
    fun inject(activity: LoginActivity)
}