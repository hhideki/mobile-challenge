package br.com.hugoyamashita.btgmobilechallenge.currencyapi

import br.com.hugoyamashita.btgmobilechallenge.currencyapi.model.ConversionRateListResponse
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
class ConversionRateWebserviceRequestTest {

    private val noRates = mapOf<String, Double>()
    private val singleRate = mapOf("USDBRL" to 1.000)
    private val multipleRate = mapOf(
        "USDBRL" to 1.000,
        "USDEUR" to 0.500
    )

    @Test
    fun `API fails when webservice's conversion rate request fails`() {
        // Given
        val service = mockk<CurrencyLayerService>().apply {
            every { requestConversionRates() } returns Single.just(
                ConversionRateListResponse(
                    false,
                    "",
                    "",
                    0L,
                    "USD",
                    mapOf()
                )
            )
        }
        val api = CurrencyApi(service)

        // When
        val apiResponse = api.getConversionRates().test().also { it.dispose() }

        // Then
        apiResponse.apply {
            assertSubscribed()
            assertError(Exception::class.java)
            assertTerminated()
        }
    }

    @Test
    fun `Webservice should return no conversion rates`() {
        // Given
        val service = mockk<CurrencyLayerService>().apply {
            every { requestConversionRates() } returns Single.just(
                ConversionRateListResponse(
                    true,
                    "",
                    "",
                    0L,
                    "USD",
                    noRates
                )
            )
        }
        val api = CurrencyApi(service)

        // When
        val apiResponse = api.getConversionRates().test().also { it.dispose() }

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
    fun `Webservice should return single conversion rate`() {
        // Given
        val service = mockk<CurrencyLayerService>().apply {
            every { requestConversionRates() } returns Single.just(
                ConversionRateListResponse(
                    true,
                    "",
                    "",
                    0L,
                    "USD",
                    singleRate
                )
            )
        }
        val api = CurrencyApi(service)

        // When
        val apiResponse = api.getConversionRates().test().also { it.dispose() }

        // Then
        apiResponse.apply {
            assertSubscribed()
            assertNoErrors()
            assertValueCount(1)
            values().first().apply {
                assertThat(this, not(IsEmptyCollection()))
                assertThat(size, `is`(singleRate.size))
            }
            assertTerminated()
        }
    }

    @Test
    fun `Webservice should return multiple conversion rates`() {
        // Given
        val service = mockk<CurrencyLayerService>().apply {
            every { requestConversionRates() } returns Single.just(
                ConversionRateListResponse(
                    true,
                    "",
                    "",
                    0L,
                    "USD",
                    multipleRate
                )
            )
        }
        val api = CurrencyApi(service)

        // When
        val apiResponse = api.getConversionRates().test().also { it.dispose() }

        // Then
        apiResponse.apply {
            assertSubscribed()
            assertNoErrors()
            assertValueCount(1)
            values().first().apply {
                assertThat(this, not(IsEmptyCollection()))
                assertThat(size, `is`(multipleRate.size))
            }
            assertTerminated()
        }
    }

}