package com.example.testsegundamano.coin.coin.dagger

import com.example.testsegundamano.coin.coin.dto.CoinServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL : String = "http://data.fixer.io/api/";

    @Provides fun providerCoinServices() : CoinServices{
        return providerRetrofit(BASE_URL, providerHttpClient()).create(CoinServices::class.java)
    }

    @Provides fun providerHttpClient() : OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level  = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient()
        client.newBuilder().addInterceptor(interceptor)
        return client;
    }

    @Provides fun providerRetrofit(baseUrl: String, client: OkHttpClient) : Retrofit {
        return  Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}
