package com.example.testsegundamano.coin.coin.repository

import android.content.Context
import android.net.ConnectivityManager
import com.example.testsegundamano.coin.coin.dto.api.ResponseCatalogCoin
import com.example.testsegundamano.coin.coin.dto.Coin
import com.example.testsegundamano.coin.coin.dto.CoinServices
import com.example.testsegundamano.coin.coin.dto.api.ResponseCoin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinRepository constructor(
        private val coinServices: CoinServices, private val context: Context) : Coin.Repository{

    private lateinit var model : Coin.Model

    override fun setModel(model: Coin.Model) {
        this.model = model
    }

    override fun getCoinOption(callback: Coin.CallBackServices) {
        val call : Call<ResponseCatalogCoin>  = coinServices.getAllTypeCoin()
        call.enqueue(object : Callback<ResponseCatalogCoin>{
            override fun onResponse(
                call: Call<ResponseCatalogCoin>,
                response: Response<ResponseCatalogCoin>
            ) {
                callback.onCompletedCall(response.body());
            }
            override fun onFailure(call: Call<ResponseCatalogCoin>, t: Throwable) {
                callback.onCompletedCall(t);
            }
        } )
    }

    override suspend fun getHistory(dateCurrent: String, coins: String): ResponseCoin? {
            val call: Call<ResponseCoin> = coinServices.getHistoryByDay(dateCurrent, coins)
            return call.execute().body()
    }

    override fun availableNetwork() : Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }
}