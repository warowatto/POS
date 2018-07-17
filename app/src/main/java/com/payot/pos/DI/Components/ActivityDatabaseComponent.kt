package com.payot.pos.DI.Components

import com.payot.pos.Activity.ProductSettingActivity
import com.payot.pos.DI.PerActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class))
interface ActivityDatabaseComponent {
    fun inject(activity: ProductSettingActivity)
}