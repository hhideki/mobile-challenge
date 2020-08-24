package br.com.hugoyamashita.btgmobilechallenge.currencyapi.model

data class ConversionRate(
    val fromSymbol: String,
    val toSymbol: String,
    val rate: Double
)