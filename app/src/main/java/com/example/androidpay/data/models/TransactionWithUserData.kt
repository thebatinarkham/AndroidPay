package com.example.androidpay.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionWithUserData(
    var transactionId:Int,
    var userId:Int,
    @ColumnInfo(name = "amount")
    var transactionAmount: Int,
    @ColumnInfo(name = "description")
    var transactionDescription: String,
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
    var dueDate:String,
    @ColumnInfo(name = "title")
    var foreignUserTitle:String,
    @ColumnInfo(name = "image_url")
    var foreignUserImageUrl:String

):Parcelable