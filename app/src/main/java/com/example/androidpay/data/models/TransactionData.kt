package com.example.androidpay.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "transaction_table",
    foreignKeys = [ForeignKey(
    entity = UserData::class,
    parentColumns = arrayOf("userId"),
    childColumns = arrayOf("foreignAccount_id"),
    onDelete = ForeignKey.CASCADE
)])

@Parcelize
data class TransactionData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionId")
    var id: Int,
    var amount: Long,
    var description: String,
    var transactionTime: String,
    val userAccountId: Int,
    @ColumnInfo(name = "foreignAccount_id",index = true)
    val foreignAccountId: Int,
    var paymentMethod: String,
    var transactionType: TransactionType,
    var bankName:String,
    var creditCardNumber:String,
    var expireDate:String,
    var cardCvv:String,
    var dueDate:String
) : Parcelable
