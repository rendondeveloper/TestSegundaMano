package com.example.testsegundamano.coin.coin.dto.api

import com.google.gson.annotations.Expose

data class ResponseCatalogCoin (
    @Expose
    val success: Boolean,
    @Expose
    val symbols: Map<String, String>
)