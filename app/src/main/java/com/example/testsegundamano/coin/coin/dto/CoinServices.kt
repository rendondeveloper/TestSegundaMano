package com.example.testsegundamano.coin.coin.dto

import com.example.testsegundamano.coin.coin.dto.api.ResponseCatalogCoin
import com.example.testsegundamano.coin.coin.dto.api.ResponseCoin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinServices{

    @GET( "symbols?access_key=0879701ece7de1a9af785b25854a5e74")
    fun getAllTypeCoin() : Call<ResponseCatalogCoin>

    @GET("{date}?access_key=0879701ece7de1a9af785b25854a5e74")
    fun getHistoryByDay(@Path("date") date : String,
                        @Query("symbols") coinsSearch : String) : Call<ResponseCoin>
}