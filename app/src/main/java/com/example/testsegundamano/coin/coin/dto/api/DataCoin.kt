package com.example.testsegundamano.coin.coin.dto.api

import com.example.testsegundamano.coin.coin.dto.CoinState
import java.time.LocalDate

data class DataCoin(
        val date : LocalDate,
        val coinFrom : String,
        val coinTo : String,
        var arrowFrom : CoinState = CoinState.UP,
        var arrowTo : CoinState =  CoinState.UP,
        val valueFrom : Double,
        val valueTo : Double
)