package com.substantive.prepare.repository.Api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkingFactory {
    companion object {
        val weather = Retrofit.Builder()
                .baseUrl("https://api.weather.gov")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        inline fun<reified T> api(block : T.() -> Unit)
        {
            val a = weather.create(T::class.java)
            block(a)
        }
    }

}