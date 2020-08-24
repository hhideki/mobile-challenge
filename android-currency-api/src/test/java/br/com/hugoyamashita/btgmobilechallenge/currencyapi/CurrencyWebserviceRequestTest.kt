package br.com.hugoyamashita.btgmobilechallenge.currencyapi

import br.com.hugoyamashita.btgmobilechallenge.currencyapi.model.CurrencyListResponse
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.collection.IsEmptyCollection
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CurrencyWebserviceRequestTest {

    private val noCurrencyMap = mapOf<String, String>()
    private val singleCurrencyMap = mapOf("USD" to "US Dollar")
    private val multipleCurrenciesMap =
        mapOf(
            "USD" to "US Dollar",
            "BRL" to "Brazil Real"
        )

    @Test
    fun `API fails when webservice's currency request fails`() {
        // Given
        val service = mockk<CurrencyLayerService>().apply {
            every { requestAvailableCurrencies() } returns Single.just(
                CurrencyListResponse(false, "", "", mapOf())
            )
        }
        val api = CurrencyApi(service)

        // When
        val apiResponse = api.getAvailableCurrencies().test().also { it.dispose() }

        // Then
        apiResponse.apply {
            assertSubscribed()
            assertError(Exception::class.java)
            assertTerminated()
        }
    }

    @Test
    fun `Webservice should return no currencies`() {
        // Given
        val service = mockk<CurrencyLayerService>().apply {
            every { requestAvailableCurrencies() } returns Single.just(
                CurrencyListResponse(true, "", "", noCurrencyMap)
            )
        }
        val api = CurrencyApi(service)

        // When
        val apiResponse = api.getAvailableCurrencies().test().also { it.dispose() }

        // Then
        apiResponse.apply {
            assertSubscribed()
            assertNoErrors()
            assertValueCount(1)
            values().first().apply {
                assertThat(this, IsEmptyCollection())
            }
            assertTerminated()
        }
    }

    @Test
    fun `Webservice should return single currency`() {
        // Given
        val service = mockk<CurrencyLayerService>().apply {
            every { requestAvailableCurrencies() } returns Single.just(
                CurrencyListResponse(true, "", "", singleCurrencyMap)
            )
        }
        val api = CurrencyApi(service)

        // When
        val apiResponse = api.getAvailableCurrencies().test().also { it.dispose() }

        // Then
        apiResponse.apply {
            assertSubscribed()
            assertNoErrors()
            assertValueCount(1)
            values().first().apply {
                assertThat(this, not(IsEmptyCollection()))
                assertThat(size, `is`(singleCurrencyMap.size))
            }
            assertTerminated()
        }
    }

    @Test
    fun `Webservice should return multiple currencies`() {
        // Given
        val service = mockk<CurrencyLayerService>().apply {
            every { requestAvailableCurrencies() } returns Single.just(
                CurrencyListResponse(true, "", "", multipleCurrenciesMap)
            )
        }
        val api = CurrencyApi(service)

        // When
        val apiResponse = api.getAvailableCurrencies().test().also { it.dispose() }

        // Then
        apiResponse.apply {
            assertSubscribed()
            assertNoErrors()
            assertValueCount(1)
            values().first().apply {
                assertThat(this, not(IsEmptyCollection()))
                assertThat(size, `is`(multipleCurrenciesMap.size))
            }
            assertTerminated()
        }
    }

}