package br.com.hugoyamashita.btgmobilechallenge.currencyapi.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies")
    fun getAll(): Observable<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currency: Currency): Completable

    @Query("DELETE FROM currencies")
    fun deleteAll(): Completable

}