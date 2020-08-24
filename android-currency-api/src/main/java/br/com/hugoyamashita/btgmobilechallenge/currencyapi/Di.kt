package br.com.hugoyamashita.btgmobilechallenge.currencyapi

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal val moduleDi = DI {

    bind<Interceptor>(tag = "accessKey") with provider {
        // Add CurrencyLayer API key to every call
        Interceptor { chain ->
            val original = chain.request()
            val newUrl = original
                .url
                .newBuilder()
                .addQueryParameter("access_key", "4aa59e99be843d2fbe4fdc32ff733a1e")
                .build()
            val request = original
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(request)
        }
    }

    bind<Interceptor>(tag = "logging") with provider {
        // Logs response call's body
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    bind<OkHttpClient>() with provider {
        OkHttpClient.Builder()
            .addInterceptor(instance<Interceptor>(tag = "accessKey"))
            .addInterceptor(instance<Interceptor>(tag = "logging"))
            .build()
    }

    bind<Retrofit>() with provider {
        Retrofit.Builder()
            .baseUrl("http://api.currencylayer.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(instance())
            .build()
    }

    bind<CurrencyLayerService>() with provider {
        instance<Retrofit>().create(CurrencyLayerService::class.java)
    }

    bind<CurrencyApiContract.Api>() with provider { CurrencyApi(instance()) }

}