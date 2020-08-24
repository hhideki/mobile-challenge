package br.com.hugoyamashita.btgmobilechallenge.currencyapi.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Currency::class,
        ConversionRate::class
    ],
    version = 1
)
abstract class ApiDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun conversionRateDate(): ConversionRateDao

    companion object {

        @Volatile
        private var singletonInstance: ApiDatabase? = null

        fun getInstance(context: Context) =
            singletonInstance ?: synchronized(this) {
                singletonInstance ?: buildDatabase(context).also { singletonInstance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ApiDatabase::class.java,
                "currency-api.db"
            ).build()

    }

}