package br.com.hugoyamashita.btgmobilechallenge.currencyapi.cache

internal open class Cache<T>(private val threshold: Long = 300000 /* 5 min cache */) :
    CacheContract<T> {

    private var lastUpdate = 0L
    private var data: T? = null

    override fun get() = if (!isObsolete()) data else null

    override fun set(data: T) {
        this.data = data
        hit()
    }

    override fun purge() {
        data = null
    }

    override fun hit() {
        lastUpdate = System.currentTimeMillis()
    }

    override fun hasData() = data != null && !isObsolete()

    override fun isObsolete() = System.currentTimeMillis() - lastUpdate > threshold

}