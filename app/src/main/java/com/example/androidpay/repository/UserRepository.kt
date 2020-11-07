package com.example.androidpay.repository

import androidx.lifecycle.LiveData
import com.example.androidpay.data.UserDao
import com.example.androidpay.data.models.UserData

class UserRepository(private val userDao: UserDao) {

    val getAllData:LiveData<List<UserData>> = userDao.getAllUser()

    suspend fun insertData(userData: UserData){
        userDao.insertUser(userData)
    }

    suspend fun updateData(userData: UserData){
       userDao.updateUser(userData)
    }

    suspend fun deleteItem(userData: UserData){
        userDao.deleteItem(userData)
    }

    suspend fun deleteAll(){
        userDao.deleteAll()
    }
    





}