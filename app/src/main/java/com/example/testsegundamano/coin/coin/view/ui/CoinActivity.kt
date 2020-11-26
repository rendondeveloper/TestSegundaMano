package com.example.testsegundamano.coin.coin.view.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testsegundamano.R
import com.example.testsegundamano.coin.coin.dto.Coin
import com.example.testsegundamano.coin.coin.dto.api.DataCoin
import com.example.testsegundamano.coin.root.App
import com.example.testsegundamano.coin.coin.view.adapter.CoinAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class CoinActivity : Coin.View, AppCompatActivity() {

    @Inject lateinit var presenter :  Coin.Presenter
    lateinit var coinAdapter : CoinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (this.application as App).component.inject(this)
        presenter.setView(this)
        presenter.getCoinOption()

        btnSearch.setOnClickListener {presenter.getHistory()}
        btnReloadCoinType.setOnClickListener {presenter.getCoinOption()}

        coinAdapter = CoinAdapter()
        rvData.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvData.adapter = coinAdapter
    }

    override fun setCoinOptionList(list: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spCoinTo.adapter = adapter
        spCoinFrom.adapter = adapter
    }

   override fun showEmptyStateByCoinOptionList(title: String, message: String, drawable: Drawable?) {
       btnReloadCoinType.visibility= VISIBLE
       btnSearch.isEnabled = false
       this.showOrHiddenListOrEmptyState(false)
       this.setDataState(title, message, drawable)
   }

    override fun showReadyState(title: String, message: String, drawable: Drawable?) {
        btnReloadCoinType.visibility = GONE
        btnSearch.isEnabled = true
        this.showOrHiddenListOrEmptyState(false)
        this.setDataState(title, message, drawable)
    }

   override fun setHistoryList(list: List<DataCoin>) {
           this.showOrHiddenListOrEmptyState(true)
           coinAdapter.updateList(list)
           runAnimation()
   }

    override fun showEmptyStateByHistory(title: String, message: String, drawable: Drawable?) {
        setDataState(title, message, drawable)
    }

    override fun showError(message: String) {
        Snackbar.make(clScreen, message , Snackbar.LENGTH_LONG).show();
    }

    override fun showModal(show: Boolean) {
        rlProgress.visibility = if(show) VISIBLE else GONE
    }

    override fun getFromCoin(): String? {
       return  spCoinFrom.selectedItem?.toString()
   }

   override fun getToCoin(): String? {
       return  spCoinTo.selectedItem?.toString()
   }

   override fun getLimit(): String? {
       return etLimit.text?.toString()
   }

   override fun executeSearch() {
       this.presenter.getHistory()
   }

    private fun setDataState(title: String, message: String, drawable: Drawable?){
        ivEmptyState.setImageDrawable(drawable)
        tvTitleEmptyState.text = title
        tvMessageEmptyState.text = message
    }

    private fun showOrHiddenListOrEmptyState(visible : Boolean){
        rvData.visibility  = if(visible) VISIBLE else GONE
        llEmptyState.visibility = if(visible) GONE else VISIBLE
    }

    private fun runAnimation() {
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_left_to_right);
        rvData.layoutAnimation = controller
        coinAdapter.notifyDataSetChanged();
        rvData.scheduleLayoutAnimation();
    }
}