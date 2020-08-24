package br.com.hugoyamashita.btgmobilechallenge.currencyapi.model

import com.google.gson.annotations.Expose

class ConversionRateListResponse(
    success: Boolean,
    terms: String,
    privacy: String,
    @Expose val timestamp: Long,
    @Expose val source: String,
    @Expose val quotes: Map<String, Double>
) : Response(success, terms, privacy)