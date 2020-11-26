package com.example.testsegundamano.coin.root

import com.example.testsegundamano.coin.coin.dagger.ApiModule
import com.example.testsegundamano.coin.coin.dagger.CoinModule
import com.example.testsegundamano.coin.coin.view.ui.CoinActivity
import dagger.Component

@Component(modules = [ApplicationModule::class, CoinModule::class, ApiModule::class])
interface ApplicationComponent {
    fun inject(view : CoinActivity)
}