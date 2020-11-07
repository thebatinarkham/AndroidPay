package com.example.androidpay.repository

import androidx.lifecycle.LiveData
import com.example.androidpay.data.TransactionDao
import com.example.androidpay.data.models.TransactionData
import com.example.androidpay.data.models.TransactionWithUserData

class TransactionRepository(private val transactionDao: TransactionDao) {
    val getAllData:LiveData<List<TransactionData>> = transactionDao.getAllData()
    val getTransactionHistoryWithUserHistory:LiveData<List<TransactionWithUserData>> = transactionDao.getTransactionWithUser()

    suspend fun insertData(transactionData: TransactionData){
        transactionDao.insertData(transactionData)
    }

    suspend fun updateData(transactionData: TransactionData){
        transactionDao.updateData(transactionData)
    }

    suspend fun deleteItem(transactionData: TransactionData){
        transactionDao.deleteItem(transactionData)
    }

    suspend fun deleteAll() {
        transactionDao.deleteAll()
    }

    fun searchTransactionById(searchId :Int):LiveData<TransactionData>{
        return  transactionDao.searchTransactionById(searchId)
    }



}