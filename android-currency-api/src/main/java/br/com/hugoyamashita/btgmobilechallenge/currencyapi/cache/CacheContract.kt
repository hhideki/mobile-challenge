package br.com.hugoyamashita.btgmobilechallenge.currencyapi.cache

interface CacheContract<T> {

    fun get(): T?
    fun set(data: T)
    fun purge()
    fun hit()
    fun hasData(): Boolean
    fun isObsolete(): Boolean

}