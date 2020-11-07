package com.example.androidpay.data.api

import com.example.androidpay.data.models.TransactionData
import com.example.androidpay.data.models.UserData
import io.reactivex.Single
import retrofit2.http.GET

interface BaseApi {

    @GET("thebatinarkham/android-pay-Api/main/userdata.json")
    fun getUsers(): Single<List<UserData>>

    @GET("thebatinarkham/android-pay-Api/main/Transactiondata.json")
    fun getTransaction():Single<List<TransactionData>>
}


