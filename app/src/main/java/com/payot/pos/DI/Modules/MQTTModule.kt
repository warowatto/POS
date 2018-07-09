package com.payot.pos.DI.Modules

import android.content.Context
import dagger.Module
import dagger.Provides
import org.eclipse.paho.android.service.MqttAndroidClient
import javax.inject.Singleton

@Module
class MQTTModule {

    var host = ""

    @Singleton
    @Provides
    fun mqttClient(context: Context): MqttAndroidClient {
        return MqttAndroidClient(context, host, null)
    }
}