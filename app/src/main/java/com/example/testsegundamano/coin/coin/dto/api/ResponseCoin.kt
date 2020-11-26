package com.example.testsegundamano.coin.coin.dto.api

import com.google.gson.annotations.Expose

data class ResponseCoin (
        @Expose
        val success: Boolean,
        @Expose
        val timestamp: Long,
        @Expose
        val historical: Boolean,
        @Expose
        val base: String,
        @Expose
        val date: String,
        @Expose
        val rates: Map<String, String>
)