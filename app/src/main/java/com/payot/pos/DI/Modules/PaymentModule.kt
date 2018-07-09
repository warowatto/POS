package com.payot.pos.DI.Modules

import dagger.Module
import io.reactivex.Observable

@Module
class PaymentModule {

}

class NetworkEception : Exception("네트워크가 활성상태가 아닙니다")
class CardPaymentException : Exception("해당카드는 사용불가능한 카드 입니다")

interface AppPOSController {
    fun getSate(): Boolean

    fun stateObserver(): Observable<Boolean>

    fun payment(): Observable<Map<String, Any>>
}