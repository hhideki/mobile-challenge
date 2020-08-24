package br.com.hugoyamashita.btgmobilechallenge.currencyapi.model

import com.google.gson.annotations.Expose

abstract class Response(
    @Expose val success: Boolean,
    @Expose val terms: String,
    @Expose val privacy: String
)