package br.com.hugoyamashita.btgmobilechallenge.currencyapi.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class ConversionRate(
    @PrimaryKey
    @ColumnInfo(name = "symbol")
    val symbol: String,

    @ColumnInfo(name = "rate")
    val rate: Double
)