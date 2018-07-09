package com.payot.pos.DI.Modules

import android.app.Activity
import android.content.Context
import android.net.NetworkInfo
import android.view.WindowManager
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.payot.pos.Utils.observeOnMainThread
import dagger.Provides
import io.reactivex.Observable
import javax.inject.Singleton

class DeviceModule {

//    @Singleton
//    @Provides
//    fun checker(): ApplicationRunningChecker = object : ApplicationRunningChecker {
//        override fun checkApp(packageName: String): Boolean {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//    }
//
//    @Singleton
//    @Provides
//    fun controller(context: Context): DeviceController = object : DeviceController {
//        override fun networkState(): Observable<Boolean> {
//            return ReactiveNetwork.observeNetworkConnectivity(context)
//                    .map { it.state == NetworkInfo.State.CONNECTED }
//                    .observeOnMainThread()
//        }
//
//
//        override fun bluetoothEnable(): Observable<Boolean> {
//            TODO()
//        }
//
//        override fun alwaysScreenConfig(activity: Activity, boolean: Boolean): Observable<Boolean> {
//            if (boolean) activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//            else activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//
//            return Observable.just(boolean)
//        }
//
//    }
}

interface DeviceController {
    fun bluetoothEnable(): Observable<Boolean>

    fun networkState(): Observable<Boolean>

    fun alwaysScreenConfig(activity: Activity, boolean: Boolean): Observable<Boolean>
}

interface ApplicationRunningChecker {
    fun checkApp(packageName: String): Boolean
}