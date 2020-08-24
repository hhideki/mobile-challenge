package br.com.hugoyamashita.btgmobilechallenge.currencyapi

import br.com.hugoyamashita.btgmobilechallenge.currencyapi.model.Currency

internal class CurrencyApiCache : CurrencyApiContract.Cache {

    private val currencies: List<Currency>? = null

    override fun getCurrencies(): List<Currency> {
        return listOf()
    }

    override fun purge(): Boolean {
        return false
    }

}