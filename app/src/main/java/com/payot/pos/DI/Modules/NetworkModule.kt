package com.payot.pos.DI.Modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.payot.pos.Data.User
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.text.DateFormat
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    val host = ""

    @Singleton
    @Provides
    fun okhttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor())
                .build()
    }

    @Singleton
    @Provides
    fun gson(): Gson {
        return GsonBuilder()
                .setDateFormat(DateFormat.FULL)
                .create()
    }

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(host)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun api(retrofit: Retrofit): RestAPI {
        return retrofit.create(RestAPI::class.java)
    }
}

interface RestAPI {

    @FormUrlEncoded
    @POST()
    fun login(@Field("email") email: String, @Field("password") password: String): Single<User>


}