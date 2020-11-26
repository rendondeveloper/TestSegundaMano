package com.example.testsegundamano.coin.coin.dagger

import android.app.Application
import android.content.Context
import com.example.testsegundamano.coin.coin.dto.Coin
import com.example.testsegundamano.coin.coin.dto.CoinServices
import com.example.testsegundamano.coin.coin.model.CoinModel
import com.example.testsegundamano.coin.coin.presenter.CoinPresenter
import com.example.testsegundamano.coin.coin.repository.CoinRepository
import dagger.Module
import dagger.Provides

@Module class CoinModule(private val application: Application) {

    @Provides fun providerContext() : Context {
        return  application;
    }

    @Provides fun providerCoinPresenter(model : Coin.Model) : Coin.Presenter{
        return CoinPresenter(model, providerContext())
    }

    @Provides fun providerCoinModel(repository: Coin.Repository) : Coin.Model{
        return CoinModel(repository)
    }

    @Provides fun providerCoinRepository(coinServices: CoinServices) : Coin.Repository{
        return CoinRepository(coinServices,  providerContext())
    }
}