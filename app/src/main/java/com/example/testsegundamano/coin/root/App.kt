package com.example.testsegundamano.coin.root

import android.app.Application
import com.example.testsegundamano.coin.coin.dagger.ApiModule
import com.example.testsegundamano.coin.coin.dagger.CoinModule

class App : Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .apiModule(ApiModule())
            .coinModule(CoinModule(this))
            .build();
    }
}