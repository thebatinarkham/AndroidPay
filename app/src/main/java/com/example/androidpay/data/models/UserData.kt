package com.example.androidpay.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class UserData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    var id:Int ,
    var title:String,
    var image_url:String,
    var gmail_id:String,
    var contact_number:String

):Parcelable