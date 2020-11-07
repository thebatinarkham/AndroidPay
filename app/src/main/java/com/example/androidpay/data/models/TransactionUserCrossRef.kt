package com.example.androidpay.data.models

import androidx.room.Entity

@Entity(primaryKeys = ["transactionId","userId"])
data class TransactionUserCrossRef(
    val transactionId:Long,
    val userId:Long
)
