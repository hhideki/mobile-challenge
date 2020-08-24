package br.com.hugoyamashita.btgmobilechallenge.currencyapi

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CurrencyWebserviceRequestTest::class,
    ConversionRateWebserviceRequestTest::class
)
class CurrencyApiTestSuite