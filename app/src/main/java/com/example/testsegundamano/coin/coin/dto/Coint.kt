package com.example.testsegundamano.coin.coin.dto

import android.graphics.drawable.Drawable
import com.example.testsegundamano.coin.coin.dto.api.DataCoin
import com.example.testsegundamano.coin.coin.dto.api.ResponseCoin

interface Coin {

    interface View{
        fun showEmptyStateByCoinOptionList(title: String, message: String, drawable: Drawable?)
        fun showReadyState(title: String, message: String, drawable: Drawable?)
        fun setCoinOptionList(coinTypeList : List<String>)
        fun setHistoryList(list : List<DataCoin>)
        fun getFromCoin(): String?
        fun getToCoin(): String?
        fun getLimit(): String?
        fun executeSearch()
        fun showError(message : String)
        fun showModal(show: Boolean = false)
        fun showEmptyStateByHistory(title: String, message: String, drawable: Drawable?)
    }

    interface Presenter{
        fun setView(view : View)
        fun getCoinOption()
        fun setCoinOption(coinTypeList : List<String>)
        fun getHistory()
        fun setHistoryList(list : List<DataCoin>)
        fun showErrorCoinOption(validationIsInternet: Boolean)
        fun showErrorHistoryEmpty()
    }

    interface Model{
        fun setPresenter(presenter : Presenter)
        fun getCoinOption()
        fun getHistory(fromCoin : String, toCoin: String, limit : Int)
    }

    interface Repository{
        fun setModel(model : Model)
        fun getCoinOption(callback : CallBackServices)
        fun availableNetwork() : Boolean
        suspend fun getHistory(dateCurrent: String, s: String): ResponseCoin?
    }

    interface CallBackServices{
        fun <T> onCompletedCall(response : T)
    }
}