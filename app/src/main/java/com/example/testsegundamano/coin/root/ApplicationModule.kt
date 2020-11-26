package com.example.testsegundamano.coin.root

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class ApplicationModule
    constructor(private val application: Application){

    @Provides
    @Singleton
    fun providerContext() : Context {
        return  application;
    }
}