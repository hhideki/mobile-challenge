package br.com.hugoyamashita.btgmobilechallenge.currencyapi.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ConversionRateDao {

    @Query("SELECT * FROM rates")
    fun getAll(): Observable<List<ConversionRate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rate: ConversionRate): Completable

    @Query("DELETE FROM rates")
    fun deleteAll(): Completable

}