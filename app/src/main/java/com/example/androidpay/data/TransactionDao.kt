package com.example.androidpay.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.androidpay.data.models.TransactionData
import com.example.androidpay.data.models.TransactionWithUserData

@Dao
interface TransactionDao {
    @Transaction
    @Query("SELECT * FROM transaction_table ORDER BY transactionId ASC")
    fun getAllData():LiveData<List<TransactionData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(transactionData: TransactionData):Long

    @Update
    suspend fun updateData(transactionData: TransactionData)

    @Delete
    suspend fun deleteItem(transactionData: TransactionData)

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM transaction_table WHERE transactionId =:searchId")
    fun  searchTransactionById(searchId :Int):LiveData<TransactionData>

    @Query("SELECT * FROM transaction_table inner join user_table on transaction_table.foreignAccount_id = user_table.userId")
    fun getTransactionWithUser(): LiveData<List<TransactionWithUserData>>


}