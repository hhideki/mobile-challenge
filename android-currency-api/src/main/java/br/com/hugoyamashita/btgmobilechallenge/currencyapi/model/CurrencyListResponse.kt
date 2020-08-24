package br.com.hugoyamashita.btgmobilechallenge.currencyapi.model

import com.google.gson.annotations.Expose

class CurrencyListResponse(
    success: Boolean,
    terms: String,
    privacy: String,
    @Expose val currencies: Map<String, String>
) : Response(success, terms, privacy)