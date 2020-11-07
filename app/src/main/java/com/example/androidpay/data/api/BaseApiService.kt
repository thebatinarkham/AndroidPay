package com.example.androidpay.data.api

import com.example.androidpay.data.models.TransactionData
import com.example.androidpay.data.models.UserData
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BaseApiService {
    private val BASE_URL = "https://raw.githubusercontent.com/"

    private val api = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BaseApi::class.java)

    fun getUser(): Single<List<UserData>> {
        return api.getUsers()
    }

    fun getTransaction():Single<List<TransactionData>>{
        return api.getTransaction()
    }
}