package br.com.hugoyamashita.btgmobilechallenge.currencyapi

import br.com.hugoyamashita.btgmobilechallenge.currencyapi.model.ConversionRateListResponse
import br.com.hugoyamashita.btgmobilechallenge.currencyapi.model.CurrencyListResponse
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyLayerService {

    @GET("/list")
    fun requestAvailableCurrencies(): Single<CurrencyListResponse>

    @GET("/live")
    fun requestConversionRates(): Single<ConversionRateListResponse>

}