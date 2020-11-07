package com.example.androidpay.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.androidpay.data.models.UserData

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table ORDER BY userId ASC")
    fun getAllUser():LiveData<List<UserData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userData: UserData)

    @Update
    suspend fun updateUser(userData: UserData)

    @Delete
    suspend fun deleteItem(userData: UserData)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String):LiveData<List<UserData>>

}