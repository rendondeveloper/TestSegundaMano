package com.example.testsegundamano.coin.coin.presenter

import android.content.Context
import com.example.testsegundamano.R
import com.example.testsegundamano.coin.coin.dto.Coin
import com.example.testsegundamano.coin.coin.dto.api.DataCoin

class CoinPresenter
    constructor(private var model: Coin.Model, private var context: Context)
    : Coin.Presenter{

    private lateinit var view: Coin.View;

    override fun setView(view: Coin.View) {
        this.view = view;
        this.model.setPresenter(this)
    }

    override fun getCoinOption() {
        this.model.getCoinOption()
    }

    override fun setCoinOption(list: List<String>) {
        if(list.isEmpty()){
            this.setEmptyStateByCoinList(false)
        }else{
            this.setReadyState()
            this.view.setCoinOptionList(list)
        }
    }

    override fun showErrorCoinOption(validationIsInternet: Boolean) {
        this.setEmptyStateByCoinList(validationIsInternet)
    }

    override fun getHistory() {
        val fromCoin = this.view.getFromCoin()
        val toCoin = this.view.getToCoin()
        val limit = this.view.getLimit()

        if(fromCoin == null || fromCoin.isEmpty()){
            this.view.showError(context.getString(R.string.message_error_coin_from))
            return
        }

        if(toCoin == null || toCoin.isEmpty()){
            this.view.showError(context.getString(R.string.message_error_coin_to))
            return
        }

        if(limit == null || limit.isEmpty()){
            this.view.showError(context.getString(R.string.message_error_coin_limit))
            return
        }else if(limit.toInt() < 0){
            this.view.showError(context.getString(R.string.message_error_coin_zero))
            return
        }

        this.view.showModal(true)
        this.model.getHistory(fromCoin, toCoin, limit.toInt())
    }

    override fun setHistoryList(list: List<DataCoin>) {
        this.view.showModal()
        if(list.isEmpty()){
            this.view.showEmptyStateByCoinOptionList(
                context.getString(R.string.title_empty_state),
                context.getString(R.string.text_empty_state),
                context.getDrawable(R.drawable.ic_empty))
        }else{
            this.view.setHistoryList(list)
        }
    }

    override fun showErrorHistoryEmpty() {
        this.view.showModal(false)
        this.view.showEmptyStateByHistory(
                context.getString( R.string.title_error_not_available_network),
                context.getString(R.string.message_error_not_available_network),
                context.getDrawable(R.drawable.ic_error))
    }

    private fun setReadyState(){
        this.view.showReadyState(
            context.getString(R.string.title_init_state),
            context.getString(R.string.text_init_state),
            context.getDrawable(R.drawable.ic_init))
    }

    private fun setEmptyStateByCoinList(validationIsInternet: Boolean){
        this.view.showEmptyStateByCoinOptionList(
                context.getString(if(validationIsInternet) R.string.title_error_not_available_network else R.string.message_error_general),
                context.getString(if(validationIsInternet) R.string.message_error_not_available_network else R.string.text_error_type_coin_state),
                context.getDrawable(R.drawable.ic_error))
    }

}