package com.example.androidpay.data.converter

import androidx.room.TypeConverter
import com.example.androidpay.data.models.TransactionType

class TransactionConverter {
    companion object{
        @TypeConverter
        @JvmStatic
        fun fromTransactionType(value: TransactionType):String{
            return value.name
        }

        @TypeConverter
        @JvmStatic
        fun toTransactionType(value: String):TransactionType{
            return TransactionType.valueOf(value)
        }
    }
}