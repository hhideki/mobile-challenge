package br.com.hugoyamashita.btgmobilechallenge.currencyapi

import br.com.hugoyamashita.btgmobilechallenge.currencyapi.model.ConversionRate
import br.com.hugoyamashita.btgmobilechallenge.currencyapi.model.Currency
import io.reactivex.Single

interface CurrencyApiContract {

    interface Api {
        fun getAvailableCurrencies(): Single<List<Currency>>
        fun getConversionRates(): Single<List<ConversionRate>>
    }

    interface Cache {
        fun getCurrencies(): List<Currency>
        // fun getConversionRates(): List<ConversionRate>
        fun purge(): Boolean
    }

}