package com.payot.pos.DI.Modules

import dagger.Module
import io.reactivex.Observable

@Module
class PaymentModule {

}

interface AppPOSController {
    fun getSate(): Boolean

    fun stateObserver(): Observable<Boolean>

    fun payment(): Observable<Map<String, Any>>
}