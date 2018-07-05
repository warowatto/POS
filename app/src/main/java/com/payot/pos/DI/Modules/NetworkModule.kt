package com.payot.pos.DI.Modules

import dagger.Module
import io.reactivex.Observable
import io.reactivex.Single

@Module
class NetworkModule {
}

interface API {
    fun login(): Single<User>
}