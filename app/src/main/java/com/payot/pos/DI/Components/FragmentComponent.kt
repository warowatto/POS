package com.payot.pos.DI.Components

import com.payot.pos.Activity.Fragment.MachineListFragment
import com.payot.pos.Activity.Fragment.MachineSelectFragment
import com.payot.pos.Activity.Fragment.ProductSelectFragment
import com.payot.pos.Activity.Fragment.ProductSettingFragment
import com.payot.pos.DI.PerFragment
import dagger.Component

@PerFragment
@Component(dependencies = arrayOf(AppComponent::class))
interface FragmentComponent {
    fun inject(fragment: ProductSettingFragment)

    fun inject(fragment: MachineListFragment)

    fun inject(fragment: MachineSelectFragment)

    fun inject(fragment: ProductSelectFragment)
}