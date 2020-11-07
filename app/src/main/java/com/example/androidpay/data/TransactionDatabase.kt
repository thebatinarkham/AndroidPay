package com.example.androidpay.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidpay.data.converter.LocalDateConverter
import com.example.androidpay.data.converter.TransactionConverter
import com.example.androidpay.data.models.TransactionData
import com.example.androidpay.data.models.UserData

@Database(entities = [TransactionData::class, UserData::class],version = 1,exportSchema = false)
@TypeConverters(TransactionConverter::class,LocalDateConverter::class)
abstract class TransactionDatabase:RoomDatabase() {

    abstract fun transactionDao() :TransactionDao
    abstract fun userDao() :UserDao

    companion object{
        @Volatile
        private var INSTANCE:TransactionDatabase? = null

        fun getDatabase(context: Context) : TransactionDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return  tempInstance
            }

            synchronized(this){
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "transaction_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}