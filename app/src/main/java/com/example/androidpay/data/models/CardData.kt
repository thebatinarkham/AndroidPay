package com.example.androidpay.data.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "card_data")
@Parcelize
data class CardData(var bankName:String,var cardNumber :String,var cardYear:Int):Parcelable