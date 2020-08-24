package br.com.hugoyamashita.btgmobilechallenge.currencyapi

import br.com.hugoyamashita.btgmobilechallenge.currencyapi.model.Currency
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CurrencyApi(private val service: CurrencyLayerService) : CurrencyApiContract.Api {

    override fun getAvailableCurrencies() =
        Single
            .create<List<Currency>> { emitter ->
                try {
                    val currencies = service
                        .requestAvailableCurrencies()
                        .map { response ->
                            if (response.success) {
                                if (response.currencies.isNotEmpty()) {
                                    response.currencies.map { Currency(it.key, it.value) }
                                } else {
                                    listOf()
                                }
                            } else {
                                emitter.tryOnError(Exception("Service request failed"))
                                listOf()
                            }
                        }
                        .blockingGet()

                    emitter.onSuccess(currencies)
                } catch (e: Exception) {
                    emitter.tryOnError(e)
                }
            }

}